package api

import com.google.gson.annotations.SerializedName

//Infos to be received from api
data class ExerciseDto (
    @SerializedName("bodyPart")
    val bodyPartOfEx : String,

    @SerializedName("equipment")
    val equipmentForEx : String,

    @SerializedName("gifUrl")
    val gifurlOfEx : String,

    @SerializedName("name")
    val nameOfEx : String,

    @SerializedName("target")
    val targetOfEx : String )

