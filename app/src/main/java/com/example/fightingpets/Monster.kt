package com.example.fightingpets

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlin.math.roundToInt

@Parcelize
data class Monster(var maxLifePoints: Int, var currentLifePoints: Int, var hunger: Int, var happiness: Int,
                   var sleepiness: Int, var age: Int, var sprite: Int, var attacks: List<Attack>, var type: String):Parcelable {

    fun currentLifePercentage(): Int {
        return ((1.0 * currentLifePoints / maxLifePoints) * 100.00).roundToInt()
    }
}