package com.awscherb.cardkeeper.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.awscherb.cardkeeper.data.entity.PkPassEntity
import com.awscherb.cardkeeper.data.entity.ScannedCodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PkPassDao {

    @Query("SELECT * FROM PkPassEntity WHERE id = :id LIMIT 1")
    fun getPass(id: String): Flow<List<PkPassEntity>>

    @Query("SELECT * FROM PkPassEntity")
    fun listPkPasses(): Flow<List<PkPassEntity>>

    @Query("SELECT * FROM PkPassEntity WHERE description LIKE '%' || :query || '%'")
    fun listPkPasses(query: String): Flow<List<PkPassEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPass(pass: PkPassEntity): Long

    @Update
    suspend fun updatePass(pass: PkPassEntity)

    @Query("DELETE FROM PkPassEntity WHERE id = :id")
    suspend fun delete(id: String): Int
}