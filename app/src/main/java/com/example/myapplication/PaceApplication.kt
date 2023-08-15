package com.example.myapplication

import android.app.Application
import androidx.room.Room
import com.example.myapplication.data.AppDatabase

class PaceApplication : Application() {
    private lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "pace-database").fallbackToDestructiveMigration().build()
    }

    fun getDatabase() : AppDatabase {
        return database
    }
}