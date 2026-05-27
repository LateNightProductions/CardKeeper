package com.awscherb.cardkeeper.pkpass.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.awscherb.cardkeeper.pkpass.entity.PassUpdateEntity
import com.awscherb.cardkeeper.pkpass.entity.PkPassEntity
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

    @Query("SELECT * FRom PassUpdateEntity WHERE passId = :passId LIMIT 1")
    fun getUpdateSettingsForPass(passId: String): Flow<List<PassUpdateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAutoUpdateSettings(settings: PassUpdateEntity)

    @Query("UPDATE PkPassEntity SET sortOrder = :sortOrder WHERE id = :id")
    suspend fun updateSortOrder(id: String, sortOrder: Long)

    @Query("SELECT * FROM PkPassEntity WHERE groupingIdentifier = :groupingIdentifier")
    fun getPassesByGroupingIdentifier(groupingIdentifier: String): Flow<List<PkPassEntity>>

    @Query("SELECT * FROM PkPassEntity WHERE groupId = :groupId")
    fun getPassesByGroupId(groupId: String): Flow<List<PkPassEntity>>
}