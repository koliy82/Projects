package aboba.models

import com.beust.klaxon.Klaxon
import java.io.File

object MarryDB {
    val FILE = File("./marry.json")
    val marry:MutableList<Marry> = mutableListOf()

    @Synchronized
    fun save() {
        FILE.delete()
        FILE.createNewFile()
        FILE.writeText(Klaxon().toJsonString(marry))
    }

    fun load(){
        if(FILE.exists().not())return
        marry.clear()
        marry += Klaxon().parseArray<Marry>(FILE.readText())!!
        println("Marry is loaded!")
    }

    fun getMarryByfirstPartnerId(id:Long) : Marry? = marry.firstOrNull { it.firstPartnerID == id}

    fun getMarryBysecondPartnerId(id:Long) : Marry? = marry.firstOrNull { it.secondPartnerID == id}

    fun getOrNullMarry(player: Player) : Marry? {
        return getMarryByfirstPartnerId(player.telegramID) ?: getMarryBysecondPartnerId(player.telegramID)
    }

    fun addMarry(firstPlayer: Player, secondPlayer: Player) {
        this.marry += Marry(firstPlayer, secondPlayer)
    }

    fun deleteMarry(player: Player) {
        val marry = getOrNullMarry(player) ?: return
        this.marry -= marry
    }

    fun getMarries() : List<Marry> {
        val all = marry.toMutableList().sortedBy{ it.startDay }
        val list = mutableListOf<Marry>()
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
    fun deleteById(id:Int){
        marry.removeAt(id-1)
        save()
    }
}