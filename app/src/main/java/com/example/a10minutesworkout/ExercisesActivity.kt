package com.example.a10minutesworkout

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a10minutesworkout.databinding.ActivityExercisesBinding

class ExercisesActivity : AppCompatActivity() {

    private var binding : ActivityExercisesBinding? = null

    private var restTime : CountDownTimer? = null
    private var restProgress = 0

    private var restTimeExercise : CountDownTimer? = null
    private var restProgressExercise = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExercisesBinding.inflate(layoutInflater)
        setContentView(binding?.root)

       setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExercise?.setNavigationOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

            setupRestView()
    }
    private fun setupRestView() {
            if (restTime != null) {
                restTime?.cancel()
                restProgress = 0
            }
        setRestProgressBar()
    }
    @SuppressLint("SetTextI18n")
    private fun setupExerciseView() {
        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.tvTitle?.text = "Exercise Name"
        binding?.flExerciseView?.visibility = View.VISIBLE
        if (restTimeExercise != null) {
            restTimeExercise?.cancel()
            restProgressExercise = 0
        }
        setExerciseProgressBar()
    }
    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress

        restTime = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                setupExerciseView()
            }
        }.start()
    }
    private fun setExerciseProgressBar() {
        binding?.progressBarExercise?.progress = restProgressExercise

        restTimeExercise = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgressExercise++
                binding?.progressBarExercise?.progress = 30 - restProgressExercise
                binding?.tvTimerExercise?.text = (30 - restProgressExercise).toString()
            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExercisesActivity,
                    "the 30 sec are over, move to the rest view",
                    Toast.LENGTH_LONG).show()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTime != null) {
            restTime?.cancel()
            restProgress = 0
        }
        if (restTimeExercise != null) {
            restTimeExercise?.cancel()
            restProgressExercise = 0
        }
        binding = null
    }
}












