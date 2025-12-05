package com.example.madimplementation.data

//This file represents one item/row in a table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity //Turns class into a data table
data class ScannedItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val brand: String?,
    val model: String?,
    val imagePath: String?,
    val timestamp: Long = System.currentTimeMillis()
)