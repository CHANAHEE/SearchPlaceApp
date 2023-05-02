package com.cha.searchplace.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import com.cha.searchplace.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 0_ setContentView(R.layout.activity_splash) -> 테마를 이용하여 화면을 구현할 것임.

        // 4_ 1.5 초 정도 후에 로그인 액티비티 로 전환하자. 일단 로그인 액티비티부터 만들자.
//        Handler(Looper.getMainLooper()).postDelayed( object : Runnable {
//            override fun run() {
//
//            }
//        },1500)

        // 5_ lambda 표기로 축약하자.
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        },1500)
    }
}