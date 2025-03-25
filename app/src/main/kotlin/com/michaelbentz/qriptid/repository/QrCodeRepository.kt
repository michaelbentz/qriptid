package com.michaelbentz.qriptid.repository

import com.michaelbentz.qriptid.database.dao.QrCodeDao
import com.michaelbentz.qriptid.domain.model.QrCode
import com.michaelbentz.qriptid.mapper.QrCodeMapper
import com.michaelbentz.qriptid.service.QrCodeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QrCodeRepository @Inject constructor(
    private val qrCodeService: QrCodeService,
    private val qrCodeDao: QrCodeDao,
    private val mapper: QrCodeMapper,
) {
    fun getLatest(): Flow<QrCode?> {
        return qrCodeDao
            .getLatest()
            .map { entity -> entity?.let { mapper.entityToDomain(it) } }
    }

    suspend fun createQrCode(data: String): Boolean {
        return try {
            val responseBody = qrCodeService.createQrCode(data).await()
            val qrCode = QrCode(
                bytes = responseBody.bytes().toList(),
                millis = System.currentTimeMillis(),
                data = data
            )
            qrCodeDao.insert(mapper.domainToEntity(qrCode)) != -1L
        } catch (_: Throwable) {
            false
        }
    }
}
