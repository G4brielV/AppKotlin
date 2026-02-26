package com.example.appkotlin.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/* Interface de contrato para as chamadas HTTP
 * Async não bloqueante
 * Para qualquer ação de rede ou demorada
 */
interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("users")
    suspend fun getUsers(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): UserSummaryListResponse

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): UserDetailDto
}
