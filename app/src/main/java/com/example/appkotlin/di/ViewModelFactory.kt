package com.example.appkotlin.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appkotlin.data.local.TokenManager
import com.example.appkotlin.data.remote.RetrofitInstance
import com.example.appkotlin.data.repository.UserRepositoryImpl
import com.example.appkotlin.domain.repository.UserRepository
import com.example.appkotlin.ui.detail.UserDetailViewModel
import com.example.appkotlin.ui.login.LoginViewModel
import com.example.appkotlin.ui.userlist.UserListViewModel
import kotlinx.coroutines.Dispatchers

/*
 * Criar instancia de ViewModel
 * UI (Screen) -> ViewModel -> Repository / UseCase -> Data Source (API / Banco)
 * Screen: Exibe estado e envia ações
 * ViewModel: Controla estado e lógica da tela
 * Factory: Cria a ViewModel corretamente
 * Repository: Fornece dados
 */
class ViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(UserListViewModel::class.java) -> {
                UserListViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(UserDetailViewModel::class.java) -> {
                UserDetailViewModel(userRepository) as T
            }
            else -> throw IllegalArgumentException(
                "Unknown ViewModel class: ${modelClass.name}"
            )
        }
    }
}