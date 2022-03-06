package data

import api.ExerciseDto


data class Exercise(
    val bodyPartOfEx : String,
    val equipmentForEx : String,
    val gifurlOfEx : String,
   // val idOfEx : Int,
    val nameOfEx : String,
    val targetOfEx : String )

/*  Mapping function (extension function) to map the ExerciseDto to our app Exercise object */
fun ExerciseDto.toExercise(): Exercise {
    val exerciseBodyPart = bodyPartOfEx //?: ""
    val exerciseEquipment = equipmentForEx //?: ""
    val exerciseGif = gifurlOfEx //?: "https://giphy.com/gifs/onepeloton-dont-give-up-alex-toussaint-you-aint-a-quitter-YsOQGFLy6l6aIqZ0ru"
    //val exerciseId = Integer.parseInt(idOfEx)
    val exerciseName = nameOfEx //?: ""
    val exerciseTarget = targetOfEx //?: ""

    return Exercise(exerciseBodyPart, exerciseEquipment, exerciseGif, exerciseName, exerciseTarget)
}