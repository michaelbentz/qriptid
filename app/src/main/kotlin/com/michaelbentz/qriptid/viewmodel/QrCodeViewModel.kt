package com.michaelbentz.qriptid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelbentz.qriptid.ui.model.QrCodeUiData
import com.michaelbentz.qriptid.ui.state.QrCodeUiState
import com.michaelbentz.qriptid.usecase.CreateQrCodeUseCase
import com.michaelbentz.qriptid.usecase.GetLatestQrCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class QrCodeViewModel @Inject constructor(
    private val getLatestQrCodeUseCase: GetLatestQrCodeUseCase,
    private val createQrCodeUseCase: CreateQrCodeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<QrCodeUiState>(QrCodeUiState.Loading)
    val uiState: StateFlow<QrCodeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getLatestQrCodeUseCase().collect { qrCode ->
                _uiState.value = qrCode?.let {
                    QrCodeUiState.Data(
                        QrCodeUiData(it.millis, it.data, it.bytes)
                    )
                } ?: QrCodeUiState.NoData
            }
        }
    }

    fun createQrCode(data: String) {
        viewModelScope.launch {
            _uiState.value = QrCodeUiState.Loading
            if (!createQrCodeUseCase(data)) {
                _uiState.value = QrCodeUiState.Error("Failed to generate QR code.")
            }
        }
    }
}
