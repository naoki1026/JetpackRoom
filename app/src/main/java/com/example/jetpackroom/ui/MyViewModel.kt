package com.example.jetpackroom.ui

import androidx.lifecycle.ViewModel
import com.example.jetpackroom.data.entity.Person

class MyViewModel : ViewModel() {

    var data : MutableList<Person> = mutableListOf(
        Person("Taro", "taro@yamada", 36),
        Person("Hanako", "hanako@flower", 25),
        Person("Sachiko", "sachiko@happy", 14)
    )

    var person : Person = Person("new user", "new@address", 0)

    fun allByText() : String {
        var res = ""
        for (item in data) {
            res += item.to_s()
            res += "\n"
        }
        return res
    }

    fun getById(id: Int):Person = data[id]

    fun add( name:String, mail:String, age:Int ){
        data.add(Person(name, mail, age))

    }
}