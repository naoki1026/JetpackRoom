package com.example.jetpackroom.data.entity

import androidx.room.ColumnInfo

data class PersonTuple (
    @ColumnInfo(name = "name") val name : String?,
    @ColumnInfo(name = "age") val age : Int?
)