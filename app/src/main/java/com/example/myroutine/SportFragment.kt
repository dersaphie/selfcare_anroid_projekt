package com.example.myroutine
import adapter.ExerciseAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import api.ExerciseDto
import api.ExerciseRepo
import com.example.myroutine.databinding.FragmentSportBinding
import data.Exercise
import data.toExercise
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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


        view.findViewById<Button>(R.id.btnMuscleBodyWeight)?.setOnClickListener {
            val navOptions: NavOptions = NavOptions.Builder()
                .setPopUpTo(R.id.home, inclusive = false, saveState = true)
                //.setRestoreState(restoreState = true)
                .build()
                workout.clear()
                //getWorkout()
            (activity as RvSportHostFragment?)!!.getWorkoutMuscleBodyweight()
            findNavController().navigate(SportFragmentDirections.actionSportToRvSportHostFragment(), navOptions)
            //findNavController().navigate(R.id.action_home_to_sport, null, navOptions)
        }

        view.findViewById<Button>(R.id.btnMuscleEquipment)?.setOnClickListener {
            val navOptions: NavOptions = NavOptions.Builder()
                .setPopUpTo(R.id.home, inclusive = false, saveState = true)
                //.setRestoreState(restoreState = true)
                .build()
            workout.clear()
            (activity as RvSportHostFragment?)!!.getWorkoutMuscleEquipment()
            findNavController().navigate(SportFragmentDirections.actionSportToRvSportHostFragment(), navOptions)
            //findNavController().navigate(R.id.action_home_to_sport, null, navOptions)
        }




    }

}