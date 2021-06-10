package com.example.jetpackroom.ui

import android.widget.EditText
import androidx.lifecycle.ViewModel
import com.example.jetpackroom.data.dao.PersonDao
import com.example.jetpackroom.data.entity.Person

class MyViewModel(dao: PersonDao) : ViewModel() {

    val dao = dao

     var person : Person = Person("", "", 0)
     get() = field
     set(value) {
         field = value
     }


    fun data() : Array<Person> = dao.loadAllPeople()

    fun add(name: EditText, mail:EditText, age:EditText ){
        val nm = name.text.toString()
        val ml = mail.text.toString()
        val ag = age.text.toString().toInt()
        addPerson(nm, ml, ag)
        name.setText("")
        mail.setText("")
        age.setText("0")
    }

    private fun addPerson(nm: String, ml: String, ag: Int) {
        val p = Person(nm, ml, ag)
        dao.insertPerson((p))
    }

    fun allByText() : String {
        val data = data().iterator()
        var res = ""
        for(item in data){
            res += "${item.to_s()}\n"
        }
        return res
    }

//    var data : MutableList<Person> = mutableListOf(
//        Person("Taro", "taro@yamada", 36),
//        Person("Hanako", "hanako@flower", 25),
//        Person("Sachiko", "sachiko@happy", 14)
//    )
//
//
//
//    fun getById(id: Int):Person = data[id]


}