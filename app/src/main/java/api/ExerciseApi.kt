package api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ExerciseApi {

    @Headers(
        "x-rapidapi-host: exercisedb.p.rapidapi.com",
        "x-rapidapi-key: 1d45bebd9dmshbaad6bdabadea3ep1f7a2bjsn19c506b945fe"
    )
    @GET("/exercises/equipment/body%20weight")
    fun getMuscleExerciseBodyweightFromApi(): Call<List<ExerciseDto>>

    @Headers(
        "x-rapidapi-host: exercisedb.p.rapidapi.com",
        "x-rapidapi-key: 1d45bebd9dmshbaad6bdabadea3ep1f7a2bjsn19c506b945fe"
    )
    @GET("/exercises/equipment/band")
    fun getMuscleExerciseEquipmentFromApi(): Call<List<ExerciseDto>>

    @Headers(
        "x-rapidapi-host: exercisedb.p.rapidapi.com",
        "x-rapidapi-key: 1d45bebd9dmshbaad6bdabadea3ep1f7a2bjsn19c506b945fe"
    )
    //API-Call to get cardio with bodyweigth
    @GET("/exercises/bodyPart/cardio")///bodyPart/cardio")
    fun getCardioExerciseBodyweightFromApi(): Call<List<ExerciseDto>>

    @Headers(
        "x-rapidapi-host: exercisedb.p.rapidapi.com",
        "x-rapidapi-key: 1d45bebd9dmshbaad6bdabadea3ep1f7a2bjsn19c506b945fe"
    )
    //API-Call to get cardio with equipment
    @GET("/exercises/equipment/band/bodyPart/cardio")
    fun getCardioExerciseEquipmentFromApi(): Call<List<ExerciseDto>>
}