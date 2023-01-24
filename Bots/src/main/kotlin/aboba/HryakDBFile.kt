package aboba

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class Hryaks : java.io.Serializable {
    var hryaks:ArrayList<Hryak> = ArrayList()
}
object HryakDBFiledd {
    val FILE = File("./hryaks.bin")
    val hryaks:MutableList<Hryak> = mutableListOf()

    @Synchronized
    fun save() {
        val str = ByteArrayOutputStream()
        val out = ObjectOutputStream(str)
        val n = Hryaks()
        n.hryaks += hryaks
        out.writeObject(n)
        FILE.delete()
        FILE.createNewFile()
        FILE.writeBytes(str.toByteArray())
    }

    fun load(){
        if(FILE.exists().not())return
        val inn = ByteArrayInputStream(FILE.readBytes())
        val innO = ObjectInputStream(inn)
        val n = innO.readObject() as Hryaks
        hryaks.clear()
        hryaks += n.hryaks
    }

    fun getById(id:Long) : Hryak? = hryaks.firstOrNull { it.discordID == id }

    fun getTop() : List<Hryak> {
        val all = hryaks.toMutableList().sortedByDescending { it.score }
        val list = mutableListOf<Hryak>()
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

    fun getOrCreate(id:ULong) : Hryak {
        var inCache = getById(id.toLong())
        if(inCache == null){
            inCache = Hryak()
            inCache.discordID = id.toLong()
            hryaks += inCache
        }
        return inCache
    }
}