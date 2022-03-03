package com.example.myroutine

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class Measurements : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_massurements)

        val weight: EditText = findViewById(R.id.rightArmInt)
        val armRight = findViewById<EditText>(R.id.armRightInt)
        val armLeft = findViewById<EditText>(R.id.leftArmInt)
        val breast = findViewById<EditText>(R.id.brustInt)
        val waist = findViewById<EditText>(R.id.waistInt)
        val hips = findViewById<EditText>(R.id.hipsInt)
        val legRight = findViewById<EditText>(R.id.rightUpperLegInt)
        val legLeft = findViewById<EditText>(R.id.leftUpperLegInt)

        val defaultText = "value"

        weight.setOnTouchListener({ v, event ->
            if (weight.text.toString().contentEquals(defaultText)) weight.text.clear()
            true // return is important...
        })
        armRight.setOnTouchListener({ v, event ->
            if (armRight.text.toString().contentEquals(defaultText)) armRight.setText(" ")
            true // return is important...
        })
        armLeft.setOnTouchListener({ v, event ->
            if (armLeft.text.toString().contentEquals(defaultText)) armLeft.setText(" ")
            true // return is important...
        })
        breast.setOnTouchListener({ v, event ->
            if (breast.text.toString().contentEquals(defaultText)) breast.setText(" ")
            true // return is important...
        })
        waist.setOnTouchListener({ v, event ->
            if (waist.text.toString().contentEquals(defaultText)) waist.setText(" ")
            true // return is important...
        })
        hips.setOnTouchListener({ v, event ->
            if (hips.text.toString().contentEquals(defaultText)) hips.setText(" ")
            true // return is important...
        })
        legRight.setOnTouchListener({ v, event ->
            if (legRight.text.toString().contentEquals(defaultText)) legRight.setText(" ")
            true // return is important...
        })
        legLeft.setOnTouchListener({ v, event ->
            if (legLeft.text.toString().contentEquals(defaultText)) legLeft.setText(" ")
            true // return is important...
        })

//        findViewById<View>(R.id.entire_view).setOnTouchListener( { v, event ->
//            if(weight.text.isEmpty()){
//                weight.setText(defaultText)
//            }
//           weight.setText(weight.text.toString().replace("\n", "").replace(" ",""))
//            if(armRight.text.isEmpty()){
//                armRight.setText(defaultText)
//            }
//            armRight.setText(armRight.text.toString().replace("\n", "").replace(" ",""))
//            if(armLeft.text.isEmpty()){
//                armLeft.setText(defaultText)
//            }
//            armLeft.setText(armLeft.text.toString().replace("\n", "").replace(" ",""))
//
//            if(breast.text.isEmpty()){
//                breast.setText(defaultText)
//            }
//            breast.setText(breast.text.toString().replace("\n", "").replace(" ",""))
//
//            if(waist.text.isEmpty()){
//                waist.setText(defaultText)
//            }
//            waist.setText(waist.text.toString().replace("\n", "").replace(" ",""))
//
//            if(hips.text.isEmpty()){
//                hips.setText(defaultText)
//            }
//            hips.setText(hips.text.toString().replace("\n", "").replace(" ",""))
//
//            if(legRight.text.isEmpty()){
//                legRight.setText(defaultText)
//            }
//            legRight.setText(legRight.text.toString().replace("\n", "").replace(" ",""))
//
//            if(legLeft.text.isEmpty()){
//                legLeft.setText(defaultText)
//            }
//            legLeft.setText(legLeft.text.toString().replace("\n", "").replace(" ",""))
//
//            true
//        })
        findViewById<Button>(R.id.buttonSaveMessurements).setOnClickListener {
            val weightInt = weight.text.toString().toInt()
            val armRightInt = armRight.text.toString().toInt()
            val armLeftInt = armLeft.text.toString().toInt()
            val breastInt = breast.text.toString().toInt()
            val waistInt = waist.text.toString().toInt()
            val hipsInt = hips.text.toString().toInt()
            val legRightInt = legRight.text.toString().toInt()
            val legLeftInt = legLeft.text.toString().toInt()
            val unixTime = System.currentTimeMillis() / 1000L
            val userID = Utility.db.rawQuery("SELECT USERID FROM USER WHERE NAME = ${User.currenUser?.name} AND BDAY = ${User.currenUser?.bday}", null).getInt(0)
            Utility.db.execSQL("INSERT INTO Meassurements (weight, armRight, armLeft, breast, waist, hips, legRight, legLeft, unixTime, USERID) VALUES ($weightInt, $armRightInt, $armLeftInt, $breastInt, $waistInt, $hipsInt, $legRightInt, $legLeftInt, $unixTime, $userID)")

            println("Inserted")
        }


    }
}
