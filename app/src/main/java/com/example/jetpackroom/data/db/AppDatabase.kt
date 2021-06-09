package com.example.jetpackroom.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetpackroom.data.dao.PersonDao
import com.example.jetpackroom.data.entity.Person

@Database(entities = arrayOf(Person::class), version = 1, exportSchema = false)
class AppDatabase {
    abstract class AppData : RoomDatabase() {
        abstract fun personDao() : PersonDao
    }
}