package com.example.tp_searchplaceapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.tp_searchplaceapp.G
import com.example.tp_searchplaceapp.R
import com.example.tp_searchplaceapp.databinding.ActivityEmailSigninBinding
import com.example.tp_searchplaceapp.model.UserAccount
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User

class EmailSigninActivity : AppCompatActivity() {

    lateinit var binding: ActivityEmailSigninBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailSigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_action_arrow_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.btnSignin.setOnClickListener { clickSignIn() }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun clickSignIn() {
        var email:String = binding.etEmail.text.toString()
        var password:String = binding.etPassword.text.toString()

        val db = FirebaseFirestore.getInstance()
        db.collection("emailUsers")
            .whereEqualTo("email",email)
            .whereEqualTo("password",password)
            .get().addOnSuccessListener {
                if(it.documents.size>0){
                    // 41_ 로그인 성공!
                    var id:String = it.documents[0].id
                    G.userAccount = UserAccount(id,email)

                    // 44_ 로그인 성공했으니.. 곧바로 MainActivity 로 이동
                    val intent = Intent(this,MainActivity::class.java)
                    // 45_ 기존 task 의 모든 액티비티들을 제거하고 새로운 작업처럼 새로운 task 를 시작하자.
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)


                    startActivity(intent)
                }
                else {
                    // 46_ 로그인 실패
                    // 47_ create() 대신 show 만 해도 가능하다
                    AlertDialog.Builder(this).setMessage("이메일 혹은 비밀번호가 잘못되었습니다.").show()
                    binding.etEmail.requestFocus()
                    binding.etEmail.selectAll()
                }
            }
    }
}