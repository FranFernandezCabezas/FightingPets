package com.example.fightingpets.mainzone


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.example.fightingpets.R
import com.example.fightingpets.databinding.FragmentMainZoneBinding

/**
 * A simple [Fragment] subclass.
 */
class MainZone : Fragment() {


    private lateinit var viewModel: MainZoneViewModel


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        viewModel = MainZoneViewModel()

        val binding = DataBindingUtil.inflate<FragmentMainZoneBinding>(
            inflater, R.layout.fragment_main_zone, container, false)

        viewModel.monster.observe(this, Observer { monster ->
            binding.lifebarTextView.text = "" + monster!!.currentLifePoints + " / " + monster!!.maxLifePoints + " HP"

            binding.lifebarProgressBar.progress = monster!!.currentLifePercentage()
            binding.happinesProgressBar.progress = monster!!.happiness
            binding.hungerProgressBar.progress = monster!!.hunger
            binding.sleepProgressBar.progress = monster!!.sleepiness
        })

        // Inflate the layout for this fragment
        return binding.root
    }
}
