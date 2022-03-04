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

class RVSportMuscleEQFragment : Fragment() {
    private var _binding: FragmentRvsportmuscleeqBinding? = null
    private val binding get() = _binding!!


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

        binding.rvWorkout.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
        binding.rvWorkout.adapter = exerciseAdapter
        workout.clear()
        getWorkoutMuscleEquipment()

    }


    // Method to get a random cocktail list - as the API doesn't provide a free API-Call to get a random cocktail list we use a work-around here:
    // We set up a for loop that requests 21 random cocktails from the API and put each of them into the cocktail list
    // The implementation of the callback works the same way as in the getWorkout() method
    fun getWorkoutMuscleEquipment() {
        workoutRepo.getMuscleEquipmentExercise(object : Callback<List<ExerciseDto>> {
            override fun onResponse(
                call: Call<List<ExerciseDto>?>,
                response: Response<List<ExerciseDto>?>
            ) {
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
                    binding.tvExerciseApiErrorText.setText(R.string.exercise_api_error_unsuccessful_response)
                    binding.tvExerciseApiErrorText.setVisibility(View.VISIBLE)
                    binding.rvWorkout.setVisibility(View.INVISIBLE)
                }
            }

            override fun onFailure(call: Call<List<ExerciseDto>>, t: Throwable) {
                binding.tvExerciseApiErrorText.setText(R.string.exercise_api_error_on_failure)
                binding.tvExerciseApiErrorText.setVisibility(View.VISIBLE)
                binding.rvWorkout.setVisibility(View.INVISIBLE)
            }
        })
    }

   }