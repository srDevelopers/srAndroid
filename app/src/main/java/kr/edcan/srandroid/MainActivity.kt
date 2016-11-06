package kr.edcan.srandroid

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kr.edcan.srandroid.databinding.ContentMainHeaderBinding
import kr.edcan.srandroid.databinding.RecyclerTitleBinding
import okhttp3.ResponseBody
import org.jetbrains.anko.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {


    var noticeArr: ArrayList<Announce> = ArrayList()
    var arrayList = ObservableArrayList<Any>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setData()
    }

    private fun setData() {
        var getAnnounce: Call<ArrayList<Announce>> = NetworkHelper.networkInstance.getAnnounce()
        getAnnounce.enqueue(object : Callback<ArrayList<Announce>> {
            override fun onResponse(call: Call<ArrayList<Announce>>?, response: Response<ArrayList<Announce>>?) {
                var responseBody = response!!.body()
                noticeArr = responseBody
                arrayList.add(MainHeader("오늘 일정 없음", "디지털 콘텐츠 경진대회"))
                arrayList.add("공지사항")
                for (s in 0..noticeArr.size - 1) {
                    arrayList.add(Notice(noticeArr[s]))
                }
                UI {
                    setLayout()
                }
            }

            override fun onFailure(call: Call<ArrayList<Announce>>?, t: Throwable?) {
                Log.e("asdf", t!!.message)
            }
        })

    }

    private fun setLayout() {
        toolbar.setTitleTextColor(Color.WHITE)
        LinearLayoutManager(this).let {
            it.orientation = LinearLayoutManager.VERTICAL
            mainRecycler.layoutManager = it
        }
        LastAdapter.with(arrayList, BR.item)
                .map<MainHeader>(R.layout.content_main_header)
                .map<String>(R.layout.recycler_title)
                .map<Notice>(R.layout.item_main)
                .onBindListener(object
                : LastAdapter.OnBindListener {
                    override fun onBind(item: Any, view: View, type: Int, position: Int) {
                        when (type) {
                            R.layout.content_main_header -> {
                                val headerBinding = DataBindingUtil.getBinding<ContentMainHeaderBinding>(view)
                                headerBinding.scoreManage.setOnClickListener { startActivity(intentFor<SrNowActivity>().noHistory()) }
                            }
                            R.layout.recycler_title -> {
                                val titleBinding = DataBindingUtil.getBinding<RecyclerTitleBinding>(view)
                                titleBinding.text.text = item.toString()
                            }
                        }
                    }
                })
                .onClickListener(object : LastAdapter.OnClickListener {
                    override fun onClick(item: Any, view: View, type: Int, position: Int) {
                        when (type) {
                            R.layout.item_main ->
                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse((arrayList[position] as Notice).notice.url)))
                        }
                    }
                })
                .into(mainRecycler)
        openSrNow.setOnClickListener { startActivity<SrNowActivity>() }
    }
}

class MainHeader(val today: String, val tomorrow: String) {

}
