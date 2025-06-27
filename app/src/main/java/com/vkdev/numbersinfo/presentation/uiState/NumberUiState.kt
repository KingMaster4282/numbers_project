package com.vkdev.numbersinfo.presentation.uiState

import com.vkdev.numbersinfo.domain.model.NumberInfoModel

data class NumberUiState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val numberInfoModel: NumberInfoModel? = null,
)