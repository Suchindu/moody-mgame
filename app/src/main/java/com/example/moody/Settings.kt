package com.example.moody

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class Settings : AppCompatActivity() {
    private lateinit var audioManager: AudioManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val seekBar: SeekBar = findViewById(R.id.seekBar)
        seekBar.max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        seekBar.progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Not needed
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Not needed
            }
        })
        val toggleButton: ToggleButton = findViewById(R.id.sound)
        val sharedPreferences = getSharedPreferences("com.example.moody", Context.MODE_PRIVATE)
        toggleButton.isChecked = sharedPreferences.getBoolean("toggleButtonState", false)

        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // The toggle is enabled, unmute the MediaPlayer
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false)
            } else {
                // The toggle is disabled, mute the MediaPlayer
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true)
            }
            sharedPreferences.edit().putBoolean("toggleButtonState", isChecked).apply()
        }

    }

    }
