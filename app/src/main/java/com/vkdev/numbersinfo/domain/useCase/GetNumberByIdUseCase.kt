package com.vkdev.numbersinfo.domain.useCase

import com.vkdev.numbersinfo.domain.result.NumberInfoResult
import com.vkdev.numbersinfo.domain.repository.NumbersRepository
import javax.inject.Inject

class GetNumberByIdUseCase @Inject constructor(
    private val repository: NumbersRepository
) {

    suspend operator fun invoke(id: Int): NumberInfoResult {
        return repository.getNumberById(id)
    }

}