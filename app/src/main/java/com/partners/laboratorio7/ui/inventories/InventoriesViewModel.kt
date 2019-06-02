package com.partners.laboratorio7.ui.inventories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.partners.laboratorio7.database.AppDao
import com.partners.laboratorio7.database.InventaryDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class InventoriesViewModel(
                           val database: AppDao) : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _inventories = MutableLiveData<ArrayList<InventaryDb>>()
    val inventories: LiveData<ArrayList<InventaryDb>>
        get() = _inventories

    init {
        _inventories.value = ArrayList(database.getAllInventories())//?: arrayListOf<InventaryDb>()
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun clear(){
        database.clearInventories()
        _inventories.value = ArrayList(database.getAllInventories())
    }
    fun reset(){
        _inventories.value = ArrayList(database.getAllInventories())
    }
    fun onAddRow(inventaryDb: InventaryDb) {
        database.insertInventory(inventaryDb)
        _inventories.value = ArrayList(database.getAllInventories())
    }
}
