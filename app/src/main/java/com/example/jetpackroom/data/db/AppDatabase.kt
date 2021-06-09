package com.example.jetpackroom.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetpackroom.data.dao.PersonDao
import com.example.jetpackroom.data.entity.Person

/**
 *  RoomDatabase
 *  データベースへの基本的な接続と、次にDAOなどの管理を行う。
 *
 * */

@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao() : PersonDao
}
