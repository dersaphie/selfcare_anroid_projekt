package com.example.myroutine

import adapter.ExerciseAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import api.ExerciseRepo
import com.example.myroutine.databinding.FragmentSportknowledgeBinding
import data.Exercise

class SportknowledgeFragment: Fragment() {
    private var _binding: FragmentSportknowledgeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // set title of top action bar
        (activity as AppCompatActivity).supportActionBar?.title = this.getString(R.string.sportKnowledge)
        // Inflate the layout for this fragment
        _binding = FragmentSportknowledgeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSeeInfo.setOnClickListener{
            binding.tvRecommendation.setText(R.string.textSportKnowledge)
        }
        binding.btnLowSport.setOnClickListener {
            binding.tvRecommendation.setText(R.string.lowSportRepetition)
        }
        binding.btnMediumSport.setOnClickListener {
            binding.tvRecommendation.setText(R.string.mediumSportRepetition)
        }
        binding.btnHardSport.setOnClickListener {
            binding.tvRecommendation.setText(R.string.hardSportRepetition)
        }
    }
}