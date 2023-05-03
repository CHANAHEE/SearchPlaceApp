package com.cha.tp_searchplaceapp

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        // 52_ kako sdk 초기화 작업
        KakaoSdk.init(this,"8faf5cafa96e1731070ef00dcd9aee33")
    }
}