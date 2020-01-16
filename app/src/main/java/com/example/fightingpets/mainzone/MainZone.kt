package com.example.fightingpets.mainzone


import android.annotation.SuppressLint
import android.graphics.Color
import android.media.Image
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.fightingpets.Monster

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

        val binding = DataBindingUtil.inflate<FragmentMainZoneBinding>(
            inflater, R.layout.fragment_main_zone, container, false)

        binding.pruebaTextView.text = "Sprite aun no creado de bichito mono"

        binding.pruebaTextView.gravity = Gravity.CENTER

        binding.monsterSpriteImageView.setImageResource(viewModel.monster.value!!.sprite)

        binding.lifebarTextView.text = "" + viewModel.monster.value!!.currentLifePoints + " / " + viewModel.monster.value!!.maxLifePoints + " HP"

        binding.lifebarProgressBar.progress = viewModel.monster.value!!.currentLifePercentage()

        // Inflate the layout for this fragment
        return binding.root
    }
}
