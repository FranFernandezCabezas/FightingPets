package com.example.fightingpets.inventory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fightingpets.InventoryItem
import com.example.fightingpets.ItemType

import com.example.fightingpets.R
import com.example.fightingpets.databinding.InventoryFragmentBinding

class InventoryFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(InventoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<InventoryFragmentBinding>(
            inflater, R.layout.inventory_fragment, container, false
        )

        binding.viewModel = viewModel


        /* Retrieves the monster from the SafeARgs */

        var monster = InventoryFragmentArgs.fromBundle(arguments!!).monster

        viewModel.setMonster(monster)



        viewModel.listItems.observe(this, Observer {
            fillRecyclerItems(binding)
        })

        binding.backImageView.setOnClickListener {
            it.findNavController()
                .navigate(InventoryFragmentDirections.actionInventoryFragmentToMainZone())
        }

        binding.useButton.setOnClickListener {

            viewModel.useItem()
        }

        return binding.root
    }

    /* Receives the elements from the Repo class */

    private fun fillRecyclerItems(binding: InventoryFragmentBinding) {

        var recView = binding.itemsRecyclerView

        val adaptador: InventoryItemsAdapter

        var itemsList: ArrayList<InventoryItem>

        recView.setHasFixedSize(true)
        if (viewModel.listItems.value != null) {

            itemsList = viewModel.getItems()

            adaptador = InventoryItemsAdapter(itemsList, InventoryItemsAdapter.OnClickListener {
                binding.itemDescriptionTextview.text = it.descrip
                binding.itemNameTextView.text = it.name
                binding.objectImageView.setImageResource(it.icon)
                viewModel.setActualItem(it)
            })
        } else {
            itemsList = ArrayList()
            itemsList.add(
                InventoryItem(
                    "nada encontrado",
                    ItemType.HEALING,
                    0,
                    "Null",
                    R.drawable.backpack,
                    0
                )
            )
            adaptador = InventoryItemsAdapter(itemsList, InventoryItemsAdapter.OnClickListener {
            })
        }
        recView.adapter = adaptador
        recView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}
