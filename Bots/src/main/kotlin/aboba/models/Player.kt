package aboba.models

class Player {
    var id: Long = 0L
    var discordID: Long = 0L
    var discordNick:String = ""
    var telegramID: Long = 0L
    var telegramNick:String = ""

    var hryak: aboba.models.Hryak = aboba.models.Hryak()
}