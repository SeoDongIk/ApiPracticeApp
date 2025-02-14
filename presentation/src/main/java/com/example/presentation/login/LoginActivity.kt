package com.example.presentation.login

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kr.co.fastcampus.presentation.theme.ApiPracticeTheme
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApiPracticeTheme {
                LoginScreen()
            }
        }
    }
}


