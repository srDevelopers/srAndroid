package kr.edcan.srandroid

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import org.json.JSONObject
import kotlin.properties.Delegates

/**
 * Created by JunseokOh on 2016. 11. 7..
 */
class DataManger(context: Context) {
    var pref by Delegates.notNull<SharedPreferences>()
    var editor by Delegates.notNull<SharedPreferences.Editor>()
    init {
        pref = PreferenceManager.getDefaultSharedPreferences(context)
        editor = pref.edit()
    }
    fun saveCalendar(data: JSONObject) {
//        editor.putString("calendar", data.toString())
//        Log.e("asdf", JSONObject(data.toString().))
    }
}