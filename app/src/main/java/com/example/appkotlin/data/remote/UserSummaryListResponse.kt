package com.example.appkotlin.data.remote

data class UserSummaryListResponse(
    val users: List<UserSummaryDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
