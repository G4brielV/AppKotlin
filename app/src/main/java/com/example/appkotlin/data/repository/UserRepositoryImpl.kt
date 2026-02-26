package com.example.appkotlin.data.repository

import com.example.appkotlin.data.local.TokenManager
import com.example.appkotlin.data.remote.ApiService
import com.example.appkotlin.data.remote.LoginRequest
import com.example.appkotlin.data.remote.UserDetailDto
import com.example.appkotlin.data.remote.UserSummaryDto
import com.example.appkotlin.domain.model.UserDetail
import com.example.appkotlin.domain.model.UserSummary
import com.example.appkotlin.domain.repository.UserRepository

class UserRepositoryImpl(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) : UserRepository {

    override suspend fun login(username: String, password: String): Result<Unit> {
        return try {
            val response = apiService.login(LoginRequest(username, password))
            tokenManager.saveToken(response.accessToken)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUsers(page: Int, limit: Int): Result<List<UserSummary>> {
        return try {
            val skip = (page - 1) * limit
            val response = apiService.getUsers(limit, skip)
            val users = response.users.map { it.toUserSummary() }
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUser(id: Int): Result<UserDetail> {
        return try {
            val userDto = apiService.getUser(id)
            Result.success(userDto.toUserDetail())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

private fun UserSummaryDto.toUserSummary(): UserSummary {
    return UserSummary(
        id = id,
        firstName = firstName,
        lastName = lastName,
        image = image
    )
}

private fun UserDetailDto.toUserDetail(): UserDetail {
    return UserDetail(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        image = image,
        age = age,
        gender = gender,
        username = username,
        birthDate = birthDate,
        hair = hair
    )
}
