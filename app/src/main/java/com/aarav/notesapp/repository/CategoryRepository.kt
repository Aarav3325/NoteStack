package com.aarav.notesapp.repository

import kotlinx.coroutines.flow.Flow
import com.aarav.notesapp.roomdb.Category
import com.aarav.notesapp.roomdb.CategoryDao

class CategoryRepository(private val categoryDao: CategoryDao) {

    val allCategories: Flow<List<Category>> = categoryDao.getAllCategories()

    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }

    suspend fun updateCategory(categoryId: Int, name: String, color: Int) {
        categoryDao.updateCategory(categoryId, name, color)
    }

    suspend fun getAllCategoriesSync(): List<Category> = categoryDao.getAllCategoriesSync()

    suspend fun insertCategories(categories: List<Category>) = categoryDao.insertCategories(categories)
}
