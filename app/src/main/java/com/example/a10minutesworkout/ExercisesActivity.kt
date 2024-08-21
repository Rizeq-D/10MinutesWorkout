package com.example.a10minutesworkout

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a10minutesworkout.databinding.ActivityExercisesBinding
import java.util.Locale

class ExercisesActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding : ActivityExercisesBinding? = null

    private var restTime : CountDownTimer? = null
    private var restProgress = 0

    private var restTimeExercise : CountDownTimer? = null
    private var restProgressExercise = 0

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExercisesBinding.inflate(layoutInflater)
        setContentView(binding?.root)

       setSupportActionBar(binding?.toolbarExercise)

        tts = TextToSpeech(this, this)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        exerciseList = Constants.defaultExerciseList()

        binding?.toolbarExercise?.setNavigationOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

            setupRestView()
    }
    @SuppressLint("SuspiciousIndentation")
    private fun setupRestView() {
        binding?.flProgressBar?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.tvUpComingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE
            if (restTime != null) {
                restTime?.cancel()
                restProgress = 0
            }
        binding?.tvUpcomingExerciseName?.text = exerciseList!![currentExercisePosition +1]
            .getName()
        setRestProgressBar()
    }

    @SuppressLint("SetTextI18n")
    private fun setupExerciseView() {
        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.tvUpComingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE
        if (restTimeExercise != null) {
            restTimeExercise?.cancel()
            restProgressExercise = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())

        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition]
            .getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition]
            .getName()

        setExerciseProgressBar()
    }
    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress

        restTime = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                setupExerciseView()
            }
        }.start()
    }
    private fun setExerciseProgressBar() {
        binding?.progressBarExercise?.progress = restProgressExercise

        restTimeExercise = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgressExercise++
                binding?.progressBarExercise?.progress = 30 - restProgressExercise
                binding?.tvTimerExercise?.text = (30 - restProgressExercise).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition < exerciseList?.size!! -1) {
                    setupRestView()
                }else {
                    Toast.makeText(
                        this@ExercisesActivity,
                        "the 30 sec are over, move to the rest view",
                        Toast.LENGTH_LONG
                    ).show()
                }
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
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
        binding = null
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language not supported or missing data")
            }
        } else {
            Log.e("TTS", "Initialization failed")
        }
    }
    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}












