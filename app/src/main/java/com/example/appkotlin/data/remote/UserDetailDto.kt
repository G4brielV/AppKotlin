package com.example.appkotlin.data.remote

import com.example.appkotlin.domain.model.Hair

data class UserDetailDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val image: String,
    val age: Int,
    val gender: String,
    val username: String,
    val birthDate: String,
    val hair: Hair
)


