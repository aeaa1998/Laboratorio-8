package com.partners.laboratorio7.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.time.Instant

@Entity(tableName = "products_table")
data class Products(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "code")
    var code: String = ""

)