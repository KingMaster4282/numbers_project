package com.vkdev.numbersinfo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.vkdev.numbersinfo.domain.result.NumberInfoResult
import com.vkdev.numbersinfo.data.api.NumbersApi
import com.vkdev.numbersinfo.data.dao.NumbersDao
import com.vkdev.numbersinfo.data.entity.NumberEntity
import com.vkdev.numbersinfo.data.mapper.toModel
import com.vkdev.numbersinfo.domain.model.NumberInfoModel
import com.vkdev.numbersinfo.domain.repository.NumbersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NumbersRepositoryImpl @Inject constructor(
    private val numbersApi: NumbersApi,
    private val numbersDao: NumbersDao,
): NumbersRepository {

    override suspend fun getInfo(number: Int): NumberInfoResult {
        return withContext(Dispatchers.IO){
            try {
                val response = numbersApi.getNumberInfo(number)

                return@withContext NumberInfoResult.Success(response.toModel())
            }
            catch (e: Exception){
                return@withContext NumberInfoResult.Error(e.message ?: "Something going wrong")
            }

        }
    }

    override suspend fun getRandomInfo(): NumberInfoResult {
        return withContext(Dispatchers.IO){
            try {
                val response = numbersApi.getRandomNumberInfo()

                return@withContext NumberInfoResult.Success(response.toModel())
            }
            catch (e: Exception){
                return@withContext NumberInfoResult.Error(e.message ?: "Something going wrong")
            }
        }
    }

    override suspend fun saveNumber(number: Int, text: String): Long {
        return withContext(Dispatchers.IO){
            return@withContext numbersDao.insertNumber(NumberEntity(text = text, number = number))
        }
    }

    override suspend fun getNumberById(id: Int): NumberInfoResult {
        return withContext(Dispatchers.IO){
            try {
                val response = numbersDao.getNumberById(id)

                return@withContext NumberInfoResult.Success(response.toModel())
            }
            catch (e: Exception){
                return@withContext NumberInfoResult.Error(e.message ?: "Something went wrong")
            }

        }
    }

    override fun getSavedNumbersPaging(): Flow<PagingData<NumberInfoModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { numbersDao.getAllNumbersPaging() }
        ).flow.map { data->
            data.map {
                it.toModel()
            }
        }
    }

}