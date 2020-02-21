package com.example.fightingpets.walk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fightingpets.*
import kotlin.random.Random

class WalkingViewModel : ViewModel() {

    private var _textArea = MutableLiveData<String>()

    val textArea: LiveData<String>
        get() = _textArea
    
    var itemsFound = ArrayList<InventoryItem>()

    var zoneSelected: Int = 0

    var sleepiness: Int = 0


    init {
        _textArea.value = ""
    }


    /* Generates a random response by some random numbers. It can also be a new item. */
    fun makeRandomResponse() {

        var itemFound = false
        var randomizer = Random.nextInt(0,7)
        var exit = "\n\n"

        when(randomizer) {
            0 ->  {
                exit += getItem(0)
                itemFound = true
            }
            1 -> exit += App.instance!!.getString(R.string.firstpart1)
            2 -> exit += App.instance!!.getString(R.string.firstpart2)
            3 -> exit += App.instance!!.getString(R.string.firstpart3)
            4 -> exit += App.instance!!.getString(R.string.firstpart4)
            5 -> exit += App.instance!!.getString(R.string.firstpart5)
            6 -> exit = App.instance!!.getString(R.string.firstpart6)
            else -> {
                exit += getItem(1)
                itemFound = true
            }
        }

        if (!itemFound) {

            randomizer = Random.nextInt(1, 4)

            when (randomizer) {
                1 -> exit += " when "
                2 -> exit += " while "
                3 -> exit += " but "
                else -> exit += " and "
            }

            randomizer = Random.nextInt(1, 6)

            when (randomizer) {
                1 -> exit += App.instance!!.getString(R.string.secondpart1)
                2 -> exit += App.instance!!.getString(R.string.secondpart2)
                3 -> exit += App.instance!!.getString(R.string.secondpart3)
                4 -> exit += App.instance!!.getString(R.string.secondpart4)
                5 -> exit += App.instance!!.getString(R.string.secondpart5)
                else -> exit += App.instance!!.getString(R.string.secondpart6)
            }
        }
        sleepiness -= 1

        _textArea.value += exit
    }


    /* Checks if the new item is the first or the second possible drop and if it is already on the list of items. */
    private fun getItem(item: Int): String {
        
        var itemFound: InventoryItem
        if (item == 1) {
            itemFound = getFirstItem()
        } else {
            itemFound = getSecondItem()
        }

        var itemAdded = false

        for (it in itemsFound) {
            if (it.name == itemFound.name) {
                it.quantity += 1
                itemAdded = true
            }
        }
        if (!itemAdded) {
            itemsFound.add(itemFound)
        }
        return "Your pet found a ${itemFound.name}!!"
    }

    /* Creates the first posible drop item depending on the area selected */
    private fun getFirstItem(): InventoryItem {

        var item: InventoryItem = createInventoryItem("null", 0)

        when(zoneSelected) {
            1 -> item = createInventoryItem("Potion", 1)

            2 -> item = createInventoryItem("Basic Energizer", 1)

            3 -> item = createInventoryItem("Hotdog", 1)
        }
        return item
    }

    /* Creates the second posible drop item depending on the area selected */
    private fun getSecondItem(): InventoryItem {

        var item: InventoryItem = createInventoryItem("null", 0)

        when(zoneSelected) {
            1 -> item = createInventoryItem("Basic Energizer", 1)

            2 -> item = createInventoryItem("Hotdog", 1)

            3 -> item = createInventoryItem("Hotdog", 1)
        }
        return item
    }


    /* When stopped the loop, get's the elements created and shows it on screen */
    fun showItemsGotten() {
        _textArea.value = "You've got: "

        for (item in itemsFound) {
            _textArea.value += "\t ${item.quantity} ${item.name}"
        }
    }


    fun saveItems() {
        // TODO: Save the new items to the Inventory database
    }
}
