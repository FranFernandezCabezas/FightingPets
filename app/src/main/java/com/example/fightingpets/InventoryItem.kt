package com.example.fightingpets

import android.content.Context

enum class ItemType(val value: String) {
    HEALING("Heal"),
    HUNGER("Meal"),
    ENERGIZE("Energize"),
    TENT("Sleep")
}


data class InventoryItem(var name: String, var function: ItemType, var valor: Int, var descrip: String, var icon: Int, var quantity: Int)

fun getType(name: String): ItemType {

    if (name.toLowerCase().contains("potion")) {

        return ItemType.HEALING

    } else {

        if (name.toLowerCase().contains("energizer")) {

            return ItemType.ENERGIZE

        } else {

            if (name == "Tent") {
                return ItemType.TENT
            }
        }
    }
    return ItemType.HUNGER
}

fun getValor(name: String): Int {

    if (name.contains("Super")) {
        return 50
    } else {

        if (name.contains("Ultra")) {
            return 100
        }
    }
    return 25
}

fun getDescription(name: String): String {

    when(name) {
        "Potion" -> return App.instance!!.getString(R.string.potion)

        "Superpotion" -> return App.instance!!.getString(R.string.super_potion).toString()

        "Ultrapotion" -> return App.instance!!.getString(R.string.ultra_potion).toString()

        "Basic energizer" -> return App.instance!!.getString(R.string.energizer).toString()

        "Super energizer" -> return (R.string.super_energizer).toString()

        "Ultra energizer" -> return (R.string.ultra_energizer).toString()

        "Hotdog" -> return App.instance!!.getString(R.string.hotdog).toString()

        "Super fish" -> return (R.string.fish).toString()

        "Ultra coconut" -> return (R.string.coconut).toString()

        else -> return App.instance!!.getString(R.string.sleep_bag).toString()
    }
}


fun putOnImages(items: ArrayList<InventoryItem>) {

    for (i in items) {

        when(i.function) {

            ItemType.HEALING -> i.icon = R.drawable.posiblepocion

            ItemType.HUNGER -> i.icon = R.drawable.food

            ItemType.ENERGIZE -> i.icon = R.drawable.posibleenergizar

            else -> i.icon = R.drawable.sleeping_bag

        }
    }
}