package com.michaelbentz.qriptid.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qr_code")
data class QrCodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val millis: Long,
    val data: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val bytes: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QrCodeEntity
        if (id != other.id) return false
        if (millis != other.millis) return false
        if (data != other.data) return false
        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + millis.hashCode()
        result = 31 * result + data.hashCode()
        result = 31 * result + bytes.contentHashCode()
        return result
    }
}
