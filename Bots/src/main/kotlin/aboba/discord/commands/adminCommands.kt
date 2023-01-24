package aboba.discord.commands

import aboba.discord.utils.adminID
import aboba.discord.utils.isBotChannel
import aboba.discord.utils.toLinkedName
import dev.kord.common.entity.Permission
import dev.kord.common.entity.Permissions
import me.jakejmattson.discordkt.arguments.IntegerArg
import me.jakejmattson.discordkt.arguments.UserArg
import me.jakejmattson.discordkt.commands.commands

//fun adminCommands() = commands("Команды Админов", requiredPermissionLevel = Permissions(Permission.Unknown(1L shl 11))) {
//    slash("add", "Добавить хряку очки") {
//        execute(UserArg, IntegerArg) {
//            if(channel.id.isBotChannel().not()) return@execute
//            if(author.id.value!= adminID) return@execute
//            val hr = HryakDBFile.getOrCreate(args.first.id.value)
//            hr.score += args.second
//            HryakDBFile.save()
//            respond("Хряку **${hr.name}** [${args.first.toLinkedName()}] добавлено ${args.second} кг")
//        }
//    }
//
//    slash("remove", "Забрать у хряка очки") {
//        execute(UserArg, IntegerArg) {
//            if(channel.id.isBotChannel().not()) return@execute
//            if(author.id.value!= adminID) return@execute
//            val hr = HryakDBFile.getOrCreate(args.first.id.value)
//            hr.score -= args.second
//            HryakDBFile.save()
//            respond("Хряку **${hr.name}** [${args.first.toLinkedName()}] добавлено ${args.second} кг")
//        }
//    }
//
//    slash("myPermissions", "Показывает твои права"){
//        execute {
//            val mem = getMember()
//            val perm = mem!!.getPermissions()
//            println(perm.code)
//
//            respond( "u permission code = ${perm.code}")
//        }
//    }
//}
