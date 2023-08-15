package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pace(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo("time")
    val time: String,

    @ColumnInfo("distance")
    val distance: String,

    @ColumnInfo("pace")
    val pace: String
)
