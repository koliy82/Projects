package aboba.discord.commands

import aboba.discord.utils.isBotChannel
import aboba.discord.utils.timeToNight
import aboba.discord.utils.toLinkedName
import aboba.models.PlayersDB
import dev.kord.common.Color
import me.jakejmattson.discordkt.arguments.EveryArg
import me.jakejmattson.discordkt.commands.commands
import kotlin.math.abs

fun hryakCommands() = commands("Хряки") {
    globalSlash("name", "Дать хряку новое имя") {
        execute(EveryArg) {
            if(channel.id.isBotChannel().not()) return@execute
            val pl = PlayersDB.getOrCreateDiscord(author.id.value.toLong())
            if(args.first.length !in 1..48){
                respond("Размер имени хряка может быть от 1 до 48")
                return@execute
            }
            pl.hryak.name = args.first
            PlayersDB.save()
            respondPublic("Хряк переименован, новое имя - ${args.first}")
        }
    }
    globalSlash("my", "Информация о хряке") {
        execute {
            if(channel.id.isBotChannel().not()) return@execute
            val pl = PlayersDB.getOrCreateDiscord(author.id.value.toLong())
            respondPublic("<@${pl.discordID}>, твой \uD83D\uDC37 ${pl.hryak.name}\nИмеет массу: ${pl.hryak.score} кг.")
        }
    }
    globalSlash("top", "Топ хряков"){
        execute {
            if(channel.id.isBotChannel().not()) return@execute
            var txt = ""
            var counter = 1
            PlayersDB.getTop().forEach {
                txt += "${counter++}. ${it.hryak.name} - ${it.hryak.score} кг [<@${it.discordID}>]\n"
            }
            respondPublic(embed = {
                color = Color(0x00bfff)
                title = "\uD83D\uDC37 Топ-50 хряков \uD83D\uDC37"
                description = txt
            })
        }
    }
    globalSlash("grow", "Простая кормёжка хряка. (-15...30)"){
        execute {
            if(channel.id.isBotChannel().not()) return@execute
            val pl = PlayersDB.getOrCreateDiscord(author.id.value.toLong())
            val hr = pl.hryak
            if(hr.isFeed) {
                respondPublic("${this.author.toLinkedName()}, ты сегодня уже кормил хряка! \uD83D\uDC7F\nСледующая кормёжка через ${timeToNight()}. ⌚")
                return@execute
            }
            val value = (-15..30).random()
            if(hr.isPohudel){
                hr.score += abs(value*2)
                hr.isPohudel = false
                respondPublic("${this.author.toLinkedName()}, в прошлый раз тебе не повезло, но сегодня твой хряк **${hr.name}** поправился на ${abs(value)}x2 кг! \uD83D\uDC7F")
            }else{
                hr.score += value
                if(value<=0){
                    hr.isPohudel = true
                    respondPublic("${this.author.toLinkedName()}, твой хряк **${hr.name}** похудел на ${abs(value)} кг. \uD83D\uDE25 \nНичего, в следующий раз поправится вдвое больше! 😊")
                }else{
                    respondPublic("${this.author.toLinkedName()}, твой хряк **${hr.name}** поправился на $value кг. \uD83D\uDE0A")
                }
            }
            hr.fedL = System.currentTimeMillis()
            PlayersDB.save()
        }
    }
}