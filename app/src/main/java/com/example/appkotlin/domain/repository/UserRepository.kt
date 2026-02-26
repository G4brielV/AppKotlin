package com.example.appkotlin.domain.repository

import com.example.appkotlin.domain.model.UserDetail
import com.example.appkotlin.domain.model.UserSummary

interface UserRepository {
    suspend fun login(username: String, password: String): Result<Unit>
    suspend fun getUsers(page: Int, limit: Int): Result<List<UserSummary>>
    suspend fun getUser(id: Int): Result<UserDetail>
}
