package com.example.appkotlin.data.repository

import com.example.appkotlin.data.local.TokenManager
import com.example.appkotlin.data.remote.ApiService
import com.example.appkotlin.data.remote.LoginRequest
import com.example.appkotlin.data.remote.UserDto
import com.example.appkotlin.domain.model.User
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

    override suspend fun getUsers(page: Int, limit: Int): Result<List<User>> {
        return try {
            val skip = (page - 1) * limit
            val response = apiService.getUsers(limit, skip)
            val users = response.users.map { it.toUser() }
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUser(id: Int): Result<User> {
        return try {
            val userDto = apiService.getUser(id)
            Result.success(userDto.toUser())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

private fun UserDto.toUser(): User {
    return User(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        image = image
    )
}
