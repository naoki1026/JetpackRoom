package com.example.jetpackroom.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackroom.data.dao.PersonDao
import java.lang.IllegalArgumentException

/**
 * ViewModelProviderFactory
 * ViewModelProviderでインスタンスを生成するためのファクトリクラスを定義することで、
 * DAOを組み込んだビューモデルを生成できるような仕組みを整える必要がある。
 *
 * */

// ViewModelProvider.Factoryはビューモデルファクトリーを生成するための専用クラス
class MyViewModelFactory(private val dataSource : PersonDao) : ViewModelProvider.Factory {

    // createメソッドでは、インスタンスを生成して返す
    // 引数にはビューモデルのクラスが指定される
    override fun < T : ViewModel?> create(model: Class<T>): T {

        // isAssignableFromで、MyViewModelがアサイン可能かどうかを確認する
        if (model.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(dataSource) as T
        }
        throw IllegalArgumentException("CANNOT_GET_VIEWMODEL")
    }


}