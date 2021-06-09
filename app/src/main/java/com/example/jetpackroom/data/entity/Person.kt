package com.example.jetpackroom.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  Entity
 *  データベースのテーブルとそこに保管されるレコードに対応するJava/Kotlinオブジェクト
 *  Entityは実体、存在という意味。
 *
 * */

@Entity(tableName = "person")
class Person(name: String, mail: String, age: Int) {
    @PrimaryKey(autoGenerate = true) var id : Int = 0
    var name : String = name
    var mail : String = mail
    var age : Int = age

    fun age_s(): String = age.toString()
    fun to_s(): String = "$id : $name ($mail, $age)"
}