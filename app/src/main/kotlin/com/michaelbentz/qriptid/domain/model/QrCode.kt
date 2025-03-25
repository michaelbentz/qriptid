package com.michaelbentz.qriptid.domain.model

data class QrCode(
    val millis: Long,
    val data: String,
    val bytes: List<Byte>,
)
