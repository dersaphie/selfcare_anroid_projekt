package com.example.myroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.SeekBar
import android.widget.TextView

class Massurements : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_massurements)
        val text = findViewById<TextView>(R.id.textView_Massurements)

        var bar = findViewById<SeekBar>(R.id.seekBar2)
        bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }}
