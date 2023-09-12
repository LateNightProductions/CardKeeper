package com.awscherb.cardkeeper.barcode.db

import androidx.room.*
import com.awscherb.cardkeeper.barcode.entity.ScannedCodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScannedCodeDao {

    @Query("SELECT * FROM scannedCode WHERE id = :id LIMIT 1")
    fun getScannedCode(id: Int): Flow<List<ScannedCodeEntity>>

    @Query("SELECT * FROM scannedCode")
    fun listScannedCodes(): Flow<List<ScannedCodeEntity>>

    @Query("SELECT * FROM scannedCode WHERE title LIKE '%' || :query || '%'")
    fun listScannedCodes(query: String): Flow<List<ScannedCodeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCode(code: ScannedCodeEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCode(code: ScannedCodeEntity): Int

    @Query("DELETE FROM scannedCode WHERE id = :id")
    suspend fun deleteCode(id: Int): Int
}