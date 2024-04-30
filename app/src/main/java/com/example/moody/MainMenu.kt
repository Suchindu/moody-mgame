package com.example.moody

import android.content.Intent
import android.os.Bundle
import android.widget.Button
//import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity



class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        val startbutton: Button = findViewById(R.id.button)

        startbutton.setOnClickListener {
            // Create an Intent to start MainActivity
            val intent = Intent(this, MainActivity::class.java)
            // Start the MainActivity
            startActivity(intent)
        }
        val settingbutton: Button = findViewById(R.id.button2)

        settingbutton.setOnClickListener {
            // Create an Intent to start MainActivity
            val intent = Intent(this, Settings::class.java)
            // Start the MainActivity
            startActivity(intent)
        }
        val leaderbbutton: Button = findViewById(R.id.button3)

        leaderbbutton.setOnClickListener {
            // Create an Intent to start MainActivity
            val intent = Intent(this, LeaderBoard::class.java)
            // Start the MainActivity
            startActivity(intent)
        }
        }

    }
