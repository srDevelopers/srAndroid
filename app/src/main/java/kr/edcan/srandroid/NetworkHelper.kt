package kr.edcan.srandroid

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by JunseokOh on 2016. 11. 4..
 */

object NetworkHelper {
    val networkInstance: APIRequest
        get() {
            var retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("http://iori.kr").build()
            return retrofit.create(APIRequest::class.java)
        }
}
