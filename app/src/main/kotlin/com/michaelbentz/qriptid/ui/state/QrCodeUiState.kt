package com.michaelbentz.qriptid.ui.state

import com.michaelbentz.qriptid.ui.model.QrCodeUiData

sealed interface QrCodeUiState {
    object Loading : QrCodeUiState
    data class Data(val data: QrCodeUiData) : QrCodeUiState
    object NoData : QrCodeUiState
}
