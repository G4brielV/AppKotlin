package com.example.appkotlin.domain.repository

import com.example.appkotlin.domain.model.User

interface UserRepository {
    suspend fun login(username: String, password: String): Result<Unit>
    suspend fun getUsers(page: Int, limit: Int): Result<List<User>>
    suspend fun getUser(id: Int): Result<User>
}
