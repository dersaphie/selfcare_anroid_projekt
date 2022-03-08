package api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Header and specific call we want give to the API 
 */
interface ExerciseApi {

    @Headers(
        "x-rapidapi-host: exercisedb.p.rapidapi.com",
        "x-rapidapi-key: 4d120b45ebmsh0fb0d42c089a67dp1a34f3jsn30529912b963"
    )
    @GET("/exercises/equipment/body%20weight")
    fun getMuscleExerciseBodyweightFromApi(): Call<List<ExerciseDto>>

    @Headers(
        "x-rapidapi-host: exercisedb.p.rapidapi.com",
        "x-rapidapi-key: 4d120b45ebmsh0fb0d42c089a67dp1a34f3jsn30529912b963"
    )
    @GET("/exercises/equipment/band")
    fun getMuscleExerciseEquipmentFromApi(): Call<List<ExerciseDto>>

    @Headers(
        "x-rapidapi-host: exercisedb.p.rapidapi.com",
        "x-rapidapi-key: 4d120b45ebmsh0fb0d42c089a67dp1a34f3jsn30529912b963"
    )
    //API-Call to get cardio with combined
    @GET("/exercises/bodyPart/cardio")
    fun getCardioExerciseCombinedFromApi(): Call<List<ExerciseDto>>

}