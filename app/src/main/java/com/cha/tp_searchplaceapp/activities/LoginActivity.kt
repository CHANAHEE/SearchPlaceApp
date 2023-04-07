package com.cha.tp_searchplaceapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.cha.tp_searchplaceapp.G
import com.cha.tp_searchplaceapp.databinding.ActivityLoginBinding
import com.cha.tp_searchplaceapp.model.NidUserInfoResponse
import com.cha.tp_searchplaceapp.model.UserAccount
import com.cha.tp_searchplaceapp.network.RetrofitApiService
import com.cha.tp_searchplaceapp.network.RetrofitHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.auth.User
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val binding:ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 10_ 둘러보기 버튼 클릭으로 로그인 없이 바로 Main 화면으로 이동
        binding.tvGo.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // 11_ 회원가입 버튼 클릭 - 일단 회원가입 액티비티랑 이메일 로그인 액티비티를 만들자.
        binding.tvSignup.setOnClickListener {
            // 12_ 만들고났으면, 회원가입 화면으로전환
            startActivity(Intent(this, SignupActivity::class.java))
        }

        // 13_ 이메일 로그인 버튼 클릭
        binding.layoutEmail.setOnClickListener {
            startActivity(Intent(this, EmailSigninActivity::class.java))
        }

        // 14_ 간편 로그인 버튼 클릭
        binding.ivLoginKakao.setOnClickListener { clickLoginKakao() }
        binding.ivLoginGoogle.setOnClickListener { clickLoginGoogle() }
        binding.ivLoginNaver.setOnClickListener { clickLoginNaver() }

        // 51_ 카카오 키 해시값 얻어오기
        val keyHash:String = Utility.getKeyHash(this)
        Log.i("keyhash",keyHash)
    }

    private fun clickLoginKakao(){

        val callback:(OAuthToken?,Throwable?)->Unit = { token, error ->
            if(token != null){
                //Snackbar.make(binding.root,"카카오 로그인 성공",Snackbar.LENGTH_SHORT).show()

                // 55_ 사용자 정보 요청 (회원번호, 이메일 주소)
                UserApiClient.instance.me { user, error ->
                    if(user != null){
                        var id:String = user.id.toString()
                        var email:String = user.kakaoAccount?.email ?: "" // 56_ 엘비스 연산자! : 혹시 null 이라면 이메일의 기본값은  ""
                        //Snackbar.make(binding.root,"$email",Snackbar.LENGTH_SHORT).show()
                        G.userAccount = UserAccount(id,email)

                        // 57_ main 화면으로 이동
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
            }else{
                //Snackbar.make(binding.root,"카카오 로그인 실패",Snackbar.LENGTH_SHORT).show()
            }
        }

        // 54_ 카카오톡이 설치되어 있으면 카톡으로 로그인, 아니면 카카오계정 로그인
        if(UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    private fun clickLoginGoogle(){
        // 58_ Google 에서 검색 [ 안드로이드 구글 로그인 ]

        // 61_ 구글 로그인 옵션 객체 생성 - Builder 이용
        val signInOptions:GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()


        // 60_ 구글 로그인 화면(액티비티) 을 실행하는 Intent 를 통해 로그인 구현
        val intent:Intent = GoogleSignIn.getClient(this,signInOptions).signInIntent
        val launcher:ActivityResultLauncher<Intent>
        resultLauncher.launch(intent)
    }

    val resultLauncher:ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
        object : ActivityResultCallback<ActivityResult>{
            override fun onActivityResult(result: ActivityResult?) {
                // 61_ 로그인 결과를 가져온 인텐트 객체 소환
                val intent:Intent? = result?.data

                // 62_ 돌아온 Intent 로 부터 구글 계정 정보를 가져오는 작업을 수행
                val task:Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)

                val account:GoogleSignInAccount = task.result
                var id : String = account.id.toString()
                var email:String = account.email ?: ""


                G.userAccount = UserAccount(id,email)

                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }
        })

    private fun clickLoginNaver(){

        // 63_ 네이버 로그인 API

        // 65_ 초기화 작업
        NaverIdLoginSDK.initialize(this, "MYE3XTfq5yeKhWBmbbso", "vbrpriDkH2", "장소찾기")

        // 66_ 네이버 로그인 작업
        NaverIdLoginSDK.authenticate(this,object : OAuthLoginCallback{
            override fun onError(errorCode: Int, message: String) {
                Toast.makeText(this@LoginActivity, "error : $message", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Toast.makeText(this@LoginActivity, "failure : $message", Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess() {
                Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()

                // 67_ 사용자 정보를 가져오는 작업을 REST API 으로 하며, 이 작업할 때 접속 토큰이 필요함
                val accessToken:String? = NaverIdLoginSDK.getAccessToken()

                //68_ 토큰 값 확인하자.
                Log.i("token",accessToken!!)

                // 69_ Retrofit 을 이용하여 사용자 정보 API 를 가져오자.

                // 74_ 이제 Retrofit 을 만들자.
                val retrofit = RetrofitHelper.getRetrofitInstance("https://openapi.naver.com")
                retrofit
                    .create(RetrofitApiService::class.java)
                    .getNidUserInfo("Bearer ${accessToken}")
                    .enqueue(object : Callback<NidUserInfoResponse>{
                        override fun onResponse(
                            call: Call<NidUserInfoResponse>,
                            response: Response<NidUserInfoResponse>
                        ) {
                            val userInfoResponse: NidUserInfoResponse? = response.body()
                            val id: String = userInfoResponse?.response?.id ?: ""
                            val email: String = userInfoResponse?.response?.email ?: ""

                            Toast.makeText(this@LoginActivity, "${email}", Toast.LENGTH_SHORT).show()
                            G.userAccount = UserAccount(id,email)

                            // main 화면으로 이동
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }

                        override fun onFailure(call: Call<NidUserInfoResponse>, t: Throwable) {
                            Toast.makeText(this@LoginActivity, "회원정보 불러오기 실패 : ${t.message}", Toast.LENGTH_SHORT).show()
                        }

                    })

            }

        })
    }
}