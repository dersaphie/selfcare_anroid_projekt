package api

import com.google.gson.annotations.SerializedName

//Infos to be received from api, everything apart of the id can be null
data class ExerciseDto (
    @SerializedName("bodyPart")
    val bodyPartOfEx : String,

    @SerializedName("equipment")
    val equipmentForEx : String,

    @SerializedName("gifUrl")
    val gifurlOfEx : String,

  // @SerializedName("id")
    //val idOfEx : String,

    @SerializedName("name")
    val nameOfEx : String,

    @SerializedName("target")
    val targetOfEx : String )

