package com.example.presentation.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val context: Context
) : ViewModel(), ContainerHost<MainState, MainSideEffect> {

    override val container: Container<MainState, MainSideEffect> = container(
        initialState = MainState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(MainSideEffect.Toast(message = throwable.message.orEmpty()))
                }
            }
        }
    )

    fun onLogoutClick() = intent {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e("KakaoLogin", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            }
            else {
                Log.i("KakaoLogin", "로그아웃 성공. SDK에서 토큰 삭제됨")
                intent {
                    postSideEffect(MainSideEffect.Toast(message = "로그아웃 성공"))
                    postSideEffect(MainSideEffect.NavigateToLoginActivity)
                }
            }
        }
    }
}

@Immutable
data class MainState(
    val token: String = "",
)

sealed interface MainSideEffect {
    class Toast(val message: String) : MainSideEffect
    object NavigateToLoginActivity:MainSideEffect
}