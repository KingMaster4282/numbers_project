package com.vkdev.numbersinfo.domain.useCase

import com.vkdev.numbersinfo.domain.repository.NumbersRepository
import javax.inject.Inject

class SaveNumberUseCase @Inject constructor(
    private val repository: NumbersRepository
) {

    suspend operator fun invoke(number: Int, text: String): Long{
            return repository.saveNumber(text = text, number = number)

    }

}