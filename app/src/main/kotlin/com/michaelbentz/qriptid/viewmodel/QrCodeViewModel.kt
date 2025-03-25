package com.michaelbentz.qriptid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelbentz.qriptid.database.entity.QrCodeEntity
import com.michaelbentz.qriptid.domain.usecase.CreateQrCodeUseCase
import com.michaelbentz.qriptid.domain.usecase.GetLatestQrCodeUseCase
import com.michaelbentz.qriptid.network.NetworkState
import com.michaelbentz.qriptid.ui.model.QrCodeUiData
import com.michaelbentz.qriptid.ui.state.QrCodeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class QrCodeViewModel @Inject constructor(
    private val getLatestQrCodeUseCase: GetLatestQrCodeUseCase,
    private val createQrCodeUseCase: CreateQrCodeUseCase
) : ViewModel() {
    val uiState: StateFlow<QrCodeUiState> = getUiState().stateIn(
        viewModelScope,
        WhileSubscribed(5000),
        QrCodeUiState.Loading,
    )

    private val _createQrCodeStateFlow: MutableStateFlow<NetworkState<QrCodeEntity>> =
        MutableStateFlow(NetworkState.Idle)
    val createQrCodeStateFlow = _createQrCodeStateFlow.asStateFlow()

    private fun getUiState(): Flow<QrCodeUiState> = getLatestQrCodeUseCase().mapLatest { qrCode ->
        qrCode?.let {
            QrCodeUiState.Data(
                QrCodeUiData(
                    data = it.data,
                    bytes = it.bytes,
                    millis = it.millis,
                )
            )
        } ?: QrCodeUiState.NoData
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