package com.michaelbentz.qriptid.usecase

import com.michaelbentz.qriptid.repository.QrCodeRepository
import javax.inject.Inject

class CreateQrCodeUseCase @Inject constructor(
    private val qrCodeRepository: QrCodeRepository,
) {
    suspend operator fun invoke(data: String): Boolean {
        return qrCodeRepository.createQrCode(data)
    }
}
