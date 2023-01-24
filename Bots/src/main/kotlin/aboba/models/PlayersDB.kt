package aboba.models

import com.beust.klaxon.Klaxon
import java.io.*

object PlayersDB {
    val FILE = File("./players.json")
    val players:MutableList<Player> = mutableListOf()

    @Synchronized
    fun save() {
        FILE.delete()
        FILE.createNewFile()
        FILE.writeText(Klaxon().toJsonString(players))
    }

    fun load(){
        if(FILE.exists().not())return
        players.clear()
        players += Klaxon().parseArray<Player>(FILE.readText())!!
        println("Players is loaded!")
    }

    fun getById(id:Long) : Player? = players.firstOrNull { it.id == id }
    fun getByDiscordId(id:Long) : Player? = players.firstOrNull { it.discordID == id }
    fun getByTelegramId(id:Long) : Player? = players.firstOrNull { it.telegramID == id }

    fun getOrCreateDiscord(id:Long) : Player {
        var pl = getByDiscordId(id)
        if(pl == null){
            pl = Player()
            pl.id = players.size.toLong()
            pl.discordID = id
            players += pl
        }
        return pl
    }

    fun getOrCreateTelegram(id:Long) : Player {
        var pl = getByTelegramId(id)
        if(pl == null){
            pl = Player()
            pl.id = players.size.toLong()
            pl.telegramID = id
            players += pl
        }
        return pl
    }

    fun getTop() : List<Player> {
        val all = players.toMutableList().sortedByDescending { it.hryak.score }
        val list = mutableListOf<Player>()
        var i = 0
        run a@{
            all.forEach {
                list += it
                i++
                if(i > 75){
                    return@a
                }
            }
        }
        return list
    }
}