package com.michaelbentz.qriptid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelbentz.qriptid.database.entity.QrCodeEntity
import com.michaelbentz.qriptid.domain.usecase.CreateQrCodeUseCase
import com.michaelbentz.qriptid.network.NetworkState
import com.michaelbentz.qriptid.repository.QrCodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrCodeViewModel @Inject constructor(
    qrCodeRepository: QrCodeRepository,
    private val createQrCodeUseCase: CreateQrCodeUseCase
) : ViewModel() {
    val latestQrCode = qrCodeRepository.getLatest()

    private val _createQrCodeStateFlow: MutableStateFlow<NetworkState<QrCodeEntity>> = MutableStateFlow(NetworkState.Idle)
    val createQrCodeStateFlow = _createQrCodeStateFlow.asStateFlow()

    fun createQrCode(data: String) {
        viewModelScope.launch {
            _createQrCodeStateFlow.emit(NetworkState.Loading)
            createQrCodeUseCase(data).collect { state ->
                _createQrCodeStateFlow.emit(state)
            }
        }
    }
}