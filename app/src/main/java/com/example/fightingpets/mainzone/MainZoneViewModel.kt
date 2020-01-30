package com.example.fightingpets.mainzone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fightingpets.Monster
import com.example.fightingpets.R
import com.google.firebase.firestore.FirebaseFirestore

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange

class MainZoneViewModel {

    private var _monster = MutableLiveData<Monster>()

    val monster: LiveData<Monster>
        get() = _monster


    // Connects to the database
    val db = FirebaseFirestore.getInstance()

    init {

        var maxLife: Int = 0
        var actLife: Int = 0
        var hunger: Int = 0
        var happiness: Int = 0
        var sleepiness: Int = 0
        var age: Int = 0

        var user = FirebaseAuth.getInstance().currentUser

        if (user != null) {

            println("Usuario ${user!!.uid}")

            db.collection("Monsters").whereEqualTo("userId", user.uid).
                addSnapshotListener { snapshots, e ->

                if (!snapshots!!.isEmpty) {

                    for (dc in snapshots!!.documentChanges) {

                        when (dc.type) {
                            DocumentChange.Type.ADDED -> {
                                maxLife = (dc.document.getLong("maxHP"))!!.toInt()

                                actLife = (dc.document.getLong("actHP"))!!.toInt()

                                hunger = (dc.document.getLong("hunger"))!!.toInt()

                                happiness = (dc.document.getLong("happiness"))!!.toInt()

                                sleepiness = (dc.document.getLong("sleepiness"))!!.toInt()

                                age = (dc.document.getLong("age"))!!.toInt()

                                _monster.value = Monster(
                                    maxLife,
                                    actLife,
                                    hunger,
                                    happiness,
                                    sleepiness,
                                    age,
                                    R.drawable.prueba
                                )
                            }

                            DocumentChange.Type.MODIFIED -> {

                            }
                        }
                    }
                }
            }
        }
    }
}