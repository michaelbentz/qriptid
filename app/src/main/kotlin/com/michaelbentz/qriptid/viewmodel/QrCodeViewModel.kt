package com.michaelbentz.qriptid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelbentz.qriptid.database.entity.QrCodeEntity
import com.michaelbentz.qriptid.domain.usecase.CreateQrCodeUseCase
import com.michaelbentz.qriptid.domain.usecase.GetLatestQrCodeUseCase
import com.michaelbentz.qriptid.network.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrCodeViewModel @Inject constructor(
    private val getLatestQrCodeUseCase: GetLatestQrCodeUseCase,
    private val createQrCodeUseCase: CreateQrCodeUseCase
) : ViewModel() {
    private val _latestQrCodeStateFlow: MutableStateFlow<QrCodeEntity?> = MutableStateFlow(null)
    val latestQrCodeStateFlow = _latestQrCodeStateFlow.asStateFlow()

    private val _createQrCodeStateFlow: MutableStateFlow<NetworkState<QrCodeEntity>> =
        MutableStateFlow(NetworkState.Idle)
    val createQrCodeStateFlow = _createQrCodeStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            getLatestQrCodeUseCase().collectLatest {
                _latestQrCodeStateFlow.emit(it)
            }
        }
    }

    fun createQrCode(data: String) {
        viewModelScope.launch {
            _createQrCodeStateFlow.emit(NetworkState.Loading)
            createQrCodeUseCase(data).collect { state ->
                _createQrCodeStateFlow.emit(state)
            }
        }
    }
}