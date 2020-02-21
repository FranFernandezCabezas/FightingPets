package com.example.fightingpets

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Attack(var name: String, var damage: Int, var type: String): Parcelable