package com.example.fightingpets

import kotlin.math.roundToInt

data class Monster(var maxLifePoints: Int, var currentLifePoints: Int, var hunger: Int, var happiness: Int, var sleepiness: Int, var age: Int, var sprite: Int) {

    fun currentLifePercentage(): Int {
        return ((1.0 * currentLifePoints / maxLifePoints) * 100.00).roundToInt()
    }
}