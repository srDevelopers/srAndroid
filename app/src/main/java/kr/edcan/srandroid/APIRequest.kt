package kr.edcan.srandroid

import okhttp3.ResponseBody
import java.util.ArrayList

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by JunseokOh on 2016. 11. 4..
 */

interface APIRequest {
    @GET("/api/parse/sf/{year}/{month}")
    fun getCalendar(@Path("year") year: Int, @Path("month") month: Int): Call<ArrayList<String>>

    @GET("/api/parse/md/{year}/{month}")
    fun getLunch(@Path("year") year: Int, @Path("month") month: Int): Call<ArrayList<ArrayList<String>>>

    @GET("/api/schedule/{grade}/{class}/{day}")
    fun getTimeTable(@Path("grade") grade: Int, @Path("class") classNum: Int, @Path("day") day: Int): Call<ResponseBody>

    @GET("/api/announce")
    fun getAnnounce(): Call<ArrayList<Announce>>
}
