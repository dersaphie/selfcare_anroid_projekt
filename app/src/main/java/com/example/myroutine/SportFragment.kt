package com.example.myroutine
import adapter.ExerciseAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import api.ExerciseRepo
import com.example.myroutine.databinding.FragmentSportBinding
import data.Exercise


class SportFragment : Fragment() {
    private var _binding: FragmentSportBinding? = null
    private val binding get() = _binding!!


    private var workoutRepo = ExerciseRepo()
    private var workout: MutableList<Exercise> = mutableListOf()
    val exerciseAdapter = ExerciseAdapter(workout)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSportBinding.inflate(layoutInflater, container, false)
        return binding.root
        //inflater.inflate(R.layout.fragment_sport, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<Button>(R.id.btnMuscleEquipment)?.setOnClickListener {
            val navOptions: NavOptions = NavOptions.Builder()
                .setPopUpTo(R.id.sportFragment, inclusive = false, saveState = true)
                //.setRestoreState(restoreState = true)
                .build()
            findNavController().navigate(SportFragmentDirections.actionSportToRVSportMuscleEQFragment(), navOptions)
        }

        view.findViewById<Button>(R.id.btnMuscleBodyWeight)?.setOnClickListener {
            val navOptions: NavOptions = NavOptions.Builder()
                .setPopUpTo(R.id.sportFragment, inclusive = false, saveState = true)
                .build()
            findNavController().navigate(SportFragmentDirections.actionSportToRVSportMuscleBWFragment(), navOptions)
        }

        view.findViewById<Button>(R.id.btnCardioCombined)?.setOnClickListener {
            val navOptions: NavOptions = NavOptions.Builder()
                .setPopUpTo(R.id.sportFragment, inclusive = false, saveState = true)
                .build()
            findNavController().navigate(SportFragmentDirections.actionSportToRVSportCardioCCFragment(), navOptions)
        }


    }

}