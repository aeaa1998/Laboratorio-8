package com.partners.laboratorio7.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.sql.Timestamp
import java.time.Instant

@Entity(tableName = "inventories_table")
data class InventaryDb(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "row_ids")
    var rowsId: String = "",

    @ColumnInfo(name = "name")
    var name: String = ""
//
//    @ColumnInfo(name = "created_at")
//    var createdAt: Date = Date(System.currentTimeMillis())
)
