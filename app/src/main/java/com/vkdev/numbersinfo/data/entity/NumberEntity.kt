package com.vkdev.numbersinfo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "numbers_table")
data class NumberEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val number: Int,
)