package aboba.models

import aboba.discord.utils.getDay

class Hryak{
    var name = "Хряк"
    var score:Int = 0
        set(value){
            field = if(value < 0) 0 else value
        }

    var isPohudel: Boolean = false

    val isFeed:Boolean
        get(){
            val lastDay = getDay(fedL)
            val curDay = getDay(System.currentTimeMillis())
            return lastDay == curDay
        }
    
    var fedL:Long = 0L

    constructor(){

    }

    constructor(name: String, score: Int, isPohudel: Boolean, fedL: Long) {
        this.name = name
        this.score = score
        this.isPohudel = isPohudel
        this.fedL = fedL
    }
}