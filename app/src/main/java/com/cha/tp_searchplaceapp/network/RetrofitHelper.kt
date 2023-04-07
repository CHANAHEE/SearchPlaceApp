package com.cha.tp_searchplaceapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


// 71_ Retrofit 을 만들어주는 클래스를 별도로 구성
class RetrofitHelper {
    companion object{
        fun getRetrofitInstance(baseUrl: String) : Retrofit{
            val retrofit: Retrofit = Retrofit.Builder()
                                    .baseUrl(baseUrl)
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()

            return retrofit
        }
    }
}