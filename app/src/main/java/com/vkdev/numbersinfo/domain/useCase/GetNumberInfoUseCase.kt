package com.vkdev.numbersinfo.domain.useCase

import com.vkdev.numbersinfo.domain.result.NumberInfoResult
import com.vkdev.numbersinfo.domain.repository.NumbersRepository
import javax.inject.Inject

class GetNumberInfoUseCase @Inject constructor(
    private val repository: NumbersRepository
) {

    suspend operator fun invoke(number: Int): NumberInfoResult {
        return repository.getInfo(number)
    }

}