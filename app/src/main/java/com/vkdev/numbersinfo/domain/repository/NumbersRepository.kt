package com.vkdev.numbersinfo.domain.repository

import androidx.paging.PagingData
import com.vkdev.numbersinfo.domain.result.NumberInfoResult
import com.vkdev.numbersinfo.domain.model.NumberInfoModel
import kotlinx.coroutines.flow.Flow

interface NumbersRepository {
    suspend fun getInfo(number: Int): NumberInfoResult
    suspend fun getRandomInfo(): NumberInfoResult
    suspend fun saveNumber(number: Int, text: String): Long
    suspend fun getNumberById(id: Int): NumberInfoResult
    fun getSavedNumbersPaging(): Flow<PagingData<NumberInfoModel>>
}