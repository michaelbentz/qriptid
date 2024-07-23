package com.michaelbentz.qriptid.repository

import com.michaelbentz.qriptid.database.dao.QrCodeDao
import com.michaelbentz.qriptid.database.entity.QrCodeEntity
import com.michaelbentz.qriptid.network.NetworkState
import com.michaelbentz.qriptid.service.QrCodeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QrCodeRepository @Inject constructor(
    private val qrCodeService: QrCodeService,
    private val qrCodeDao: QrCodeDao
) {
    fun getLatest(): Flow<QrCodeEntity?> {
        return qrCodeDao.getLatest()
    }

    suspend fun createQrCode(data: String): Flow<NetworkState<QrCodeEntity>> = flow {
        try {
            val responseBody = qrCodeService.createQrCode(data).await()
            val qrCodeEntity = QrCodeEntity(
                bytes = responseBody.bytes(),
                millis = System.currentTimeMillis(),
                data = data
            )
            qrCodeDao.insert(qrCodeEntity)
            emit(NetworkState.Success(qrCodeEntity))
        } catch (throwable: Throwable) {
            emit(NetworkState.Error(throwable.message, throwable))
        }
    }
}