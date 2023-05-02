package com.cha.searchplace.network

import com.cha.searchplace.model.KakaoSearchPlaceResponse
import com.cha.searchplace.model.NidUserInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitApiService {

    // 72_ 네아로 사용자 정보 API
    @GET("/v1/nid/me")
    fun getNidUserInfo(@Header("Authorization") authorization:String): Call<NidUserInfoResponse>

    // kakao keyword 장소 검색 api 에 대한 요청작업 명세
    @Headers("Authorization: KakaoAK 5cb8aea6bca7e3f32e2ac7b014b4a64b")
    @GET("/v2/local/search/keyword.json")
    fun getSearchPlace(@Query("query") query: String, @Query("y")  latitude: String,@Query("x") longitude: String): Call<KakaoSearchPlaceResponse>
}