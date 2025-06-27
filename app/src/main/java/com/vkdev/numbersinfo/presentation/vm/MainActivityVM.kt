package com.vkdev.numbersinfo.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.paging.cachedIn
import com.vkdev.numbersinfo.domain.result.NumberInfoResult
import com.vkdev.numbersinfo.domain.model.NumberInfoModel
import com.vkdev.numbersinfo.domain.useCase.GetNumberInfoUseCase
import com.vkdev.numbersinfo.domain.useCase.GetPagedNumbersUseCase
import com.vkdev.numbersinfo.domain.useCase.GetRandomInfoUseCase
import com.vkdev.numbersinfo.domain.useCase.SaveNumberUseCase
import com.vkdev.numbersinfo.presentation.uiState.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityVM @Inject constructor(
    private val getNumberInfoUseCase: GetNumberInfoUseCase,
    private val saveNumberUseCase: SaveNumberUseCase,
    private val getRandomInfoUseCase: GetRandomInfoUseCase,
    private val getPagedNumbersUseCase: GetPagedNumbersUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    val pagedNumbers: Flow<PagingData<NumberInfoModel>> =
        getPagedNumbersUseCase().cachedIn(viewModelScope)

    private val _isPagingLoading = MutableStateFlow(false)
    val isPagingLoading: StateFlow<Boolean> = _isPagingLoading.asStateFlow()

    fun observeAdapterState(adapter: PagingDataAdapter<*, *>) {
        viewModelScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                val loading = loadStates.refresh is LoadState.Loading ||
                        loadStates.append is LoadState.Loading ||
                        loadStates.prepend is LoadState.Loading
                _isPagingLoading.value = loading
            }
        }
    }

    fun getNumberInfo(number: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, message = null)
            val response = getNumberInfoUseCase(number)

            manageResponse(response)
        }
    }

    private suspend fun manageResponse(result: NumberInfoResult) {
        when (result) {
            is NumberInfoResult.Success -> {
                saveToLocal(result.response)
            }

            is NumberInfoResult.Error -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = result.message
                )
            }
        }
    }

    fun getRandom() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, message = null)
            val response = getRandomInfoUseCase()

            manageResponse(response)
        }
    }

    private suspend fun saveToLocal(numberInfoResponse: NumberInfoModel) {
        val response = saveNumberUseCase(
            number = numberInfoResponse.number,
            text = numberInfoResponse.text
        )
        if (response > 0) {
            _uiState.value = _uiState.value.copy(isLoading = false)
        } else {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                message = "Error save to local"
            )
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
}