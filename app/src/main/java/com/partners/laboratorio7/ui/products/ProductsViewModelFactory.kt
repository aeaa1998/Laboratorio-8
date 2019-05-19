package com.partners.laboratorio7.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.partners.laboratorio7.database.AppDao

class ProductsViewModelFactory(
    private val dataSource: AppDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
            return ProductsViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}