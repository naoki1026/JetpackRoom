package com.example.jetpackroom.data.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.jetpackroom.data.entity.Person
import com.example.jetpackroom.data.entity.PersonTuple
import io.reactivex.Flowable

/**
 * DAO(Data Access Object)
 * データベースへの様々な要求をまとめて実装する部分のこと
 *
 * */

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPerson(person: Person)

    @Delete
    fun deletePerson (person : Person)

    @Update
    fun updatePerson(person: Person)

    @Query("SELECT * FROM person")
    fun loadAllPeople() : Array<Person>

    // 取得してきたidをgetPersonByIdの引数にする
    @Query("SELECT * FROM person WHERE id = :id")
    fun getPersonById(id: Int) : Person

    // nameと等しいエンティティを検索
    @Query("SELECT * FROM person WHERE name = :name")
    fun getPersonByName(name: String) : Array<Person>

    // nameを含むエンティティを検索
    @Query("SELECT * FROM person WHERE name like :name")
    fun getPersonLikeName(name:String) : Array<Person>

    // ageと等しいか大きいエンティティを検索
    @Query("SELECT * FROM person WHERE age >= :age")
    fun getPersonGreaterThanAge(age: Int) : Array<Person>

    // ageと等しいか小さいエンティティを検索
    @Query("SELECT * FROM PERSON WHERE age <= :age")
    fun getPersonLowerThanAge(age: Int) : Array<Person>

    // ageがmin以上、max以下のエンティティを検索
    @Query("SELECT * FROM person WHERE age >= :min AND age<= :max")
    fun getPersonMinMaxFromAge(min:Int, max:Int) : Array<Person>

    // クエリーを直接実行する
    @RawQuery
    fun getPersonWithQuery(query: SupportSQLiteQuery) : Array<Person>

    @Query("SELECT * FROM person WHERE name like :name OR mail like :name")
    fun getPersonSubset(name: String) : Array<PersonTuple>

    // Room-RxJavaを使用
    @Query("SELECT * FROM person")
    fun loadAllPeopleFlowable() : Flowable<Array<Person>>


 }