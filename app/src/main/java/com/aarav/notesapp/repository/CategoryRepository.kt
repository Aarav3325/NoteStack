package com.aarav.notesapp.repository

import androidx.lifecycle.LiveData
import com.aarav.notesapp.roomdb.Category
import com.aarav.notesapp.roomdb.CategoryDao

class CategoryRepository(private val categoryDao: CategoryDao) {

    val allCategories: LiveData<List<Category>> = categoryDao.getAllCategories()

    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }

    suspend fun updateCategory(categoryId: Int, name: String, color: Int) {
        categoryDao.updateCategory(categoryId, name, color)
    }
}
