package com.example.tp_searchplaceapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tp_searchplaceapp.R
import com.example.tp_searchplaceapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding:ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 10_ 둘러보기 버튼 클릭으로 로그인 없이 바로 Main 화면으로 이동
        binding.tvGo.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        // 11_ 회원가입 버튼 클릭 - 일단 회원가입 액티비티랑 이메일 로그인 액티비티를 만들자.
        binding.tvSignup.setOnClickListener {
            // 12_ 만들고났으면, 회원가입 화면으로전환
            startActivity(Intent(this,SignupActivity::class.java))
        }

        // 13_ 이메일 로그인 버튼 클릭
        binding.layoutEmail.setOnClickListener {
            startActivity(Intent(this,EmailSigninActivity::class.java))
        }

        // 14_ 간편 로그인 버튼 클릭
        binding.ivLoginKakao.setOnClickListener { clickLoginKakao() }
        binding.ivLoginGoogle.setOnClickListener { clickLoginGoogle() }
        binding.ivLoginNaver.setOnClickListener { clickLoginNaver() }
    }

    private fun clickLoginKakao(){

    }

    private fun clickLoginGoogle(){

    }

    private fun clickLoginNaver(){

    }
}