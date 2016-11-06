package kr.edcan.srandroid

import java.util.*

/**
 * Created by JunseokOh on 2016. 11. 4..
 */
data class Announce(val url: String, val title: String)
data class Notice(val notice: Announce)
data class TimeTable(val tableList: String)
data class Lunch(val lunchList: ArrayList<String>)
data class IDCard(
        val name : String,
        val engName : String,
        val barcode: String
)