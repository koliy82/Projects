package aboba.discord.commands

import aboba.kordBot
import aboba.telegram.tgBot
import com.github.kotlintelegrambot.entities.ChatAction
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.ChatMember
import dev.kord.common.Color
import dev.kord.rest.builder.message.create.embed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import me.jakejmattson.discordkt.arguments.EveryArg
import me.jakejmattson.discordkt.commands.commands
import java.lang.Thread.sleep

fun utilityCommands() = commands("Utility") {
    slash("loading", "Что это? Напиши и узнаешь!"){
        execute{
            val a = author.id.value
            runBlocking {
                respondPublic{
                    color = Color(red = 211, green = 17, blue = 20)
                    title = "Загрузка..."
                    description = "<:red:1059594761801048136><:red:1059594761801048136><:red:1059594761801048136><:red:1059594761801048136><:red:1059594761801048136><:red:1059594761801048136><:gray:1059594760182038710><:gray:1059594760182038710><:gray:1059594760182038710><:gray:1059594760182038710><:gray:1059594760182038710><:gray:1059594760182038710><:gray:1059594760182038710><:gray:1059594760182038710><:gray:1059594760182038710>"
                }
                withContext(Dispatchers.IO) {
                    sleep(5000)
                }
                kordBot.rest.channel.createMessage(channel.id){
                    embed {
                        color = Color(red = 211, green = 17, blue = 20)
                        description = "Загружено, <@${a}>"
                        title = "~     <:red:1059594761801048136> \n" +
                                "     <:red:1059594761801048136> \n" +
                                "<:gray:1059594760182038710><:red:1059594761801048136><:gray:1059594760182038710>"
                    }
                }
            }
        }
    }
    //slash("tg", "test"){
    //    execute() {
    //        tgBot.telegramBot.sendMessage(ChatId.fromChannelUsername("@BeenAxis"), "Privet")
    //    }
    //}
}