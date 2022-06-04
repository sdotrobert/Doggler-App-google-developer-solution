package com.example.inventory.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "vocEnglish")
    val vocEnglish: String,
    @ColumnInfo(name = "vocChinese")
    val vocChinese: String,
    @ColumnInfo(name = "vocFavorite")
    val vocFavorite: Boolean
)