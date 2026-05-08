package com.aarav.notesapp.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM categories_table ORDER BY name ASC")
    fun getAllCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM categories_table WHERE id = :categoryId")
    fun findCategory(categoryId: Int): LiveData<Category>

    @Query("UPDATE categories_table SET name = :name, color = :color WHERE id = :categoryId")
    suspend fun updateCategory(categoryId: Int, name: String, color: Int)
}
