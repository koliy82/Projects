package aboba

import aboba.discord.utils.getDay

class Hryak : java.io.Serializable{
    var name = "Хряк"
    var score:Int = 0
        set(value){
            if(value < 0){
                field = 0
            }else{
                field = value
            }
        }

    var isPohudel: Boolean = false

    val покормил:Boolean
        get(){
            val lastDay = getDay(fedL)
            val curDay = getDay(System.currentTimeMillis())
            return lastDay == curDay
        }

    var discordID: Long = 0L

    //var fed:String = ""
    var fedL:Long = 0L
        /*set(value){
            fed = "${value}"
        }
        get(){
            return fed.toLongOrNull() ?: 0L
        }*/

    constructor(discord:ULong){
        this.discordID = discord.toLong()
    }

    constructor(){

    }
}