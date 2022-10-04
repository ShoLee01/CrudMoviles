package com.example.para_exam

import Beans.Post
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PostDao {

    @Query("SELECT * FROM post_table")
    fun getAll(): List<tablePost>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tablePost: tablePost)

    @Update
    fun update(tablePost: tablePost)

    @Delete
    fun delete(tablePost: tablePost)
}