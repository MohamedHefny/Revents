package com.mohamedhefny.revent.util

import java.text.SimpleDateFormat
import java.util.*

fun dateFormatter(_date: Long): String {
    val dateFormat = SimpleDateFormat("YYYY-MM-dd")
    val date = Date(_date*1000)
    return dateFormat.format(date)
}

fun dateToMillis(date: String): Long {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    return dateFormat.parse(date).time / 1000
}

fun timeInRange(date: Long, range: Int = 16) =
    date in System.currentTimeMillis().div(1000)..System.currentTimeMillis().plus(
        (86400).times(range)
    )