package com.michaelbentz.qriptid.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.michaelbentz.qriptid.database.entity.QrCodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QrCodeDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(qrCodeEntity: QrCodeEntity): Long

    @Query("SELECT * FROM qr_code ORDER BY millis DESC LIMIT 1")
    fun getLatest(): Flow<QrCodeEntity?>
}
