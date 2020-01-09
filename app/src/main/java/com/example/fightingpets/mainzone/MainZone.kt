package com.example.fightingpets.mainzone


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.fightingpets.R
import com.example.fightingpets.databinding.FragmentMainZoneBinding

/**
 * A simple [Fragment] subclass.
 */
class MainZone : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentMainZoneBinding>(
            inflater, R.layout.fragment_main_zone, container, false)

        binding.pruebaTextView.text = "Prueba de binding"

        binding.pruebaTextView.gravity = Gravity.CENTER


        // Inflate the layout for this fragment
        return binding.root
    }
}
