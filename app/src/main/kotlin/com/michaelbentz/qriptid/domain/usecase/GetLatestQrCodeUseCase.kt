package com.michaelbentz.qriptid.domain.usecase

import com.michaelbentz.qriptid.database.entity.QrCodeEntity
import com.michaelbentz.qriptid.repository.QrCodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLatestQrCodeUseCase @Inject constructor(
    private val qrCodeRepository: QrCodeRepository
) {
    suspend operator fun invoke(): Flow<QrCodeEntity?> {
        return withContext(Dispatchers.IO) {
            qrCodeRepository.getLatest()
        }
    }
}