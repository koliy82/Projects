package aboba.discord.events

import aboba.discord.commands.BuyerDB
import aboba.event
import aboba.kordBot
import aboba.discord.utils.getCal
import aboba.discord.utils.mainChannel
import dev.kord.common.Color
import dev.kord.common.entity.Snowflake
import dev.kord.rest.builder.message.create.embed
import kotlinx.coroutines.runBlocking
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class NightEvent {
    var isStartGame = false
    var oneNumber = 0.0
    var twoNumber = 0.0
    var symbol = '+'
    var score = 0

    fun calcRes(): Double {
        when(event.symbol){
            '+' -> return event.oneNumber + event.twoNumber
            '-' -> return event.oneNumber - event.twoNumber
            '*' -> return event.oneNumber * event.twoNumber
            '/' -> return event.oneNumber / event.twoNumber
        }
        return 0.0
    }

    fun init() {
        val now = ZonedDateTime.now(ZoneId.of("Europe/Moscow"))
        var nextRun = now.withHour(0).withMinute(0).withSecond(1)
        if (now > nextRun) nextRun = nextRun.plusSeconds(1)

        val duration: Duration = Duration.between(now, nextRun)
        val initialDelay: Long = duration.seconds

        val scheduler = Executors.newScheduledThreadPool(1)
        scheduler.scheduleAtFixedRate(
            {
                try {
                    val gc = getCal()
                    if(gc[Calendar.MINUTE] == 0 && gc[Calendar.HOUR_OF_DAY] == 0) {
                        oneNumber = (-999..999).random().toDouble()
                        twoNumber = (-999..999).random().toDouble()
                        symbol = listOf('+','-','*','/').random()
                        score = (20..60).random()
                        runBlocking {
                            try {
                                kordBot.rest.channel.createMessage(Snowflake(mainChannel)){
                                    embed {
                                        color = Color(red = 0, green = 255, blue = 255)
                                        title = "Время кормить хряков и решать примерчик!"
                                        field {
                                            name = "Пример (Ответ округлять до целых)"
                                            value = "**${oneNumber.toInt()} $symbol ${twoNumber.toInt()} = ?**"
                                        }
                                        field {
                                            name = "Награда"
                                            value = "**$score кг** сочнейшего корма для вашего хряка!"
                                        }
                                    }
                                }
                            }catch (e:Exception){
                                e.printStackTrace()
                            }
                        }
                        isStartGame = true
                        BuyerDB.check()
                    }
                }catch (ex:Exception){
                    ex.printStackTrace()
                }
            },
            initialDelay,
            TimeUnit.MINUTES.toSeconds(1),
            TimeUnit.SECONDS
        )
        println("Event has init.")
    }
}