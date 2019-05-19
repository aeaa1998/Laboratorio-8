package com.partners.laboratorio7.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.sql.RowId

@Dao
interface AppDao {

    //Products
    @Insert
    fun insertProduct(product: Products)

    @Query("SELECT * from products_table WHERE id = :key")
    fun getProduct(key: Int): Products?

    @Query("SELECT product_id from rows_table where id in (:id) and product_id = :key")
    fun getProductCheck(key: Int, id: List<Int>): Int?

    @Query("SELECT * from products_table WHERE code = :code")
    fun getProductByCode(code: String): Products?

    @Query("DELETE FROM products_table")
    fun clearProductsDb()

    @Query("SELECT * FROM products_table")
    fun getAllProducts(): List<Products>

    @Query("SELECT * FROM products_table WHERE code = :code")
    fun checkCodeExists(code: String): Products?

    //Inv
    @Query("UPDATE inventories_table set row_ids = row_ids+', '+:rowId WHERE id = :invId")
    fun updateInv(invId: Int, rowId: Int)

    @Query("UPDATE inventories_table set row_ids = :rowId WHERE id = :invId")
    fun updateFirstInv(invId: Int, rowId: Int)

    @Insert
    fun insertInventory(inventary: InventaryDb)

    @Query("SELECT * from inventories_table WHERE id = :key")
    fun getInventory(key: Int): InventaryDb?

    @Query("DELETE FROM inventories_table")
    fun clearInventories()

    @Query("SELECT * FROM inventories_table")
    fun getAllInventories(): List<InventaryDb>

//Rows

    @Insert
    fun insertRow(row: RowDb)

    @Query("SELECT * from rows_table WHERE id = :key")
    fun getRow(key: Int): RowDb?

    @Query("SELECT * from rows_table WHERE inventory_id = :invId AND product_id = :productId")
    fun getRowByInvProd(invId: Int, productId: Int): RowDb?



    @Query("DELETE FROM rows_table")
    fun clearRows()

    @Query("SELECT * FROM rows_table")
    fun getAllRows(): LiveData<List<RowDb>>

    @Query("SELECT * FROM rows_table WHERE inventory_id = :invId")
    fun getAllRowsByInv(invId: Int): List<RowDb>



}

