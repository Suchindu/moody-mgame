package com.example.moody

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class LeaderBoard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        val sharedPreferences = getSharedPreferences("com.example.moody", Context.MODE_PRIVATE)
        val scores =
            sharedPreferences.getStringSet("scores", mutableSetOf())?.map { it.toInt() } ?: listOf()

        // Sort the scores in descending order
        val sortedScores = scores.sortedDescending()

        // Display the scores. This depends on your layout and how you want to display the scores.
        // For example, if you have a TextView for each score:
        val scoreTextViews = listOf<TextView>(
            findViewById(R.id.textView),
            findViewById(R.id.textView2),
            findViewById(R.id.textView3)
            // Add more TextViews if you have more
        )

        for (i in sortedScores.indices) {
            scoreTextViews[i].text = sortedScores[i].toString()
        }

        val resetButton = findViewById<Button>(R.id.button4) // Replace with your reset button's ID

        resetButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("com.example.moody", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            for (textView in scoreTextViews) {
                textView.text = ""
            }
        }
    }
}
