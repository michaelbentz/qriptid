package com.michaelbentz.qriptid.mapper

import com.michaelbentz.qriptid.database.entity.QrCodeEntity
import com.michaelbentz.qriptid.domain.model.QrCode
import javax.inject.Inject

class QrCodeMapper @Inject constructor() {
    fun entityToDomain(entity: QrCodeEntity) = QrCode(
        data = entity.data,
        millis = entity.millis,
        bytes = entity.bytes.toList(),
    )

    fun domainToEntity(qrCode: QrCode) = QrCodeEntity(
        data = qrCode.data,
        millis = qrCode.millis,
        bytes = qrCode.bytes.toByteArray(),
    )
}
