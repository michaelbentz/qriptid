package com.michaelbentz.qriptid.ui.model

data class QrCodeUiData(
    val millis: Long,
    val data: String,
    val bytes: List<Byte>,
) {
    override fun toString(): String {
        return "QrCodeUiData(millis=$millis, data='$data')"
    }
}
