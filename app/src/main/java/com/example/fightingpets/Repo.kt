package com.example.fightingpets

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repo {


    val db = FirebaseFirestore.getInstance()

    suspend fun getItemsData(mutableData: MutableLiveData<ArrayList<InventoryItem>>) {

        var user = FirebaseAuth.getInstance().currentUser!!.uid
        val itemList = ArrayList<InventoryItem>()


        withContext(Dispatchers.IO) {
            db.collection("Monsters").get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (doc in task.result!!) {
                        doc.reference.collection("Inventory")
                            .orderBy("name").get().addOnSuccessListener { snapshots ->
                                for (dc in snapshots!!.documentChanges) {
                                    itemList.add(
                                        createInventoryItem(
                                            dc.document.getString("name")!!,
                                            dc.document.getLong("inPossession")!!.toInt()
                                        )
                                    )
                                    println("itemList: " + itemList.size)
                                }
                                putOnImages(itemList)
                                mutableData.value = itemList
                            }
                    }
                } else {
                }
            }
        }
    }

    fun refreshData(value: String, newValue: Int) {

        var user = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("Monsters").whereEqualTo("userId", user).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {


                for (doc in task.result!!) {
                    doc.reference.update(value, newValue)
                }
            }
        }
    }
}

fun createInventoryItem(name: String, quantity: Int): InventoryItem {

    var createdItem = InventoryItem(
        name,
        getType(name),
        getValor(name),
        getDescription(name),
        R.drawable.backpack,
        quantity
    )

    return createdItem
}