package com.cha.tp_searchplaceapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.cha.tp_searchplaceapp.R
import com.cha.tp_searchplaceapp.databinding.ActivityPlaceUrlBinding

class PlaceUrlActivity : AppCompatActivity() {

    val binding: ActivityPlaceUrlBinding by lazy { ActivityPlaceUrlBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.wv.webViewClient = WebViewClient() // 현재 웹 뷰 안에서 웹문서가 열리도록 ..
        binding.wv.webChromeClient = WebChromeClient() // 웹 문서 안에서 다이얼로그 같은 것을 발동하도록...

        binding.wv.settings.javaScriptEnabled = true // 웹 뷰는 보안문제때문에 기본적으로 JS 동작을 막아놓았다. 이를 허용해주기 위한 코드.

        var placeUrl: String = intent.getStringExtra("place_url") ?: ""
        binding.wv.loadUrl(placeUrl)
    }

    override fun onBackPressed() {
        if(binding.wv.canGoBack()) binding.wv.goBack()
        else super.onBackPressed()
    }
}