package com.example.inventory.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * from item WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    @Query("SELECT * from item ORDER BY vocEnglish ASC")
    fun getItems(): Flow<List<Item>>

    //1 is "TRUE"
    @Query("SELECT * from item WHERE vocFavorite=1")
    fun getFavoriteItems(): Flow<List<Item>>
}