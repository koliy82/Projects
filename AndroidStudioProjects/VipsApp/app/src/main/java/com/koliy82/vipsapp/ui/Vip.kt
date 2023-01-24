package com.koliy82.vips.Recycler

import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*

class Vip {
    fun getTimeZone() : TimeZone = TimeZone.getTimeZone(ZoneId.of("Europe/Moscow"))

    fun getCurrentTime():String{
        val format = SimpleDateFormat("dd.MM.YYYY - HH:mm")
        format.timeZone = getTimeZone()
        return format.format(Date())
    }
}

object VipList {
    var list = ArrayList<Vip>()

    fun addList(){
        list.add(Vip())
    }

    fun delList(id: Int){
        list.removeAt(id)
    }
}