package com.example.appkotlin.ui.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appkotlin.domain.model.User
import com.example.appkotlin.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UserListUiState {
    object Loading : UserListUiState()
    data class Success(val users: List<User>) : UserListUiState()
    data class Error(val message: String) : UserListUiState()
}

class UserListViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UserListUiState>(UserListUiState.Loading)
    val uiState: StateFlow<UserListUiState> = _uiState

    private var currentPage = 1
    private var userList = mutableListOf<User>()

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            _uiState.value = UserListUiState.Loading
            userRepository.getUsers(currentPage, 20)
                .onSuccess {
                    userList.addAll(it)
                    _uiState.value = UserListUiState.Success(userList)
                    currentPage++
                }
                .onFailure {
                    _uiState.value = UserListUiState.Error(it.message ?: "Unknown error")
                }
        }
    }
}
