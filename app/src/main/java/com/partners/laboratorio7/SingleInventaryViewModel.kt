package com.partners.laboratorio7

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.partners.laboratorio7.database.AppDao
import com.partners.laboratorio7.database.InventaryDb
import com.partners.laboratorio7.database.Products
import com.partners.laboratorio7.database.RowDb

class SingleInventaryViewModel(private val singleInventoryId: Int = 0,
                               val database: AppDao
) : ViewModel() {
    private var _inventory = MutableLiveData<InventaryDb>()
    private var _rows = MutableLiveData<ArrayList<RowDb>>()
    val inventory: LiveData<InventaryDb>
        get() = _inventory
    val rows: MutableLiveData<ArrayList<RowDb>>
        get() = _rows


//    init {
//        _inventory.value = database
//    }
    fun setRows(id: Int){
        _rows.value?.clear()
        _rows.value = ArrayList(database.getAllRowsByInv(id))


    }

    fun onAddRow(pid:Int, id: Int) {
        val inv = database.getInventory(id)

            val row = RowDb()
            row.productId = pid
            row.inventoryId = id
            row.quantity = 0
            database.insertRow(row)
            val rw = database.getRowByInvProd(id, pid)
            database.updateFirstInv(id, rw!!.id)
            _inventory.value = database.getInventory(id)

        setRows(id)
    }

    fun checkProductExists(p: Products?): Boolean{
        val ids = arrayListOf<Int>()
        _rows.value?.map {
            ids.add(it.id)
        }
        val c =database.getProductCheck(p!!.id, ids)
        if (c != null) {
               return true
        }

        return false
    }

    fun cleanRows(){
        _rows.value?.clear()
        database.clearInventories()
        _inventory.value = database.getInventory(_inventory.value?.id ?: 0)
    }

}

