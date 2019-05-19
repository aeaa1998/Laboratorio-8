package com.partners.laboratorio7.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rows_table")
data class RowDb(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "product_id")
    var productId: Int = 0,

    @ColumnInfo(name = "inventory_id")
    var inventoryId: Int = 0,


    @ColumnInfo(name = "quantity")
    var quantity: Int = 0
)
