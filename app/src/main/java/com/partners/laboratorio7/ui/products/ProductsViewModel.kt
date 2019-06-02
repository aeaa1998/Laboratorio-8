package com.partners.laboratorio7.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.partners.laboratorio7.database.AppDao
import com.partners.laboratorio7.database.Products

class ProductsViewModel(
                        val database: AppDao
) : ViewModel() {
    private var _products = MutableLiveData<ArrayList<Products>>()
    val products: LiveData<ArrayList<Products>>
        get() = _products

    init {
        _products.value = arrayListOf()
    }

    fun onAddRow(row: Products) {
        _products.value?.add(row)
    }

    fun getProduct(id: Int): Products?{
       return database.getProduct(id)
    }
    fun reset(){
        _products.value = ArrayList(database.getAllProducts())
    }
    fun clear(){
        database.clearInventories()
        _products.value = ArrayList(database.getAllProducts())
    }

    fun addProduct(p: Products){
        database.insertProduct(p)
        _products.value?.add(p)
    }

    fun hasCodeString(code: String): Boolean{
        val check = database.checkCodeExists(code)
        return check == null
    }

    fun getProduct(code: String): Products?{
        return database.getProductByCode(code)
    }

}
