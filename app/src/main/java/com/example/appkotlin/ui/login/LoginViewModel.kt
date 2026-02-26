package com.example.appkotlin.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appkotlin.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(username: String, password: String) {
        // Inicia uma Coroutine
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            // Delega ao repositório pelo login
            userRepository.login(username, password)
                .onSuccess {
                    _uiState.value = LoginUiState.Success
                }
                .onFailure {
                    _uiState.value = LoginUiState.Error(it.message ?: "Unknown error")
                }
        }
    }
}
