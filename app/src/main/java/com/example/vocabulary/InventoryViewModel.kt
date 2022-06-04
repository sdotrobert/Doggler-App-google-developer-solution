package com.example.inventory

import androidx.lifecycle.*
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.launch

class InventoryViewModel(private val itemDao: ItemDao) : ViewModel() {
    var isFav = MutableLiveData<Boolean>().apply { value=false }
    var allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()
    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    private fun getNewItemEntry(
        vocEnglish: String,
        vocChinese: String,
        vocFavorite: Boolean
    ): Item {
        return Item(
            vocEnglish = vocEnglish,
            vocChinese = vocChinese,
            vocFavorite = vocFavorite
        )
    }

    private fun updateItem(item: Item) {
        viewModelScope.launch {
            itemDao.update(item)
        }
    }

    private fun getUpdatedItemEntry(
        itemId: Int,
        vocEnglish: String,
        vocChinese: String,
        vocFavorite: Boolean
    ): Item {
        return Item(
            id = itemId,
            vocEnglish = vocEnglish,
            vocChinese = vocChinese,
            vocFavorite = vocFavorite
        )
    }
    fun setData(fav:Boolean) {
        if (fav==true){
            allItems = itemDao.getFavoriteItems().asLiveData()
        }else{
            allItems = itemDao.getItems().asLiveData()
        }
    }
    fun addNewItem(vocEnglish: String, vocChinese: String) {
        val newItem = getNewItemEntry(vocEnglish, vocChinese, false)
        insertItem(newItem)
    }

    fun isEntryValid(vocEnglish: String, vocChinese: String): Boolean {
        if (vocEnglish.isBlank() || vocChinese.isBlank() ) {
            return false
        }
        return true
    }

    fun retrieveItem(id: Int): LiveData<Item> {
        return itemDao.getItem(id).asLiveData()
    }

    // 切換favorite狀態
    fun favoriteSwitch(item: Item) {
        val newItem = item.copy(vocFavorite = !item.vocFavorite)
        updateItem(newItem)
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

    fun updateItem(
        itemId: Int,
        vocEnglish: String,
        vocChinese: String
    ) {
        //需要注意!!問題 (可能為null) retrieveItem(itemId).value!!.vocFavorite
        val updatedItem = getUpdatedItemEntry(itemId, vocEnglish, vocChinese, false)
        updateItem(updatedItem)
    }

    fun updateItem(
        itemId: Int,
        vocEnglish: String,
        vocChinese: String,
        vocFavorite: Boolean
    ) {
        //需要注意!!問題 (可能為null) retrieveItem(itemId).value!!.vocFavorite
        val updatedItem = getUpdatedItemEntry(itemId, vocEnglish, vocChinese, vocFavorite)
        updateItem(updatedItem)
    }

}

class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}