package recyclerview

import adapter.ExerciseAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import api.ExerciseDto
import api.ExerciseRepo
import com.example.myroutine.R
import com.example.myroutine.databinding.FragmentRvsportmuscleeqBinding
import data.Exercise
import data.toExercise
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Magic is happening here :D.. No.
 * In the RVSportMuscleEQ Fragment Adapter and Repo are brought together to
 * get Data in form of the Workout
 */
class RVSportMuscleEQFragment : Fragment() {
    //binding
    private var _binding: FragmentRvsportmuscleeqBinding? = null
    private val binding get() = _binding!!

    //Initializing for Api call
    private var workoutRepo = ExerciseRepo()
    private var workout: MutableList<Exercise> = mutableListOf()
    val exerciseAdapter = ExerciseAdapter(workout)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRvsportmuscleeqBinding.inflate(layoutInflater, container,false)
        return binding.root
        //inflater.inflate(R.layout.fragment_sport, container, false)
    }

    override fun onResume() {
        super.onResume()
        //functionality of RecyclerView Workout
        binding.rvWorkout.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
        binding.rvWorkout.adapter = exerciseAdapter
        //clear to get a new workout
        workout.clear()
        //get new workout
        getWorkoutMuscleEquipment()

    }


    // Method to get a workout - as the API-Call is set up with the fix parameter Band this method will return all exercises with Band as Equipment.
    fun getWorkoutMuscleEquipment() {
        // Get the workout from the exercise repo --> callback implementation is necessary
        workoutRepo.getMuscleEquipmentExercise(object : Callback<List<ExerciseDto>> {
            override fun onResponse(
                call: Call<List<ExerciseDto>?>,
                response: Response<List<ExerciseDto>?>
            ) {
                // If the response is successful we get back a workout
                // --> We have to 1. set the visibility of the views accordingly 2. add exercise to the list 3. notify the adapter about the change
                if (response.isSuccessful) {
                    Log.d("MainActivity", "getWorkout(): onResponse + isSuccessful")
                    binding.tvExerciseApiErrorText.setVisibility(View.GONE)
                    binding.rvWorkout.setVisibility(View.VISIBLE)
                    val exerciseApiResult = response.body()

                    exerciseApiResult?.let { result ->
                        for (i in 0..6) {
                            workout.add(result[i].toExercise())
                            //workout.addAll(result.map { it.toExercise() })
                            exerciseAdapter.notifyDataSetChanged()
                        }
                    }
                } else {
                    // If the response wasn't successful (that means we reached the API but we didn't get back a valid object, e.g. because of a non-valid API key)
                    // --> We have to inform the user about the problem
                    binding.tvExerciseApiErrorText.setText(R.string.exercise_api_error_unsuccessful_response)
                    binding.tvExerciseApiErrorText.setVisibility(View.VISIBLE)
                    binding.rvWorkout.setVisibility(View.INVISIBLE)
                }
            }
            // If the call wasn't successful we get no response back (that means we didn't reach the API e.g. because of a missing internet connection or permission)
            // --> We have to inform the user about the problem
            override fun onFailure(call: Call<List<ExerciseDto>>, t: Throwable) {
                binding.tvExerciseApiErrorText.setText(R.string.exercise_api_error_on_failure)
                binding.tvExerciseApiErrorText.setVisibility(View.VISIBLE)
                binding.rvWorkout.setVisibility(View.INVISIBLE)
            }
        })
    }

   }