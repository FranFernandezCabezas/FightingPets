package com.example.fightingpets.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fightingpets.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class InventoryViewModel : ViewModel() {

    // The internal MutableLiveData item that stores the status of the most recent request
    private val _listItems = MutableLiveData<ArrayList<InventoryItem>>()

    // The external immutable LiveData for the requests
    val listItems: LiveData<ArrayList<InventoryItem>>
        get() = _listItems

    private var _actualItem = MutableLiveData<InventoryItem>()
    val actualItem: LiveData<InventoryItem>
        get() = _actualItem

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _monster = MutableLiveData<Monster>()

    val monster: LiveData<Monster>
        get() = _monster


    init {
        coroutineScope.launch {
            var repo = Repo()
             repo.getItemsData(_listItems)
        }
    }


    fun getItems(): ArrayList<InventoryItem> {

        return if (!_listItems.value.isNullOrEmpty()) {
            _listItems.value!!
        } else {
            ArrayList()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun setActualItem(item: InventoryItem) {
        _actualItem.value = item
    }

    fun setMonster(monster: Monster) {
        _monster.value = monster
    }

    fun useItem() {
        when(actualItem.value!!.function) {
            // TODO: Method to add the new elements to the database
        }
    }
}
