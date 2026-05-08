package com.aarav.notesapp.roomdb

import kotlinx.coroutines.flow.Flow
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
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM categories_table WHERE id = :categoryId")
    fun findCategory(categoryId: Int): Flow<Category?>

    @Query("UPDATE categories_table SET name = :name, color = :color WHERE id = :categoryId")
    suspend fun updateCategory(categoryId: Int, name: String, color: Int)

    @Query("SELECT * FROM categories_table")
    suspend fun getAllCategoriesSync(): List<Category>

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)
}
