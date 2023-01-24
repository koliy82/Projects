package aboba.models

import aboba.discord.utils.getMoscowTimeMillis

class Marry(var firstPartner: Player, var secondPartner: Player, val startDay: Long = getMoscowTimeMillis()) {
    val firstPartnerID: Long
        get() = firstPartner.telegramID
    val secondPartnerID: Long
        get() = secondPartner.telegramID

    fun times(): String{
       val time = getMoscowTimeMillis() - startDay
        val seconds = time / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val years = days / 365
        return when {
            years > 0 -> "$years лет"
            days > 0 -> "$days дней ${hours%24} часов"
            hours > 0 -> "$hours часов ${minutes%60} минут"
            minutes > 0 -> "$minutes минут ${seconds%60} секунд"
            else -> "$seconds секунд"
        }
    }
}