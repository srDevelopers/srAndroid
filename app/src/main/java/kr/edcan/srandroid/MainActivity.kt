package kr.edcan.srandroid

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kr.edcan.srandroid.databinding.ContentMainHeaderBinding
import kr.edcan.srandroid.databinding.RecyclerTitleBinding
import org.jetbrains.anko.excludeFromRecents
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.noHistory
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {


    var arrayList = ObservableArrayList<Any>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setData()
        setLayout()
    }

    private fun setData() {
        arrayList.add(MainHeader("오늘 일정 없음", "디지털 콘텐츠 경진대회"))
        arrayList.add("공지사항")
        arrayList.add(Notice("Sunrin Networking Day"))
        arrayList.add(Notice("Sunrin Networking Day"))
        arrayList.add(Notice("Sunrin Networking Day"))
        arrayList.add(Notice("Sunrin Networking Day"))
        arrayList.add(Notice("Sunrin Networking Day"))
        arrayList.add(Notice("Sunrin Networking Day"))
        arrayList.add(Notice("Sunrin Networking Day"))
        arrayList.add(Notice("Sunrin Networking Day"))
        arrayList.add(Notice("Sunrin Networking Day"))
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
                            R.layout.recycler_title->{
                                val titleBinding = DataBindingUtil.getBinding<RecyclerTitleBinding>(view)
                                titleBinding.text.text = item.toString()
                            }
                        }
                    }
                })
                .into(mainRecycler)
        openSrNow.setOnClickListener { startActivity<SrNowActivity>() }
    }
}

class MainHeader(val today: String, val tomorrow: String) {

}
