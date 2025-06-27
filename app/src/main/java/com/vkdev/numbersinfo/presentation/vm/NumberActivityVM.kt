package com.vkdev.numbersinfo.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vkdev.numbersinfo.domain.result.NumberInfoResult
import com.vkdev.numbersinfo.domain.useCase.GetNumberByIdUseCase
import com.vkdev.numbersinfo.presentation.uiState.NumberUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NumberActivityVM @Inject constructor(
    private val getNumberByIdUseCase: GetNumberByIdUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow(NumberUiState())
    val uiState: StateFlow<NumberUiState> = _uiState.asStateFlow()

    fun setNumberId(id: Int){
        if(id!=-1){
            getNumberInfo(id)
        }
    }

    fun getNumberInfo(id: Int){
        viewModelScope.launch {
            id?.let {
                _uiState.value= _uiState.value.copy(isLoading = true, message = null, numberInfoModel = null)
                val result = getNumberByIdUseCase(it)

                when(result){
                    is NumberInfoResult.Success->{
                        _uiState.value = _uiState.value.copy(isLoading = false, numberInfoModel = result.response)
                    }
                    is NumberInfoResult.Error->{
                        _uiState.value = _uiState.value.copy(isLoading = false, message = result.message)
                    }
                }
            }

        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }

}