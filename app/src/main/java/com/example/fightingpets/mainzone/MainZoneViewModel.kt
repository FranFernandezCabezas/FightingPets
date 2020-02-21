package com.example.fightingpets.mainzone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fightingpets.Attack
import com.example.fightingpets.Monster
import com.example.fightingpets.R
import com.google.firebase.firestore.FirebaseFirestore

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot

@Suppress("VARIABLE_WITH_REDUNDANT_INITIALIZER")
class MainZoneViewModel {

    private var _monster = MutableLiveData<Monster>()

    val monster: LiveData<Monster>
        get() = _monster

    private var _lifePercentage = MutableLiveData<Int>()

    val lifePercentage: LiveData<Int>
        get() = _lifePercentage

    var user = FirebaseAuth.getInstance().currentUser!!.uid

    // Connects to the database

    init {
        getMonster()
    }

    /* Retrieves the monster from the database */

    fun getMonster() {

        var maxLife = 0
        var actLife = 0
        var hunger = 0
        var happiness = 0
        var sleepiness = 0
        var age = 0
        var type = ""


        FirebaseFirestore.getInstance().collection("Monsters").whereEqualTo("userId", user).get()
            .addOnSuccessListener { snapshots ->

                for (monster in snapshots) {

                    val attacks = getAttacks(monster)

                    maxLife = monster.getLong("maxHP")!!.toInt()

                    actLife = monster.getLong("actHP")!!.toInt()

                    hunger = monster.getLong("hunger")!!.toInt()

                    happiness = monster.getLong("happiness")!!.toInt()

                    sleepiness = monster.getLong("sleepiness")!!.toInt()

                    age = monster.getLong("age")!!.toInt()

                    type = monster.getString("monsterType")!!

                    _monster.value = Monster(
                        maxLife,
                        actLife,
                        hunger,
                        happiness,
                        sleepiness,
                        age,
                        R.drawable.prueba,
                        attacks,
                        type
                    )
                    _lifePercentage.value = _monster.value!!.currentLifePercentage()
                }
            }
    }


    /* Retrieves the attacks from a monster by receiving the monster DocumentSnapshot */

    private fun getAttacks(dc: DocumentSnapshot): ArrayList<Attack> {

        val listAttacks = ArrayList<Attack>()

        val attackDB = FirebaseFirestore.getInstance().collection("Config").document("combat")
            .collection("Attacks")

        attackDB.addSnapshotListener { attackSnapshots, e ->

            if (attackSnapshots != null) {

                if (!attackSnapshots.isEmpty) {
                    for (adc in attackSnapshots.documents) {

                        if (adc.getString("name") == dc.getString("attack1") || adc.getString("name") == dc.getString(
                                "attack2"
                            )
                        ) {
                            listAttacks.add(
                                Attack(
                                    adc.getString("name")!!,
                                    adc.getLong("damage")!!.toInt(),
                                    adc.getString("type")!!
                                )
                            )
                        }
                    }
                }
            }
        }
        return listAttacks
    }
}