package com.example.jetpackroom.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpackroom.data.entity.Person

@Dao
interface PersonDao {

    @Query("SELECT * FROM person")
    fun loadAllPeople() : Array<Person>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPerson(person: Person)
}