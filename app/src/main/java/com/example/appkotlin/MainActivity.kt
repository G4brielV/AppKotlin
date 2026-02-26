package com.example.appkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.appkotlin.data.local.TokenManager
import com.example.appkotlin.data.remote.RetrofitInstance
import com.example.appkotlin.data.repository.UserRepositoryImpl
import com.example.appkotlin.di.ViewModelFactory
import com.example.appkotlin.navigation.NavGraph
import com.example.appkotlin.ui.theme.AppKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        /*
        * Retrofit + TokenManager
        * UserRepositoryImpl
        * ViewModelFactory
        * LoginViewModel
        * LoginScreen
         */

        val tokenManager = TokenManager(applicationContext)
        val userRepository = UserRepositoryImpl(RetrofitInstance.api, tokenManager)
        val viewModelFactory = ViewModelFactory(userRepository)

        setContent {
            AppKotlinTheme {
                NavGraph(viewModelFactory = viewModelFactory)
            }
        }
    }
}
