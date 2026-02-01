package com.example.examesus.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface ExameDao {

    @Query("SELECT * FROM exames WHERE usuarioId = :userId ORDER BY id")
    fun getExamesByUserIdFlow(userId: String): Flow<List<ExameEntity>>

    @Query("SELECT * FROM exames WHERE usuarioId = :userId")
    suspend fun getExamesByUserId(userId: String): List<ExameEntity>

    @Query("SELECT * FROM exames WHERE unidade = :unidade ORDER BY id")
    fun getExamesDaUnidadeFlow(unidade: String): Flow<List<ExameEntity>>

    @Query("SELECT * FROM exames WHERE unidade = :unidade")
    suspend fun getExamesDaUnidade(unidade: String): List<ExameEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exames: List<ExameEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exame: ExameEntity)

    @Query("UPDATE exames SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: String, status: String)

    @Query("DELETE FROM exames WHERE unidade = :unidade")
    suspend fun deleteByUnidade(unidade: String)

    @Query("DELETE FROM exames WHERE usuarioId = :userId")
    suspend fun deleteByUserId(userId: String)
}
