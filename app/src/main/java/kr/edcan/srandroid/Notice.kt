package kr.edcan.srandroid

import java.util.*

/**
 * Created by JunseokOh on 2016. 11. 4..
 */
data class Notice(val title: String)
data class TimeTable(val tableList: ArrayList<String>)
data class Lunch(val lunchList: ArrayList<String>)
data class IDCard(
        val name : String,
        val engName : String,
        val barcode: String
)