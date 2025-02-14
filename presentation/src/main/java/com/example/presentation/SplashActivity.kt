package com.example.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.presentation.login.LoginActivity
import com.example.presentation.main.MainActivity
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val isLoggedIn = AuthApiClient.instance.hasToken()
            if (isLoggedIn) {
                UserApiClient.instance.accessTokenInfo { _, error ->
                    if (error != null) {
                        if (error is KakaoSdkError && error.isInvalidTokenError() == true) {
                            startActivity(
                                Intent(
                                    this@SplashActivity, LoginActivity::class.java
                                ).apply {
                                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                }
                            )
                        }
                        else {
                            //기타 에러
                        }
                    }
                    else {
                        //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                            if (error != null) {
                                Log.e("KakaoLogin", "토큰 정보 보기 실패", error)
                            }
                            else if (tokenInfo != null) {
                                Log.i("KakaoLogin", "토큰 정보 보기 성공" +
                                        "\n회원번호: ${tokenInfo.id}" +
                                        "\n만료시간: ${tokenInfo.expiresIn} 초")
                            }
                        }
                        // 로그인 되었을 떄
                        startActivity(
                            Intent(
                                this@SplashActivity, MainActivity::class.java
                            ).apply {
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                        )
                    }
                }
            } else {
                // 로그인 안되었을 때
                startActivity(
                    Intent(
                        this@SplashActivity, LoginActivity::class.java
                    ).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
            }
        }
    }
}