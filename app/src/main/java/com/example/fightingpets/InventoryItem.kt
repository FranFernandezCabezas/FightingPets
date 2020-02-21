package com.example.fightingpets


enum class ItemType(val value: String) {
    HEALING("Heal"),
    HUNGER("Meal"),
    FUN("happy"),
    ENERGIZE("Energize"),
    TENT("Sleep")
}


data class InventoryItem(
    var name: String,
    var function: ItemType,
    var valor: Int,
    var descrip: String,
    var icon: Int,
    var quantity: Int
)


/* Creates Inventory items by receiving the name from the database */

fun getType(name: String): ItemType {

    if (name.toLowerCase().contains("potion")) {
        return ItemType.HEALING
    } else {
        if (name.toLowerCase().contains("energizer")) {
            return ItemType.ENERGIZE
        } else {
            if (name.toLowerCase().contains("ball")) {
                return ItemType.FUN
            } else {
                if (name == "Tent") {
                    return ItemType.TENT
                }
            }
        }
        return ItemType.HUNGER
    }
}

fun getValor(name: String): Int {

    var multiplier: Int

    if (name.toLowerCase().contains("potion")) {
        multiplier = 2
    } else {
        multiplier = 1
    }

    if (name.contains("Super")) {
        return 50 * multiplier
    } else {

        if (name.contains("Ultra")) {
            return 100 * multiplier
        }
    }

    if (name.equals("Tent")) {
        return 100
    }

    return 25 * multiplier
}

fun getDescription(name: String): String {

    when (name) {
        "Potion" -> return App.instance!!.getString(R.string.potion)

        "Superpotion" -> return App.instance!!.getString(R.string.super_potion)

        "Ultrapotion" -> return App.instance!!.getString(R.string.ultra_potion)

        "Basic energizer" -> return App.instance!!.getString(R.string.energizer)

        "Super energizer" -> return App.instance!!.getString(R.string.super_energizer)

        "Ultra energizer" -> return App.instance!!.getString(R.string.ultra_energizer)

        "Hotdog" -> return App.instance!!.getString(R.string.hotdog)

        "Super fish" -> return App.instance!!.getString(R.string.fish)

        "Ultra coconut" -> return App.instance!!.getString(R.string.coconut)

        "Little funny ball" -> return App.instance!!.getString(R.string.ball)

        "Super funny puppet" -> return App.instance!!.getString(R.string.puppet)

        "Ultra funny mech" -> return App.instance!!.getString(R.string.mech)

        else -> return App.instance!!.getString(R.string.sleep_bag)
    }
}

fun putOnImages(items: ArrayList<InventoryItem>) {

    for (i in items) {

        when (i.function) {

            ItemType.HEALING -> i.icon = R.drawable.posiblepocion

            ItemType.HUNGER -> i.icon = R.drawable.food

            ItemType.FUN -> i.icon = R.drawable.little_ball

            ItemType.ENERGIZE -> i.icon = R.drawable.posibleenergizar

            else -> i.icon = R.drawable.sleeping_bag

        }
    }
}