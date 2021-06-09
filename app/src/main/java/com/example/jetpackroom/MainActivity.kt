package com.example.jetpackroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.jetpackroom.data.db.AppDatabase
import com.example.jetpackroom.data.entity.Person
import com.example.jetpackroom.databinding.ActivityMainBinding
import com.example.jetpackroom.ui.MyViewModel

class MainActivity : AppCompatActivity() {

    var _binding : ActivityMainBinding? = null
    val binding  get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java, "myfirsttapp_db"
        ).allowMainThreadQueries().build()
        binding.text1.text = getByText(db.personDao().loadAllPeople())

        // ViewModelはonCreateメソッド内に定義
        val viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        binding.text1.text = viewModel.allByText()

        binding.button1.setOnClickListener {
            val nm = binding.frmName.text
            val ml = binding.frmMail.text
            val ag = binding.frmAge.text

            viewModel.add(nm.toString(), ml.toString(), ag.toString().toInt())
            viewModel.person = Person(nm.toString(), ml.toString(), ag.toString().toInt())
            binding.text1.text = viewModel.allByText()
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
}