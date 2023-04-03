package com.example.tp_searchplaceapp.network

import com.example.tp_searchplaceapp.model.NidUserInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface RetrofitApiService {

    // 72_ 네아로 사용자 정보 API
    @GET("/v1/nid/me")
    fun getNidUserInfo(@Header("Authorization") authorization:String): Call<NidUserInfoResponse>
}