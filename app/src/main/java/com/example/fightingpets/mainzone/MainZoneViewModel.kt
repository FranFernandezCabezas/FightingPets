package com.example.fightingpets.mainzone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fightingpets.Attack
import com.example.fightingpets.Monster
import com.example.fightingpets.R
import com.google.firebase.firestore.FirebaseFirestore

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot

class MainZoneViewModel {

    private var _monster = MutableLiveData<Monster>()

    val monster: LiveData<Monster>
        get() = _monster

    private var _lifePercentage = MutableLiveData<Int>()

    val lifePercentage: LiveData<Int>
        get() = _lifePercentage


    // Connects to the database

    init {
        var user = FirebaseAuth.getInstance().currentUser!!.uid
        getMonster()
    }

    /* Retrieves the monster from the database */

    fun getMonster() {

        var maxLife: Int = 0
        var actLife: Int = 0
        var hunger: Int = 0
        var happiness: Int = 0
        var sleepiness: Int = 0
        var age: Int = 0
        var type = ""

        FirebaseFirestore.getInstance().collection("Monsters").document("1").get()
            .addOnSuccessListener { snapshots ->

                val attacks = getAttacks(snapshots)

                maxLife = snapshots.getLong("maxHP")!!.toInt()

                actLife = snapshots.getLong("actHP")!!.toInt()

                hunger = snapshots.getLong("hunger")!!.toInt()

                happiness = snapshots.getLong("happiness")!!.toInt()

                sleepiness = snapshots.getLong("sleepiness")!!.toInt()

                age = snapshots.getLong("age")!!.toInt()

                type = snapshots.getString("monsterType")!!

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


    /* Retrieves the attacks from a monster by receiving the monster DocumentSnapshot */

    private fun getAttacks(dc: DocumentSnapshot): ArrayList<Attack> {

        var listAttacks = ArrayList<Attack>()

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