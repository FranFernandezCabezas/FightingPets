package com.example.fightingpets.mainzone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fightingpets.Monster
import com.google.firebase.database.FirebaseDatabase


class MainZoneViewModel() {

    var _monster = MutableLiveData<Monster>()

    val monster: LiveData<Monster>
        get() = _monster

    // Connects to the database
    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference("monsters")

}