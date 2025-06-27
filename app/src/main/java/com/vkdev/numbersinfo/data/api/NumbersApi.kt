package com.vkdev.numbersinfo.data.api

import com.vkdev.numbersinfo.data.dto.NumberInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface NumbersApi {

    @GET("{number}")
    suspend fun getNumberInfo(@Path("number") number: Int,): NumberInfoDto

    @GET("random/math")
    suspend fun getRandomNumberInfo(): NumberInfoDto

}