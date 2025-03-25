package com.michaelbentz.qriptid.domain.usecase

import com.michaelbentz.qriptid.database.entity.QrCodeEntity
import com.michaelbentz.qriptid.network.NetworkState
import com.michaelbentz.qriptid.repository.QrCodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateQrCodeUseCase @Inject constructor(
    private val qrCodeRepository: QrCodeRepository,
) {
    suspend operator fun invoke(data: String): Flow<NetworkState<QrCodeEntity>> {
        return withContext(Dispatchers.IO) {
            qrCodeRepository.createQrCode(data)
        }
    }
}
