package aboba.discord.utils

import aboba.kordBot
import dev.kord.common.Color
import dev.kord.common.entity.Snowflake
import dev.kord.core.entity.User
import dev.kord.rest.builder.message.create.embed
import kotlinx.coroutines.runBlocking
import java.time.ZoneId
import java.util.*
val hryakRole = Snowflake(1059207505017835571)
val guildID = Snowflake(1043852434612703295)
val statisticChannel = 1065981828864950302U
val adminID = 497776932503683086U

val mainChannel = 1055549552008106054U //MAIN
val eventChannel = 1043864536136032276U //EVENTS MAIN
//val mainChannel = 1045714804289052732U //TEST
//val eventChannel = 1045714804289052732U //TEST

fun User.toLinkedName() : String = "<@${this.id.value}>"

fun Snowflake.isBotChannel() : Boolean = this.value == mainChannel

fun getCal() : Calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone(ZoneId.of("Europe/Moscow")))
fun timeToNight() : String {
    val ni = getCal()
    val h = ni[Calendar.HOUR_OF_DAY]
    val m = ni[Calendar.MINUTE]
    return "${23 - h} часа ${60 - m} мин"
}
fun getDay(time:Long) : Int{
    return getCal().apply {
        this.time = Date(time)
    }[Calendar.DAY_OF_MONTH]
}

fun Long.dateToText() : String {
    val calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone(ZoneId.of("Europe/Moscow")))
    calendar.time = Date(this)
    val hh = calendar[Calendar.HOUR_OF_DAY].toKrutoyNolik()
    val min = calendar[Calendar.MINUTE].toKrutoyNolik()
    val sec = calendar[Calendar.SECOND].toKrutoyNolik()
    return "${hh}:${min}:${sec}"
}

fun getMoscowTimeMillis() : Long {
    getCal().apply {
        return this.timeInMillis
    }
}

fun Int.toKrutoyNolik() : String {
    return if(this < 10) "0${this}"
    else "$this"
}

fun sendMessage(msg: String, channelId:ULong = mainChannel){
    runBlocking {
        try {
            kordBot.rest.channel.createMessage(Snowflake(channelId)){
                content = msg
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
}

fun sendEmbedMessage(titleTxt:String, msg: String, channelId:ULong = mainChannel){
    runBlocking {
        try {
            kordBot.rest.channel.createMessage(Snowflake(channelId)){
                //content = ""
                embed {
                    color = Color(red = 211, green = 17, blue = 20)
                    title = titleTxt
                    description = msg
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}