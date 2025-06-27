package com.vkdev.numbersinfo.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vkdev.numbersinfo.data.entity.NumberEntity

@Dao
interface NumbersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNumber(detailsEntity: NumberEntity): Long

    @Query("SELECT * FROM numbers_table WHERE id = :id")
    fun getNumberById(id: Int): NumberEntity

    @Query("SELECT * FROM numbers_table ORDER BY id DESC")
    fun getAllNumbersPaging(): PagingSource<Int, NumberEntity>
}