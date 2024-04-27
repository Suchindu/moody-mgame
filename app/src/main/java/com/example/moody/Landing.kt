package com.example.moody


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity


class Landing : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainMenu::class.java))
            finish()
        }, 1000)

    }
}