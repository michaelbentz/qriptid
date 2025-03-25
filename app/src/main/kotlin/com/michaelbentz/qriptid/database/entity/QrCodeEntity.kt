package com.michaelbentz.qriptid.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qr_code")
class QrCodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val millis: Long,
    val data: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val bytes: ByteArray,
) {
    override fun toString(): String {
        return "QrCodeEntity(id=$id, millis=$millis)"
    }
}