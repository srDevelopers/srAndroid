package kr.edcan.srandroid

import java.util.ArrayList

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by JunseokOh on 2016. 11. 4..
 */

interface APIRequest {
    @GET("/api/parse/sf/{year}/{month}")
    fun getCalendar(@Path("year") year: Int, @Path("month") month: Int): Call<ArrayList<String>>

    @GET("/api/parse/md/{year}/{month}")
    fun getLunch(@Path("year") year: Int, @Path("month") month: Int): Call<ArrayList<ArrayList<String>>>
}
