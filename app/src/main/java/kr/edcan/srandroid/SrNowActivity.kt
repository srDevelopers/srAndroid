package kr.edcan.srandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.github.nitrico.lastadapter.LastAdapter
import com.mobeam.barcodeService.service.MobeamErrorCode
import kotlinx.android.synthetic.main.activity_sr_now.*
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.NetworkInterface
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
        arrayList.run {
            add("")
            add(IDCard("김태윤", "KIM TAE YUN", "S2160250"))
            add(TimeTable(timeTable))
            add(Lunch(lunch))
        }
    }

    private fun setLayout() {
        var timeTableString: String = ""
        var lunchString: String = ""
        var getTimeTable: Call<ResponseBody> = NetworkHelper.networkInstance.getTimeTable(1, 4, 1)
        getTimeTable.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                var result = JSONObject(response!!.body().string()).getJSONArray("arr")
                for (i in 0..result.length() - 1) {
                    timeTableString += result[i].toString() + "\n"
                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.e("asdf", t!!.message)
            }
        })
        Log.e("asdf", timeTableString + "\n")
        LinearLayoutManager(this).let {
            it.orientation = LinearLayoutManager.VERTICAL
            srNowRecycler.layoutManager = it
        }
        LastAdapter.with(arrayList, BR.item)
                .map<String>(R.layout.srnow_header)
                .map<TimeTable>(R.layout.srnow_timetable)
                .map<Lunch>(R.layout.srnow_lunch)
                .map<IDCard>(R.layout.srnow_idcard)
                .into(srNowRecycler)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
