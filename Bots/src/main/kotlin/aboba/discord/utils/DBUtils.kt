package aboba.discord.utils

import aboba.HryakDBFiledd
import aboba.models.Hryak
import aboba.models.Player
import aboba.models.PlayersDB
import com.beust.klaxon.Klaxon
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

object DBUtils {
    val perenos = File("./players.json")

    fun transfer() {
        HryakDBFiledd.load()
        perenos.delete()
        perenos.createNewFile()
        val list = mutableListOf<Player>()
        var id = 0L
        HryakDBFiledd.hryaks.forEach {
            val pl = Player()
            pl.id = id
            pl.discordID = it.discordID
            val hr = aboba.models.Hryak()
            hr.name = it.name
            hr.score = it.score
            hr.isPohudel = it.isPohudel
            hr.fedL = it.fedL
            pl.hryak = hr
           list.add(pl)
            id++
        }
        perenos.writeText(Klaxon().toJsonString(list))
        println("SUCCES")
        PlayersDB.load()
    }

}


