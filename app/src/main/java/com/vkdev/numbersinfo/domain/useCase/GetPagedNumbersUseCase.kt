package com.vkdev.numbersinfo.domain.useCase

import androidx.paging.PagingData
import com.vkdev.numbersinfo.domain.model.NumberInfoModel
import com.vkdev.numbersinfo.domain.repository.NumbersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagedNumbersUseCase @Inject constructor(
    private val repository: NumbersRepository
) {
    operator fun invoke(): Flow<PagingData<NumberInfoModel>> {
        return repository.getSavedNumbersPaging()
    }
}