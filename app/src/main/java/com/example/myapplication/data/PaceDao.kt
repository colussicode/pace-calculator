package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PaceDao {

    @Insert
    fun insert(pace: Pace)

    @Query("SELECT * FROM Pace")
    fun getAllPaces() : List<Pace>
}