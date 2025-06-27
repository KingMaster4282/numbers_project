package com.vkdev.numbersinfo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vkdev.numbersinfo.data.dao.NumbersDao
import com.vkdev.numbersinfo.data.entity.NumberEntity

@Database(
    entities = [
        NumberEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun numbersDao(): NumbersDao

}