package com.example.fightingpets.mainzone


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController

import com.example.fightingpets.R
import com.example.fightingpets.databinding.FragmentMainZoneBinding
/**
 * A simple [Fragment] subclass.
 */
class MainZone : Fragment() {


    private lateinit var viewModel: MainZoneViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {


        viewModel = MainZoneViewModel()

        val binding = DataBindingUtil.inflate<FragmentMainZoneBinding>(
            inflater, R.layout.fragment_main_zone, container, false)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this


        /* Methods to travel between Fragments */

        binding.inventoryImageView.setOnClickListener{
            findNavController().navigate(MainZoneDirections.actionMainZoneToInventoryFragment(viewModel.monster.value!!))

        }

        binding.walkImageView.setOnClickListener {
            findNavController().navigate(MainZoneDirections.actionMainZoneToWalkingFragment(viewModel.monster.value!!))
        }
        // Inflate the layout for this fragment
        return binding.root
    }
}
