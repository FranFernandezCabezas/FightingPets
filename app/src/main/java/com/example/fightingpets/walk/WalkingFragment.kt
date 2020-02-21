package com.example.fightingpets.walk

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController

import com.example.fightingpets.R
import com.example.fightingpets.Repo
import com.example.fightingpets.databinding.WalkingFragmentBinding
import kotlinx.coroutines.*

class WalkingFragment : Fragment() {

    private lateinit var viewModel: WalkingViewModel
    lateinit var binding: WalkingFragmentBinding
    var stop = false

    private var job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        viewModel = WalkingViewModel()

        binding = DataBindingUtil.inflate(inflater, R.layout.walking_fragment, container, false)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        var monster = WalkingFragmentArgs.fromBundle(arguments!!).monster

        viewModel.sleepiness = monster.sleepiness


        binding.townButton.setOnClickListener{
            if (viewModel.zoneSelected != 1) {
                binding.constraintLayout.setBackgroundResource(R.drawable.posibleciudad)
                binding.bonus1ImageView.setImageResource(R.drawable.intentopocion)
                binding.bonus2ImageView.setImageResource(R.drawable.gifenergizar)
                binding.bonusesTextView.visibility = TextView.VISIBLE
                viewModel.zoneSelected = 1
                changeButtons(viewModel.zoneSelected)
            } else {
                makeWalk()
            }
        }

        binding.forestButton.setOnClickListener {
            if (viewModel.zoneSelected != 2) {
                binding.constraintLayout.setBackgroundResource(R.drawable.posiblebosque)
                binding.bonus1ImageView.setImageResource(R.drawable.intentopocion)
                binding.bonus2ImageView.setImageResource(R.drawable.gifmansana)
                binding.bonusesTextView.visibility = TextView.VISIBLE
                viewModel.zoneSelected = 2
                changeButtons(viewModel.zoneSelected)
            } else {
                makeWalk()
            }
        }

        binding.deserticButton.setOnClickListener {
            if (viewModel.zoneSelected != 3) {
                binding.constraintLayout.setBackgroundResource(R.drawable.posibleprado)
                binding.bonus1ImageView.setImageResource(R.drawable.gifmansana)
                binding.bonus2ImageView.setImageResource(R.drawable.gifmansana)
                binding.bonusesTextView.visibility = TextView.VISIBLE
                viewModel.zoneSelected = 3
                changeButtons(viewModel.zoneSelected)
            } else {
                makeWalk()
            }
        }

        binding.stopButton.setOnClickListener {
            if (!stop) {
                stop = true
                binding.areaTextview.gravity = Gravity.CENTER_HORIZONTAL
                viewModel.showItemsGotten()
                binding.stopButton.text = getString(R.string.accept)
            } else {
                stopLoop()
            }
        }

        binding.backFromWalkingImageView.setOnClickListener {
            findNavController().navigate(WalkingFragmentDirections.actionWalkingFragmentToMainZone())
        }

        binding.lifecycleOwner = this
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun stopLoop() {
        HideButtons(false)
        binding.areaTextview.gravity = Gravity.BOTTOM
        viewModel.saveItems()
        stop = false
    }

    private fun makeWalk() {
        HideButtons(true)

        coroutineScope.launch {
            while (!stop && viewModel.sleepiness > 0) {
                viewModel.makeRandomResponse()
                withContext(Dispatchers.Default) {
                    delay(1000)
                }
                if (viewModel.sleepiness <= 0) {
                    stopLoop()
                }
            }
        }
    }

    /* Changes the layout visually depending on the loop current state. */

    private fun HideButtons(hide: Boolean) {

        if (hide) {
            binding.townButton.visibility = Button.GONE
            binding.deserticButton.visibility = Button.GONE
            binding.forestButton.visibility = Button.GONE
            binding.bonusesTextView.visibility = TextView.GONE
            binding.bonus1ImageView.visibility = ImageView.GONE
            binding.bonus2ImageView.visibility = ImageView.GONE
            binding.textView.visibility = TextView.GONE
            binding.areaTextview.visibility = TextView.VISIBLE
            binding.stopButton.visibility = Button.VISIBLE
            binding.backFromWalkingImageView.visibility = ImageView.INVISIBLE
        } else {
            binding.townButton.visibility = Button.VISIBLE
            binding.deserticButton.visibility = Button.VISIBLE
            binding.forestButton.visibility = Button.VISIBLE
            binding.bonusesTextView.visibility = TextView.VISIBLE
            binding.bonus1ImageView.visibility = ImageView.VISIBLE
            binding.bonus2ImageView.visibility = ImageView.VISIBLE
            binding.textView.visibility = TextView.VISIBLE
            binding.areaTextview.visibility = TextView.GONE
            binding.stopButton.visibility = Button.GONE
            binding.backFromWalkingImageView.visibility = ImageView.VISIBLE
        }
    }


    /* Changes the button depending on the previous selection. */

    private fun changeButtons(selected: Int) {

        when(selected) {

            1 -> {
                binding.townButton.text = "Confirm: " + getString(R.string.city)
                binding.forestButton.text = getString(R.string.forest)
                binding.deserticButton.text = getString(R.string.desert)
            }

            2 -> {
                binding.townButton.text = getString(R.string.city)
                binding.forestButton.text =  "Confirm: " + getString(R.string.forest)
                binding.deserticButton.text = getString(R.string.desert)
            }

            3 -> {
                binding.townButton.text = getString(R.string.city)
                binding.forestButton.text = getString(R.string.forest)
                binding.deserticButton.text = "Confirm: " + getString(R.string.desert)
            }

            else -> {
                binding.townButton.text = getString(R.string.city)
                binding.forestButton.text = getString(R.string.forest)
                binding.deserticButton.text = getString(R.string.desert)
            }
        }

    }
}
