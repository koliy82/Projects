package aboba.discord.events

import aboba.kordBot
import aboba.discord.utils.eventChannel
import aboba.discord.utils.mainChannel
import aboba.discord.utils.sendEmbedMessage
import aboba.discord.utils.sendReq
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import dev.kord.common.Color
import dev.kord.common.entity.Snowflake
import dev.kord.rest.builder.message.create.embed
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class UserInfo(){
    @Json("data")
    val streams:MutableList<Stream> = mutableListOf()
}

class Stream {
    @Json("type")
    val type:String = ""
    @Json("id")
    val id:String = ""
    @Json("title")
    val title:String = ""
    @Json("game_name")
    val category:String = ""
    @Json("thumbnail_url")
    val thumbnail_url:String = ""
}
object CheckStream {
    val scheduler:ScheduledExecutorService = Executors.newScheduledThreadPool(1)
    val file = File("./stream.id")
    var lastId:String = ""

    init {
        if(file.exists().not()){
            file.createNewFile()
        }
        lastId = file.readText()
    }

    fun writeId(i:String){
        file.writeText(i)
    }

    fun parse() : UserInfo {
        return Klaxon().parse(sendReq()!!) ?: throw IllegalAccessError("dadawd")
    }

    fun bind(){
        println("Stream checker is loaded!")
        scheduler.scheduleWithFixedDelay({
            try {
                val data = parse()
                val stream = data.streams.firstOrNull() ?: return@scheduleWithFixedDelay
                if (stream.id != lastId) {
                    if (stream.type != "live") return@scheduleWithFixedDelay
                        lastId = stream.id
                        writeId(lastId)
                        println("СТРИМ ${stream.id}!")
                        runBlocking {
                            try {
                                kordBot.rest.channel.createMessage(Snowflake(eventChannel)){
                                    content = "||@everyone||"
                                    embed {
                                        color = Color(red = 0, green = 255, blue = 255)
                                        title = stream.title
                                        field {
                                            this.name = "Категория"
                                            this.value = stream.category
                                        }
                                        description = "aratossik падрубил!"
                                        url = "https://www.twitch.tv/aratossik"
                                        image = "https://static-cdn.jtvnw.net/previews-ttv/live_user_aratossik-1280x720.jpg"
                                        thumbnail { url = "https://static-cdn.jtvnw.net/jtv_user_pictures/efe1c187-a8da-4967-9073-3e8af52d6007-profile_image-300x300.png" }
                                    }
                                }
                            }catch (e:Exception){
                                e.printStackTrace()
                            }
                        }
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }, 0L, 10L, TimeUnit.SECONDS)
    }
}