package com.partners.laboratorio7

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.partners.laboratorio7.database.AppDao
import com.partners.laboratorio7.ui.products.ProductsViewModel

class singleInventaryFactory(
    private val singleInventoryId: Int,
    private val dataSource: AppDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SingleInventaryViewModel::class.java)) {
            return SingleInventaryViewModel(singleInventoryId, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}