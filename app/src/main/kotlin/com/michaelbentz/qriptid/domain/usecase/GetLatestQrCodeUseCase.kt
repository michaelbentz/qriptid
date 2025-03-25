package com.michaelbentz.qriptid.domain.usecase

import com.michaelbentz.qriptid.domain.model.QrCode
import com.michaelbentz.qriptid.repository.QrCodeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLatestQrCodeUseCase @Inject constructor(
    private val qrCodeRepository: QrCodeRepository,
) {
    operator fun invoke(): Flow<QrCode?> {
        return qrCodeRepository.getLatest()
    }
}
