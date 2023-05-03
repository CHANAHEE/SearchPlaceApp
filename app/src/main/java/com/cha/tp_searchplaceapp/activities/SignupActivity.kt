package com.cha.tp_searchplaceapp.activities

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.OnClickListener
import androidx.appcompat.app.AlertDialog
import com.cha.tp_searchplaceapp.R
import com.cha.tp_searchplaceapp.databinding.ActivitySignupBinding
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity : AppCompatActivity() {

    // 16_ 바인딩 객체 만들기
    lateinit var binding:ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 17_ 툴바를 액션바로 설정
        setSupportActionBar(binding.toolbar)

        // 18_ 액션바에 업버튼 만들기
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_action_arrow_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        // 21_ DB 에 저장하기 위해 버튼 클릭 리스너 만들자
        binding.btnSignup.setOnClickListener { clickSignup() }
    }

    // 19_ 업버튼 누르면 자동으로 실행되는 콜백메서드
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun clickSignup(){
        // 22_ 간단하게 firebase 의 firestore DB 를 이용하여 사용자 정보를 저장하자.

        var email:String = binding.etEmail.text.toString()
        var password:String = binding.etPassword.text.toString()
        var passwordConfirm:String = binding.etPasswordConfirm.text.toString()

        // 23_ 유효성 검사 - 원래는 정규표현식.. 오늘은 패스워드와 패스워드 확인이 맞는지 검사하자.
        // 24_ 코틀린에서는 .equals() 로 문자열 비교할 필요없음!! 그냥 == != 등등으로 하면됨!
        if(password != passwordConfirm){
            AlertDialog.Builder(this).setMessage("패스워드 확인에 문제가 있습니다. 다시 확인하세요.").create().show()
            // 25_ 전체를 잡고, 포커싱 이동해주기
            binding.etPasswordConfirm.selectAll()
            return
        }
        
        // 26_ Firestore DB 의 instance 얻어오자 - firebase 연동작업하기~~~~~

        // 29_ 연동 작업 완료했으면 이제 instance 받아오자.
        val db = FirebaseFirestore.getInstance()

        // 30_ 저장할 값(이메일, 비밀번호) 를 HashMap 으로 저장. 저장할 데이터가 여러개니까 hashmap 으로 한번에 넣어보자.
        val user:MutableMap<String,String> = mutableMapOf()
        user.put("email",email)
        user["password"] = password

        // 31_ 컬렉션의 이름은 "emailUsers" 로 지정하자. [ RDBMS 의 테이블과 같은 역할 ]

        // 36_ 근데 중복된 email 을 가진 회원이 있을 수 있으니 중복체크를 해주자.
        db.collection("emailUsers")
            .whereEqualTo("email",email)
            .get().addOnSuccessListener {

                // 37_ 같은 값을 가진 Document 가 있다면.. 사이즈가 0개 이상일테니..
                if(it.documents.size>0){
                    AlertDialog.Builder(this).setMessage("중복된 이메일이 있습니다. 다시 확인하세요.")
                    binding.etEmail.requestFocus()
                    binding.etEmail.selectAll()
                }else{
                    db.collection("emailUsers").add(user).addOnSuccessListener {
                        AlertDialog.Builder(this)
                            .setMessage("가입 완료!")
                            .setPositiveButton("확인",object : DialogInterface.OnClickListener{
                                override fun onClick(p0: DialogInterface?, p1: Int) {
                                    finish()
                                }
                            })
                            .create()
                            .show()
                    }
                }

                // 39_ addOnSuccessListener 은 쿼리문의 결과라기 보다는 쿼리문이 제대로 되었느냐를 묻는것같음.
                // 40 그래서 내부에서 쿼리문에 결과를 분기를 나눠 해주어야 함.
            }



        // 32_ 없으면 만들고, 있으면 있는 컬렉션을 참조한다.
        // 33_ 랜덤하게 만들어지는 document 명을 회원 id 값으로 사용할 예정!! 그래서 별도의 식별자를 주지 않겠다!
        // 34_ 단, 뭐 이메일, 만들어진 시간 등등을 활용하여 알고리즘을 설정해 개별적인 id 값을 만들수도 있다! 이번에는 firebase 가 만들어주는 랜덤값을 사용하자.
        // db.collection("emailUsers").document().set(user)
        // 35_  랜덤값을 얻어온다면! 아래처럼 사용가능!
        // 38_ 위로 잘라내서 붙여넣었음.


    }
}