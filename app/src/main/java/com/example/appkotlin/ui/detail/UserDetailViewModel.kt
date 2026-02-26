package com.example.appkotlin.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appkotlin.domain.model.UserDetail
import com.example.appkotlin.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UserDetailUiState {
    object Loading : UserDetailUiState()
    data class Success(val user: UserDetail) : UserDetailUiState()
    data class Error(val message: String) : UserDetailUiState()
}

class UserDetailViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UserDetailUiState>(UserDetailUiState.Loading)
    val uiState: StateFlow<UserDetailUiState> = _uiState

    fun getUser(id: Int) {
        viewModelScope.launch {
            _uiState.value = UserDetailUiState.Loading
            userRepository.getUser(id)
                .onSuccess {
                    _uiState.value = UserDetailUiState.Success(it)
                }
                .onFailure {
                    _uiState.value = UserDetailUiState.Error(it.message ?: "Unknown error")
                }
        }
    }
}
