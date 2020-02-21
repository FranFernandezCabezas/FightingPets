package com.example.fightingpets.newplayer

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.example.fightingpets.R
import com.example.fightingpets.databinding.InventoryFragmentBinding
import com.example.fightingpets.databinding.NewPlayerFragmentBinding
import com.example.fightingpets.inventory.InventoryFragmentDirections

class NewPlayerFragment : Fragment() {

    companion object {
        fun newInstance() = NewPlayerFragment()
    }

    private lateinit var viewModel: NewPlayerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<NewPlayerFragmentBinding>(
            inflater, R.layout.new_player_fragment, container, false)


        return binding.root
    }
}

// Fragment made for new players to select his monster from a list and create a new Firebase cloud database entry
