package aboba.telegram

import aboba.discord.utils.dateToText
import aboba.discord.utils.getMoscowTimeMillis
import aboba.models.MarryDB
import aboba.models.PlayersDB
import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.*
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import kotlin.concurrent.thread

object tgBot {
    lateinit var telegramBot: Bot
    val tgMainToken = "1684356520:AAHpmfTBbR3rfi7jR9P2gMQgKveNaTn7f7M"
    val tgTestToken = "5805694979:AAH9xKKWPlw2st53O8H9piOIo4UIOXqXK1o"
    fun start(){
        thread {
            telegramBot = bot{
                token = tgMainToken
                timeout = 30
                dispatch {
//                    addHandler(object : Handler{
//                        override fun checkUpdate(update: Update): Boolean {
//                            return true
//                        }
//
//                        override fun handleUpdate(bot: Bot, update: Update) {
//                            update.message?.from?.let {
//                                if(update.message?.leftChatMember != null){
//                                    println("DELETE USER: ${it.username} -> ${it.id}")
//                                }else{
//                                    println("NEW USER: ${it.username} -> ${it.id}")
//                                }
//                            }
//                        }
//                    })
                    //message(Filter.Reply or Filter.Forward) {
                    //bot.sendMessage(ChatId.fromId(message.chat.id), text = "someone is replying or forwarding messages ...")
                    //}
                    command("help"){
                        var txt = "Список команд:\n\n"
                        bot.getMyCommands().get().forEach {
                            txt += "/${it.command} - ${it.description}\n"
                        }
                        bot.sendMessage(ChatId.fromId(message.chat.id), text = txt)
                    }

                    command("info") {
                        val user = message.from ?: return@command
                        val msg = message.replyToMessage
                        if (msg == null) {
                            bot.sendMessage(ChatId.fromId(message.chat.id), "Нужно ответить на сообщение пользователя, которого хотите проверить!")
                            return@command
                        }
                        val toUser = msg.from ?: return@command

                        bot.sendMessage(
                            chatId = ChatId.fromId(message.chat.id),
                            text = "FromUser:\n id - ${user.id}\n username - @${user.username}\n isBot ${user.isBot}\n" +
                                    "ToUser: id - ${toUser.id} \n username - @${toUser.username} \n isBot - ${toUser.isBot} \n${getMoscowTimeMillis().dateToText()}\n"
                        )
                        println(msg)
                    }

                    command("браки") {
                        val list = MarryDB.getMarries()
                        var text = "\uD83D\uDC8D БРАКИ (Пар:${list.size})\n\n"
                        var i = 1
                        list.forEach {
                            val user1 = bot.getChatMember(ChatId.fromId(message.chat.id), it.secondPartnerID).get().user
                            val user2 = bot.getChatMember(ChatId.fromId(message.chat.id), it.firstPartnerID).get().user
                            text += "${i++}. <a href=\"https://t.me/${user1.username}\">${user1.firstName} ${user1.lastName ?:""}</a> + <a href=\"https://t.me/${user2.username}\">${user2.firstName} ${user2.lastName ?: ""}</a> (${it.times()})\n"
                        }
                        bot.sendMessage(ChatId.fromId(message.chat.id), text, disableNotification = true, disableWebPagePreview = true, parseMode = ParseMode.HTML)
                    }

                    command("braks"){
                        val list = MarryDB.getMarries()
                        var text = "\uD83D\uDC8D БРАКИ (ПАР: ${list.size})\n\n"
                        var i = 1
                        list.forEach {
                            val user1 = bot.getChatMember(ChatId.fromId(message.chat.id), it.secondPartnerID).get().user
                            val user2 = bot.getChatMember(ChatId.fromId(message.chat.id), it.firstPartnerID).get().user
                            //text += "${i++}. [${user1.firstName} ${user1.lastName ?:""}](https://t.me/${user1.id})  + [${user2.firstName} ${user2.lastName?:""}](https://t.me/${user2.id}) (${it.times()})\n"
                            text += "${i++}. <a href=\"https://t.me/${user1.username}\">${user1.firstName} ${user1.lastName ?:""}</a> + <a href=\"https://t.me/${user2.username}\">${user2.firstName} ${user2.lastName ?: ""}</a> (${it.times()})\n"
                        }
                        bot.sendMessage(ChatId.fromId(message.chat.id), text, disableNotification = true, disableWebPagePreview = true, parseMode = ParseMode.HTML)
                    }

                    command("развод") {
                        val user = message.from ?: return@command
                        val dbUser = PlayersDB.getOrCreateTelegram(user.id) ?: return@command
                        val marry = MarryDB.getOrNullMarry(dbUser)
                        if (marry == null) {
                            bot.sendMessage(ChatId.fromId(message.chat.id), "Вы не состоите в браке")
                            return@command
                        }
                        val firstname = bot.getChatMember(ChatId.fromId(message.chat.id), marry.firstPartnerID).get().user.username ?: "null"
                        val secondname = bot.getChatMember(ChatId.fromId(message.chat.id), marry.secondPartnerID).get().user.username ?: "null"
                        bot.sendMessage(
                            ChatId.fromId(message.chat.id), "Ты уверен, что хочешь разорвать брак между @$firstname и @$secondname? \uD83D\uDC94",
                            replyMarkup = InlineKeyboardMarkup.createSingleRowKeyboard(
                                InlineKeyboardButton.CallbackData(
                                    text = "Да.",
                                    callbackData = "Развод ${user.id}"
                                )
                            )
                        )
                        var isClicked = false

                        callbackQuery("Развод ${user.id}") {
                            if (isClicked) return@callbackQuery
                            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
                            val from = callbackQuery.from ?: return@callbackQuery
                            if (from.id != user.id) {
                                bot.sendMessage(ChatId.fromId(chatId), "Вы не ${user.username}")
                                return@callbackQuery
                            }
                            bot.sendMessage(ChatId.fromId(chatId), "библетумб")
                            val pl = PlayersDB.getByTelegramId(from.id) ?: return@callbackQuery
                            MarryDB.deleteMarry(dbUser)
                            MarryDB.save()
                            isClicked = true
                        }
                    }

                    command("endsex"){
                        val user = message.from ?: return@command
                        val dbUser = PlayersDB.getOrCreateTelegram(user.id) ?: return@command
                        val marry = MarryDB.getOrNullMarry(dbUser)
                        if (marry == null) {
                            bot.sendMessage(ChatId.fromId(message.chat.id), "Вы не состоите в браке")
                            return@command
                        }
                        val firstname = bot.getChatMember(ChatId.fromId(message.chat.id), marry.firstPartnerID).get().user.username ?: "null"
                        val secondname = bot.getChatMember(ChatId.fromId(message.chat.id), marry.secondPartnerID).get().user.username ?: "null"
                        bot.sendMessage(
                            ChatId.fromId(message.chat.id),
                            "Ты уверен, что хочешь разорвать брак между @$firstname и @$secondname? \uD83D\uDC94",
                            replyMarkup = InlineKeyboardMarkup.createSingleRowKeyboard(
                                InlineKeyboardButton.CallbackData(
                                    text = "Да.",
                                    callbackData = "Развод ${user.id}"
                                )
                            )
                        )
                        var isClicked = false

                        callbackQuery("Развод ${user.id}") {
                            if (isClicked) return@callbackQuery
                            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
                            val from = callbackQuery.from ?: return@callbackQuery
                            if (from.id != user.id) {
                                bot.sendMessage(ChatId.fromId(chatId), "Вы не ${user.username}")
                                return@callbackQuery
                            }
                            bot.sendMessage(ChatId.fromId(chatId), "библетумб")
                            val pl = PlayersDB.getByTelegramId(from.id) ?: return@callbackQuery
                            MarryDB.deleteMarry(dbUser)
                            MarryDB.save()
                            isClicked = true
                        }
                    }

                    command("брак") {
                        val user = message.from ?: return@command
                        val dbUser = PlayersDB.getOrCreateTelegram(user.id) ?: return@command
                        val marry = MarryDB.getOrNullMarry(dbUser)
                        if (marry == null) {
                            bot.sendMessage(ChatId.fromId(message.chat.id), "Вы не состоите в браке")
                            return@command
                        }
                        val firstname = bot.getChatMember(ChatId.fromId(message.chat.id), marry.firstPartnerID).get().user.username ?: "null"
                        val secondname = bot.getChatMember(ChatId.fromId(message.chat.id), marry.secondPartnerID).get().user.username ?: "null"
                        bot.sendMessage(ChatId.fromId(message.chat.id), "Брак между @$firstname и @$secondname\nДлиться уже ${marry.times()}")
                    }

                    command("brak"){
                        val user = message.from ?: return@command
                        val dbUser = PlayersDB.getOrCreateTelegram(user.id) ?: return@command
                        val marry = MarryDB.getOrNullMarry(dbUser)
                        if (marry == null) {
                            bot.sendMessage(ChatId.fromId(message.chat.id), "Вы не состоите в браке")
                            return@command
                        }
                        val firstname = bot.getChatMember(ChatId.fromId(message.chat.id), marry.firstPartnerID).get().user.username ?: "null"
                        val secondname = bot.getChatMember(ChatId.fromId(message.chat.id), marry.secondPartnerID).get().user.username ?: "null"
                        bot.sendMessage(ChatId.fromId(message.chat.id), "Брак между @$firstname и @$secondname\nДлиться уже ${marry.times()}")
                    }

                    command("предложение") {
                        val user = message.from ?: return@command
                        val msg = message.replyToMessage
                        if (msg == null) {
                            bot.sendMessage(ChatId.fromId(message.chat.id), "Нужно ответить на сообщение пользователя, которому хотите предложить брак")
                            return@command
                        }
                        val toUser = msg.from ?: return@command
                        if (user.id == toUser.id) {
                            bot.sendMessage(ChatId.fromId(message.chat.id), "Вы не можете состоять в браке с самим собой")
                            return@command
                        }
                        if(toUser.isBot){
                            bot.sendMessage(ChatId.fromId(message.chat.id), "Не трогай бота, ему ещё жить да жить")
                            return@command
                        }
                        val userDB = PlayersDB.getOrCreateTelegram(user.id) ?: return@command
                        val marry = MarryDB.getOrNullMarry(userDB)
                        if (marry != null) {
                            bot.sendMessage(ChatId.fromId(message.chat.id), "Ты уже состоишь в браке!")
                            return@command
                        }
                        bot.sendMessage(
                            ChatId.fromId(message.chat.id),
                            "\uD83D\uDC8D @${toUser.username?:toUser.firstName}, минуточку внимания.\n" +
                                    "\uD83D\uDC96 @${user.username?:user.firstName} сделал вам предложение руки и сердца.",
                            replyMarkup = InlineKeyboardMarkup.createSingleRowKeyboard(
                                InlineKeyboardButton.CallbackData(
                                    text = "❤️\u200D\uD83D\uDD25 Согласиться",
                                    callbackData = "Согласиться ${toUser.id}"
                                ),
                                InlineKeyboardButton.CallbackData(
                                    text = "\uD83D\uDC94 Отказаться",
                                    callbackData = "Отказаться ${toUser.id}"
                                )
                            )
                        )
                        var isClicked = false

                        callbackQuery("Согласиться ${toUser.id}") {
                            if (isClicked) return@callbackQuery
                            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
                            val from = callbackQuery.from ?: return@callbackQuery
                            if (from.id != toUser.id) {
                                bot.answerCallbackQuery(callbackQuery.id, "Вы не ${toUser.username}", true)
                                return@callbackQuery
                            }
                            val toUserDB = PlayersDB.getOrCreateTelegram(toUser.id)
                            if (MarryDB.getOrNullMarry(toUserDB) != null) {
                                return@callbackQuery
                            }
                            MarryDB.addMarry(userDB, toUserDB)
                            MarryDB.save()
                            isClicked = true
                            bot.sendMessage(ChatId.fromId(chatId), "@${user.username} и @${toUser.username} теперь вместе ❤️\u200D\uD83D\uDD25")
                        }

                        callbackQuery("Отказаться ${toUser.id}") {
                            if (isClicked) return@callbackQuery
                            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
                            val from = callbackQuery.from ?: return@callbackQuery
                            if (from.id != toUser.id) {
                                bot.answerCallbackQuery(callbackQuery.id, "Вы не ${toUser.username}", true)
                                return@callbackQuery
                            }
                            bot.sendMessage(ChatId.fromId(chatId), "Отказ \uD83D\uDDA4")
                            isClicked = true
                        }
                    }

                    command("gosex"){
                        val user = message.from ?: return@command
                        val msg = message.replyToMessage
                        if (msg == null) {
                            bot.sendMessage(ChatId.fromId(message.chat.id), "Нужно ответить на сообщение пользователя")
                            return@command
                        }
                        val toUser = msg.from ?: return@command
                        if (user.id == toUser.id) {
                            bot.sendMessage(ChatId.fromId(message.chat.id), "Вы не можете состоять в браке с самим собой")
                            return@command
                        }
                        if(toUser.isBot){
                            bot.sendMessage(ChatId.fromId(message.chat.id), "Не трогай бота, ему ещё жить да жить")
                            return@command
                        }
                        val userDB = PlayersDB.getOrCreateTelegram(user.id) ?: return@command
                        val marry = MarryDB.getOrNullMarry(userDB)
                        if (marry != null) {
                            bot.sendMessage(ChatId.fromId(message.chat.id), "Ты уже состоишь в браке!")
                            return@command
                        }
                        bot.sendMessage(
                            ChatId.fromId(message.chat.id),
                            "\uD83D\uDC8D @${toUser.username}, минуточку внимания.\n" +
                                    "\uD83D\uDC96 @${user.username} сделал вам предложение руки и сердца.",
                            replyMarkup = InlineKeyboardMarkup.createSingleRowKeyboard(
                                InlineKeyboardButton.CallbackData(
                                    text = "❤️\u200D\uD83D\uDD25 Согласиться",
                                    callbackData = "Согласиться ${toUser.id}"
                                ),
                                InlineKeyboardButton.CallbackData(
                                    text = "\uD83D\uDC94 Отказаться",
                                    callbackData = "Отказаться ${toUser.id}"
                                )
                            )
                        )
                        var isClicked = false

                        callbackQuery("Согласиться ${toUser.id}") {
                            if (isClicked) return@callbackQuery
                            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
                            val from = callbackQuery.from ?: return@callbackQuery
                            if (from.id != toUser.id) {
                                bot.answerCallbackQuery(callbackQuery.id, "Вы не ${toUser.username}", true)
                                return@callbackQuery
                            }
                            val toUserDB = PlayersDB.getOrCreateTelegram(toUser.id)
                            if (MarryDB.getOrNullMarry(toUserDB) != null) {
                                return@callbackQuery
                            }
                            MarryDB.addMarry(userDB, toUserDB)
                            MarryDB.save()
                            isClicked = true
                            bot.sendMessage(ChatId.fromId(chatId), "@${user.username} и @${toUser.username} теперь вместе ❤️\u200D\uD83D\uDD25")
                        }

                        callbackQuery("Отказаться ${toUser.id}") {
                            if (isClicked) return@callbackQuery
                            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
                            val from = callbackQuery.from ?: return@callbackQuery
                            if (from.id != toUser.id) {
                                bot.answerCallbackQuery(callbackQuery.id, "Вы не ${toUser.username}", true)
                                return@callbackQuery
                            }
                            bot.sendMessage(ChatId.fromId(chatId), "Отказ \uD83D\uDDA4")
                            isClicked = true
                        }
                    }

                    //command("discord"){
                    //    runBlocking {
                    //        val id = Snowflake(497776932503683086L)
                    //        Snowflake("")
                    //        val user = kordBot.getUser(id) ?: return@runBlocking
                    //        user.sendPrivateMessage("Ты?")
                    //    }
                    //}

                    //command("inlineButtons") {
                    //    val inlineKeyboardMarkup = InlineKeyboardMarkup.create(
                    //        listOf(InlineKeyboardButton.CallbackData(text = "Test Inline Button", callbackData = "testButton")),
                    //        listOf(InlineKeyboardButton.CallbackData(text = "Show alert", callbackData = "showAlert"))
                    //    )
                    //    bot.sendMessage(
                    //        chatId = ChatId.fromId(message.chat.id),
                    //        text = "Hello, inline buttons!",
                    //        replyMarkup = inlineKeyboardMarkup
                    //    )
                    //}
                    //
                    //callbackQuery("testButton") {
                    //    val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
                    //    bot.sendMessage(ChatId.fromId(chatId), callbackQuery.data)
                    //}

                    //callbackQuery(
                    //    callbackData = "NotYouButton",
                    //    callbackAnswerText = "Это не твоя кнопка, создай себе свою!",
                    //    callbackAnswerShowAlert = true
                    //) {}

                    command("sexdel"){
                        try {
                            if(message.from!!.id != 725757421L) return@command
                            MarryDB.deleteById(args[0].toInt())
                        }catch (e: Exception){
                            bot.sendMessage(ChatId.fromId(message.chat.id), "Ошибка ${e.message}")
                        }
                    }

                    telegramError {
                        println(error.getErrorMessage())
                    }
                }
            }

            telegramBot.startPolling()
            println("Telegram Bot started")
        }
    }
}