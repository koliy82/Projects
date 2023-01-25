package aboba

import aboba.discord.events.CheckStream
import aboba.discord.events.NightEvent
import aboba.discord.utils.*
import aboba.models.Marry
import aboba.models.MarryDB
import aboba.models.Player
import aboba.models.PlayersDB
import aboba.telegram.tgBot
import com.github.kotlintelegrambot.dispatcher.*
import com.github.kotlintelegrambot.entities.*
import dev.kord.common.annotation.KordPreview
import dev.kord.common.entity.*
import dev.kord.common.entity.optional.Optional
import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.rest.json.request.ChannelModifyPatchRequest
import kotlinx.coroutines.*
import me.jakejmattson.discordkt.dsl.bot
import me.jakejmattson.discordkt.dsl.listeners
import me.jakejmattson.discordkt.extensions.toTimeString
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.lang.Thread.sleep
import java.lang.management.ManagementFactory
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.math.round


const val token = "DELETED"

val event = NightEvent()
lateinit var kordBot:Kord

object Main{
    @JvmStatic
    fun main(argsZ:Array<String>){
        tgBot.start()
        thread {
            while(true){
                when(readln()){
                    "transfer"->{
                        DBUtils.transfer()
                    }
                    "mem"->{
                        printMemory()
                    }
                    "gc"->{
                        System.gc()
                        println("GC COLLECTED")
                    }
                }
            }
        }
        startDiscord()
    }

    fun printMemory(){
        val heapMemoryUsage = ManagementFactory.getMemoryMXBean().heapMemoryUsage
        val usage = heapMemoryUsage.used / (1024 * 1024)
        val max = heapMemoryUsage.max / (1024 * 1024)
        val commited = heapMemoryUsage.committed / (1024 * 1024)
        println("RAM MIN-MAX: $commited-$max MB")
        println("USED: $usage MB (${commited-usage})")
    }

    fun startDiscord(){
        thread{
            PlayersDB.load()
            MarryDB.load()
            bot(token){
                onStart {
                    kordBot = kord
                    event.init()
                    CheckStream.bind()
                    runBlocking {
                        val members  = kordBot.rest.guild.getGuildMembers(guildID).size
                        kordBot.rest.channel.patchChannel(Snowflake(statisticChannel), ChannelModifyPatchRequest(name = Optional("$members")))
                    }
                }
                prefix{"~"}
                mentionEmbed { description = "<@${it.author.id}>" }
                presence { playing("ABOBA inc.") }
                configure {
                    deleteInvocation = false
                    commandReaction = null
                    defaultPermissions = Permissions(Permission.UseApplicationCommands)
                }
            }
        }
    }
}


val f = File("bot.logs")
fun fsb() = listeners {
    on<MessageCreateEvent> {
        if(f.exists().not()) withContext(Dispatchers.IO) {
            f.createNewFile()
        }
        val bw = BufferedWriter(withContext(Dispatchers.IO) {
            FileWriter(f, true)
        })
        val pr = PrintWriter(bw)
        pr.use {
            pr.println("(${message.channelId} ${System.currentTimeMillis().dateToText()}) [${message.author?.username}] ${message.content}")
            bw.flush()
            bw.close()
        }
        println("(${message.channelId} ${System.currentTimeMillis().dateToText()}) [${message.author?.username}] ${message.content}")
        if(message.author?.isBot == true || message.content.startsWith("~")) return@on

        if(message.channelId.isBotChannel() && event.isStartGame){
            var res: Double? = message.content.toDoubleOrNull()
            if(res == null) {
                sendMessage("${message.author?.toLinkedName()}, нужно ввести число!")
                return@on
            }
            res = round(res)
            if(res == round(event.calcRes())){
                val pl = PlayersDB.getByDiscordId(message.author!!.id.value.toLong()) ?: return@on
                if(PlayersDB.getTop().first() == pl){
                    sendMessage("${message.author?.toLinkedName()}, твой хряк и так первый, дай шанс другим!")
                    return@on
                }
                pl.hryak.score += event.score
                PlayersDB.save()
                sendMessage("${message.author?.toLinkedName()}, правильно, ты получаешь ${event.score} кг.")
                event.isStartGame = false
            }
        }
    }
}


