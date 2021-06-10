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
import com.example.jetpackroom.ui.MyViewModelFactory
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding  get() = _binding!!
    lateinit var db : AppDatabase
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // dbインスタンスの生成
        db = AppDatabase.getInstance(this)
        viewModel = MyViewModelFactory(db.personDao()).create(MyViewModel::class.java)

        db.personDao().loadAllPeopleFlowable()
            .subscribeOn(Schedulers.io())
            .subscribe{
                runOnUiThread {
                    binding.text1.text = getByText(it)
                }
            }

        binding.frmId.setOnFocusChangeListener { view: View, b:Boolean ->
            if(!b){

                val id = binding.frmId.text.toString().toInt()
                val person = db.personDao().getPersonById(id)
                binding.frmName.setText(person?.name)
                binding.frmMail.setText(person?.mail)
                binding.frmAge.setText(person?.age.toString())
            }
        }

        binding.addButton.setOnClickListener {
            viewModel.add(binding.frmName, binding.frmMail, binding.frmAge)
            binding.text1.text = viewModel.allByText()
        }

        binding.updateButton.setOnClickListener {
            val id = binding.frmId.text.toString().toInt()
            val person = db.personDao().getPersonById(id)
            person.name = binding.frmName.text.toString()
            person.mail = binding.frmMail.text.toString()
            person.age = binding.frmAge.text.toString().toInt()
            db.personDao().updatePerson(person)
        }

//        db = Room.databaseBuilder(
//            applicationContext, AppDatabase::class.java, "myfirsttapp_db"
//        ).allowMainThreadQueries().build()
//        binding.text1.text = getByText(db.personDao().loadAllPeople())
//        Log.d("MainActivity", "${getByText(db.personDao().loadAllPeople())}")
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