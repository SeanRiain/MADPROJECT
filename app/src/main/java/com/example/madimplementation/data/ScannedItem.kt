package com.example.madimplementation.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScannedItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val brand: String?,
    val model: String?,
    val imagePath: String?,
    val timestamp: Long = System.currentTimeMillis()
)//needed for commit