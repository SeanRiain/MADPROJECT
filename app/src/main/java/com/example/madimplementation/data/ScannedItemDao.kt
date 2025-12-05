package com.example.madimplementation.data

//This class provides CRUD equivalent functionality for the database table, allowing rows to be
//retrieved, added, changed and removed

import androidx.room.*
import kotlinx.coroutines.flow.Flow


interface ScannedItemDao {
    @Query("SELECT * FROM ScannedItem ORDER BY timestamp DESC")
    fun getAll(): Flow<List<ScannedItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ScannedItem)

    @Update
    suspend fun update(item: ScannedItem)

    @Delete
    suspend fun delete(item: ScannedItem)
}