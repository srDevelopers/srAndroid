package kr.edcan.srandroid

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import java.util.*

class SrNowActivity : AppCompatActivity() {

    var arrayList: ArrayList<Any> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sr_now)
        setData()
        setLayout()
    }

    private fun setData() {
        // TODO Get TimeTable, Lunch
        val timeTable: ArrayList<String> = ArrayList()
        val lunch: ArrayList<String> = ArrayList()
        arrayList.run{
            add("")
            add(IDCard("오준석", "Junseok Oh", "S123123"))
            add(TimeTable(timeTable))
            add(Lunch(lunch))
        }
    }

    private fun setLayout() {
        LastAdapter.with(arrayList, BR.item)
                .map<String>(R.layout.srnow_header)
                .map<TimeTable>(R.layout.srnow_timetable)
                .map<Lunch>(R.layout.srnow_lunch)
                .map<IDCard>(R.layout.srnow_idcard)
                .onBindListener(object : LastAdapter.OnBindListener {
                    override fun onBind(item: Any, view: View, type: Int, position: Int) {
                    }
                })

    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
