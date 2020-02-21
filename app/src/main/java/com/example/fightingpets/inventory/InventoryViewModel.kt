package com.example.fightingpets.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fightingpets.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@Suppress("UseExpressionBody")
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

    lateinit var repo: Repo


    init {
        coroutineScope.launch {
            repo = Repo()
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
            ItemType.HEALING -> calculateLimits("heal")
            ItemType.HUNGER -> calculateLimits("hunger")
            ItemType.FUN -> calculateLimits("happiness")
            ItemType.ENERGIZE -> calculateLimits("sleepiness")
            ItemType.TENT -> repo.refreshMonsterData("sleepiness", 100)
        }
    }


    /* Calculates if using an item makes the variable get over it's limit */

    private fun calculateLimits(valor: String) {

        var itemUsed = true

        when(valor) {

            "heal" -> {

                if (monster.value!!.currentLifePoints == monster.value!!.maxLifePoints) {
                    itemUsed = false

                } else {

                    if (monster.value!!.currentLifePoints + actualItem.value!!.valor > monster.value!!.maxLifePoints) {
                        monster.value!!.currentLifePoints = monster.value!!.maxLifePoints
                        repo.refreshMonsterData("actHP", monster.value!!.maxLifePoints)
                    } else {
                        repo.refreshMonsterData(
                            "actHP",
                            monster.value!!.currentLifePoints + actualItem.value!!.valor
                        )
                        monster.value!!.currentLifePoints += actualItem.value!!.valor
                    }
                }
            }

            "hunger" -> {

                if (monster.value!!.hunger == 100) {
                    itemUsed = false

                } else {
                    if (monster.value!!.hunger + actualItem.value!!.valor > 100) {
                        monster.value!!.hunger = 100
                        repo.refreshMonsterData("hunger", 100)
                    } else {
                        repo.refreshMonsterData(
                            "hunger",
                            monster.value!!.hunger + actualItem.value!!.valor
                        )
                        monster.value!!.hunger += actualItem.value!!.valor
                    }
                }
            }

            "happiness" -> {

                if (monster.value!!.happiness == 100) {
                    itemUsed = false

                } else {
                    if (monster.value!!.happiness + actualItem.value!!.valor > 100) {
                        monster.value!!.happiness = 100
                        repo.refreshMonsterData("happiness", 100)
                    } else {
                        repo.refreshMonsterData(
                            "happiness",
                            monster.value!!.happiness + actualItem.value!!.valor
                        )
                        monster.value!!.happiness += actualItem.value!!.valor
                    }
                }
            }

            "sleepiness" -> {

                if (monster.value!!.sleepiness == 100) {
                    itemUsed = false

                } else {
                    if (monster.value!!.sleepiness + actualItem.value!!.valor > 100) {
                        monster.value!!.sleepiness = 100
                        repo.refreshMonsterData("sleepiness", 100)
                    } else {
                        repo.refreshMonsterData(
                            "sleepiness",
                            monster.value!!.sleepiness + actualItem.value!!.valor
                        )
                        monster.value!!.sleepiness += actualItem.value!!.valor
                    }
                }
            }
        }

        if (itemUsed) {
            actualItem.value!!.quantity -= 1
            repo.refreshItemData(actualItem.value!!.name, -1)
        }
    }
}
