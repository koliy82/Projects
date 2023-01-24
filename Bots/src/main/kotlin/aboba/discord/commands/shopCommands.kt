package aboba.discord.commands

import aboba.discord.utils.guildID
import aboba.discord.utils.hryakRole
import aboba.discord.utils.sendMessage
import aboba.kordBot
import dev.kord.common.entity.Snowflake
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File


//fun shopCommands() = commands("Лавка Хряков") {
//    slash("shop", "Посмотреть товары в лавке") {
//        execute {
//            var txt = "МАГАЗИН\n"
//            //TODO Оформить покрасивей
//             ShopDB.getAll().forEach{
//                 txt += it.name+" "+it.price+"\n"
//             }
//            respond(txt)
//        }
//    }
//
//    globalSlash("buy", "Купить товар из лавки"){
//        execute(EveryArg) {
//            val item = ShopDB.getByName(args.first)
//            if(item==null){
//                respond("Такого предмета в лавке нет")
//                return@execute
//            }
//            val hryak = HryakDBFile.getOrCreate(author.id.value)
//            if(hryak.score<item.price){
//                respond("Недостаточно массы, нужно ${item.price} кг, а у вас ${hryak.score}")
//                return@execute
//            }
//            when(item.name){
//                "test"->{
//                    //TODO Ебануть код покупки
//                    val member = guild!!.getMember(author.id)
//                    if(member.roleIds.contains(hryakRole)){
//                        respond("${author.toLinkedName()}, но у тебя уже есть роль <@&1059207505017835571>")
//                        return@execute
//                    }
//                    BuyerDB.add(Buyer(author.id.value))
//                    guild!!.getMember(author.id).addRole(hryakRole,"Купил")
//                    respondPublic("${author.toLinkedName()}, купиль роль <@&1059207505017835571>")
//                }
//                "item1"->{
//                    //TODO Ебануть код покупки
//                    respondPublic("Купиль item1")
//                }
//            }
//            hryak.score-=item.price
//        }
//    }
//
//    slash("newItem", "test"){
//        execute(EveryArg, IntegerArg) {
//            ShopDB.add(args.first, args.second)
//            respond("123")
//        }
//    }
//}

@Serializable
class Buyer{
    var discordID:Long = 0L
    var buyTime:Long = System.currentTimeMillis()
    constructor(discord:ULong){
        this.discordID = discord.toLong()
    }
}


object BuyerDB {
    val FILE = File("buyers.db")
    fun add(buyer: Buyer){
        if(FILE.exists().not()){
            val json = Json.encodeToString(mutableListOf(buyer))
            FILE.createNewFile()
            FILE.writeText(json)
        }else{
            val item = Json.decodeFromString<MutableList<Buyer>>(FILE.readText())
            item += buyer
            val json = Json.encodeToString(item)
            FILE.delete()
            FILE.createNewFile()
            FILE.writeText(json)
        }
    }

    fun check(){
        val item = Json.decodeFromString<MutableList<Buyer>>(FILE.readText())
        var count = 0
        var txt = ""
        item.forEach{
            if(System.currentTimeMillis() >= it.buyTime+(1000 * 60 * 60 * 60 * 24 * 31)){
                runBlocking{
                    item.remove(it)
                    kordBot.rest.guild.deleteRoleFromGuildMember(guildID, hryakRole, Snowflake(it.discordID))
                    count++
                    txt+="${count}. <@${it.discordID}>\n"
                }
                //Guild().getMember(Snowflake(it.discordID)).removeRole(hryakRole, "Закончилась")
            }
        }
        if(count!=0){
            sendMessage("Сегодня закончились роли у $count человек, вот они:\n"+txt)
        }
    }
}

@Serializable
data class Item(var name:String, var price:Int)
object ShopDB {
    val dir = File("./saves/")
    //val FILE = File("${dir}shop.db")
    val items:MutableList<Item> = mutableListOf()

    @Synchronized
    fun save() {
        //val str = ByteArrayOutputStream()
        //val out = ObjectOutputStream(str)
        //out.writeObject(n)
        //FILE.delete()
        //FILE.createNewFile()
        //FILE.writeBytes(str.toByteArray())
        dir.mkdir()
        items.forEach{
            val json = Json.encodeToString(it)
            val file = File("$dir/${it.name}.json")
            file.delete()
            file.createNewFile()
            file.writeText(json)
        }
        println("Shop is saved.")
    }

    fun load(){
        if(dir.isDirectory.not())return
        //val inn = ByteArrayInputStream(FILE.readBytes())
        //val innO = ObjectInputStream(inn)
        //val n = innO.readObject() as Shop
        items.clear()
        dir.walk().forEach {
            if(it.isFile){
                val json = it.readText()
                val item = Json.decodeFromString<Item>(json)
                items += item
            }
        }
        println("Shop is loaded.")
    }

    fun add(name:String, price:Int){
        items.add(Item(name, price))
        save()
    }

    fun getByName(name:String) : Item? = items.firstOrNull { it.name == name }

    fun getAll() = items
}