package com.example.pip_demo.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pip_demo.R
import com.example.pip_demo.custom.WhiteboardView
import com.example.pip_demo.databinding.ActivityMainBinding
import com.example.pip_demo.databinding.ActivityWhiteboardBinding

class Whiteboard : AppCompatActivity() {
    private lateinit var binding: ActivityWhiteboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_whiteboard)
        binding = ActivityWhiteboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val whiteboardView = binding.whiteboardView
        binding.drawButton.setOnClickListener {
            whiteboardView.setDrawingMode()
        }

        binding.eraseButton.setOnClickListener {
            whiteboardView.setEraseMode()
        }

        binding.clearButton.setOnClickListener {
            whiteboardView.clearWhiteboard()
        }


    }
}