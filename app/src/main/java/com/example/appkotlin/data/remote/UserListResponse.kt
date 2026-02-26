package com.example.appkotlin.data.remote

data class UserListResponse(
    val users: List<UserDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
