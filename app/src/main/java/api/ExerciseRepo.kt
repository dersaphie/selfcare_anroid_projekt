package api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ExerciseRepo {

    private val exerciseApi: ExerciseApi

    init {
        // Set up the detailed logging via HttpLoggingInterceptor
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        // Create retrofit with parameters
        val retrofit = Retrofit.Builder()
            .baseUrl("https://exercisedb.p.rapidapi.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        exerciseApi = retrofit.create(ExerciseApi::class.java)
    }

    // Method for API-Call to get a muscle exercise with body weight
    fun getMuscleBodyweightExercise(callback: Callback<List<ExerciseDto>>) {
        val exerciseApiResultCall = exerciseApi.getMuscleExerciseBodyweightFromApi()
        exerciseApiResultCall.enqueue(callback)
    }

    // Method for API-Call to get a muscle exercise with equipment
    fun getMuscleEquipmentExercise(callback: Callback<List<ExerciseDto>>) {
        val exerciseApiResultCall = exerciseApi.getMuscleExerciseEquipmentFromApi()
        exerciseApiResultCall.enqueue(callback)
    }

    // Method for API-Call to get a cardio exercise with body weight and equipment
    fun getCardioCombinedExercise(callback: Callback<List<ExerciseDto>>) {
        val exerciseApiResultCall = exerciseApi.getCardioExerciseCombinedFromApi()
        exerciseApiResultCall.enqueue(callback)
    }

}