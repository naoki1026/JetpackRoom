package com.example.jetpackroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.jetpackroom.data.db.AppDatabase
import com.example.jetpackroom.data.entity.Person
import com.example.jetpackroom.databinding.ActivityMainBinding
import com.example.jetpackroom.ui.MyViewModel

class MainActivity : AppCompatActivity() {

    var _binding : ActivityMainBinding? = null
    val binding  get() = _binding!!
    lateinit var db : AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java, "myfirsttapp_db"
        ).allowMainThreadQueries().build()
        binding.text1.text = getByText(db.personDao().loadAllPeople())
        Log.d("MainActivity", "${getByText(db.personDao().loadAllPeople())}")

        binding.frmId.setOnFocusChangeListener { view: View, b: Boolean ->
            if(!b) {
                val id = binding.frmId.text.toString().toInt()
                id?.let {
                    val person = db.personDao().getPersonById(id)
                    binding.frmName.setText(person?.name)
                    binding.frmMail.setText(person?.mail)
                    binding.frmAge.setText(person?.age_s())
                }
            }
        }

        binding.addButton.setOnClickListener {
            val name = binding.frmName.text.toString()
            val mail = binding.frmMail.text.toString()
            var age = binding.frmAge.text.toString().toInt()

            val person = Person(name, mail, age)
            db.personDao().insertPerson(person)
            resetPerson()
        }

        binding.updateButton.setOnClickListener {
            val id = binding.frmId.text.toString().toInt()
            val person = db.personDao().getPersonById(id!!)

            person?.let {
                person.name = binding.frmName.text.toString()
                person.mail = binding.frmMail.text.toString()
                person.age = binding.frmAge.text.toString().toInt()
                db.personDao().updatePerson(person)
            }
            resetPerson()
        }

        binding.deleteButton.setOnClickListener {

            val id = binding.frmId.text.toString().toInt()
            val person = db.personDao().getPersonById(id)

            person?.let {
                db.personDao().deletePerson(person)
            }
            resetPerson()
        }

        binding.searchButton.setOnClickListener {
//            val name = binding.frmName.text.toString()
//            val people = db.personDao().getPersonLikeName(name)
//            binding.text1.text = getByText(people)
//            resetPerson()

            val nm = "'%" + binding.frmName.text.toString() + "%'"
            val query = SimpleSQLiteQuery (
                "select * from person where mail like $nm"
                    )
            val people = db.personDao().getPersonWithQuery(query)
            binding.text1.text = getByText(people)
        }
    }

    private fun getByText(data: Array<Person>): String {
        var res = ""
        for (item in data) {
            res += item.to_s()
            res += "\n"
        }
        return res
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun resetPerson(){
        binding.text1.text = getByText(db.personDao().loadAllPeople())
        binding.frmId.setText("")
        binding.frmName.setText("")
        binding.frmMail.setText("")
        binding.frmAge.setText("0")
    }
}