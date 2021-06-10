package com.example.jetpackroom.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {

        //  シングルトンプロパティにインスタンスを設定して、常にそのインスタンスが取り出せるようにする
        //  @Volatileアノテーションをつけることで複数のスレッドからのアクセスを可能にしている
        @Volatile private var singleton : AppDatabase? = null

        // getInstanceを呼び出して、インスタンスを生成している
        fun getInstance(context: Context) : AppDatabase =
            singleton ?: synchronized(this) {
                singleton ?: buildDatabase(context)
                    .also {singleton = it}
            }

        private fun buildDatabase(context: Context) =

            // Room.databaseBuilderインスタンスを生成して、
            // allowMainThreadQueriesを呼び出してbuildしている
            Room.databaseBuilder(context.applicationContext,
            AppDatabase::class.java, "myfirstapp_db")
                .allowMainThreadQueries()
                .build()
    }
}
