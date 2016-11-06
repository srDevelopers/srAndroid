package kr.edcan.srandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.github.nitrico.lastadapter.LastAdapter
import com.mobeam.barcodeService.service.MobeamErrorCode
import kotlinx.android.synthetic.main.activity_sr_now.*
import okhttp3.ResponseBody
import org.jetbrains.anko.UI
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
        var timeTableString: String = ""
        var lunchString: String = ""
        var getTimeTable: Call<ResponseBody> = NetworkHelper.networkInstance.getTimeTable(1, 4, 1)
        getTimeTable.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                var result = JSONObject(response!!.body().string()).getJSONArray("arr")
                for (i in 0..result.length() - 1) {
                    timeTableString += result[i].toString() + "\n"
                }
                Log.e("asdf", timeTableString)
                val lunch: ArrayList<String> = ArrayList()
                arrayList.run {
                    add("")
                    add(IDCard("김태윤", "KIM TAE YUN", "S2160250"))
                    add(TimeTable(timeTableString))
                    add(Lunch(lunch))
                }
                var date = Calendar.getInstance()
                var day = date.get(Calendar.YEAR).toString()
                var month = if (date.get(Calendar.MONTH) + 1 > 9) (date.get(Calendar.MONTH) + 1).toString() else "0" + (date.get(Calendar.MONTH) + 1).toString()
                Log.e("asdf", day + " " + month)

//                NetworkHelper.networkInstance.getLunch(date.get(Calendar.YEAR), date.get(Calendar.MONTH + 1)).enqueue(object : Callback<ArrayList<ArrayList<String>>> {
//                    override fun onResponse(call: Call<ArrayList<ArrayList<String>>>?, response: Response<ArrayList<ArrayList<String>>>?) {
//                        var responseBody = response!!.body()
//                        Log.e("asdf", responseBody[date.get(Calendar.DAY_OF_MONTH)].size.toString())
//                    }
//
//                    override fun onFailure(call: Call<ArrayList<ArrayList<String>>>?, t: Throwable?) {
//                    }
//                })
                UI {
                    setLayout()
                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.e("asdf", t!!.message)
            }
        })
        // TODO Get TimeTable, Lunch

    }

    private fun setLayout() {

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
