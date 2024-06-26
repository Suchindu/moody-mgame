package com.example.moody

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import android.view.View
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import com.example.moody.databinding.ActivityMainBinding
import java.util.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var score = 0
    private val imageArray = ArrayList<ImageView>()
    private val handler = android.os.Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private lateinit var mediaPlayer: MediaPlayer
    private var isGameStarted = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.catchmood = this
        binding.score = getString(R.string.score_0)
        score = 0
        imageArray.addAll(
            listOf(
                binding.ivLaugh, binding.ivLove, binding.ivSmile,
                binding.ivCare, binding.ivAngry, binding.ivPain,
                binding.ivEmotion, binding.ivWow, binding.ivSad
            )
        )
        mediaPlayer = MediaPlayer.create(this, R.raw.bg)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

//        imageArray.forEach { it.visibility = View.INVISIBLE }
        binding.playbutton.setOnClickListener {
            isGameStarted = true
            playAndRestart()
        }

        onBackPressedDispatcher.addCallback(this) {
            // Stop and prepare the MediaPlayer
            mediaPlayer.stop()
            mediaPlayer.prepare()

            // Call the super method to handle the back button press
            isEnabled = false
            onBackPressed()
        }


    }
    fun saveScore(score: Int) {
        val sharedPreferences =
            getSharedPreferences("com.example.moody", Context.MODE_PRIVATE)
        val scores = sharedPreferences.getStringSet("scores", mutableSetOf())
            ?: mutableSetOf()
        scores.add(score.toString())
        sharedPreferences.edit().putStringSet("scores", scores).apply()
    }

    private fun hideImages() {
        runnable = Runnable {
            imageArray.forEach { it.visibility = View.INVISIBLE }
            imageArray[Random().nextInt(8)].visibility = View.VISIBLE
            handler.postDelayed(runnable, 500)
        }
        handler.post(runnable)
    }

    @SuppressLint("SetTextI18n")
    fun increaseScore() {
        if(isGameStarted) {
            score += 5
            binding.score = "Score : $score "
        }
    }

    @SuppressLint("SetTextI18n")
    fun playAndRestart() {
        binding.playbutton.isEnabled = false
        score = 0
        binding.score = "Score : 0"
        hideImages()
        binding.time = "Time : 10"
        imageArray.forEach { it.visibility = View.INVISIBLE }

        object : CountDownTimer(10000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                binding.time = getString(R.string.time_up)
                handler.removeCallbacks(runnable)
                binding.playbutton.isEnabled = true

                AlertDialog.Builder(this@MainActivity).apply {
                    setCancelable(false)
                    setTitle(getString(R.string.game_name))
                    setMessage("YOUR SCORE IS : $score")
                    setPositiveButton(getString(R.string.yes)) { _, _ -> playAndRestart() }
                    setNegativeButton(getString(R.string.no)) { _, _ ->
                        score = 0
                        binding.score = "Score : 0"
                        binding.time = "Time : 0"
                        imageArray.forEach { it.visibility = View.INVISIBLE }
                        mediaPlayer.stop()
                        mediaPlayer.prepare()

                        val intent = Intent(this@MainActivity, MainMenu::class.java)
                        // Start the MainMenu
                        startActivity(intent)
                        finish()
                    }

                }.create().show()
                // Save the score
                saveScore(score)

            }

            @SuppressLint("SetTextI18n")
            override fun onTick(tick: Long) {
                binding.time = "Time : " + tick / 1000
            }



        }.start()
    }

}