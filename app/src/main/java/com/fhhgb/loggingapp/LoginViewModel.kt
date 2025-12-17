package com.fhhgb.loggingapp

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val sharedPreferences =
        application.getSharedPreferences("credentials_prefs", Context.MODE_PRIVATE)

    private val username = "admin"
    private val correctPassword = "password123"

    data class LoginState(
        val userName: String = "",
        val password: String = "",
        val isError: Boolean = false,
        val isLoggedIn: Boolean = false,
    )

    sealed class LoginAction {
        data class OnUserNameChanged(val input: String) : LoginAction()
        data class OnUserPasswordChanged(val input: String) : LoginAction()
        object VerifyLoginData : LoginAction()
    }

    val loginState = MutableStateFlow(value = LoginState())

    init {
        loginState.value = loginState.value.copy(
            isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false),
            userName = sharedPreferences.getString("savedUsername", "")!!
        )
    } // initialize for not logged in state and empty username

    private fun checkCredentials(userName: String, password: String) {
        if (userName == username && password == correctPassword) {
            loginState.value = loginState.value.copy(
                isLoggedIn = true,
                isError = false,
            )
            sharedPreferences.edit {
                putBoolean("isLoggedIn", true)
                putString("savedUsername", userName) // Store the username on successful login
            }
        } else {
            loginState.value = loginState.value.copy(
                isLoggedIn = false,
                isError = true,
            )
            sharedPreferences.edit {
                putBoolean("isLoggedIn", false)
            }
        }
    }

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.VerifyLoginData -> {
                checkCredentials(
                    loginState.value.userName,
                    loginState.value.password
                )
            }

            is LoginAction.OnUserPasswordChanged -> {
                loginState.value = loginState.value.copy(password = action.input)
            }

            is LoginAction.OnUserNameChanged -> {
                loginState.value = loginState.value.copy(userName = action.input)
            }
        }
    }
}
