package data

import api.ExerciseDto
import com.google.gson.annotations.SerializedName

data class Exercise(
    val bodyPartOfEx : String,
    val equipmentForEx : String,
    val gifurlOfEx : String,
   // val idOfEx : Int,
    val nameOfEx : String,
    val targetOfEx : String )

/*  Mapping function (extension function) to map the CocktailDto to our app cocktail object */
fun ExerciseDto.toExercise(): Exercise {
    val exerciseBodyPart = bodyPartOfEx //?: ""
    val exerciseEquipment = equipmentForEx //?: ""
    val exerciseGif = gifurlOfEx //?: "https://giphy.com/gifs/onepeloton-dont-give-up-alex-toussaint-you-aint-a-quitter-YsOQGFLy6l6aIqZ0ru"
    //val exerciseId = Integer.parseInt(idOfEx)
    val exerciseName = nameOfEx //?: ""
    val exerciseTarget = targetOfEx //?: ""

    return Exercise(exerciseBodyPart, exerciseEquipment, exerciseGif, exerciseName, exerciseTarget)
}