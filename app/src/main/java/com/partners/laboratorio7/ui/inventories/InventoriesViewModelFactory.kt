package com.partners.laboratorio7.ui.inventories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.partners.laboratorio7.database.AppDao

class InventoriesViewModelFactory(
    private val dataSource: AppDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoriesViewModel::class.java)) {
            return InventoriesViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
