package com.vkdev.numbersinfo.domain.result

import com.vkdev.numbersinfo.domain.model.NumberInfoModel

sealed class NumberInfoResult {
    data class Success(val response: NumberInfoModel) : NumberInfoResult()
    data class Error(val message: String) : NumberInfoResult()
}