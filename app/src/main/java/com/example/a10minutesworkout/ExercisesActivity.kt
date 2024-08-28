package com.example.a10minutesworkout

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a10minutesworkout.databinding.ActivityExercisesBinding
import com.example.a10minutesworkout.databinding.DialogCustomeBackConfirmationBinding
import java.util.Locale

class ExercisesActivity: AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding: ActivityExercisesBinding? = null

    private var restTime: CountDownTimer? = null
    private var restProgress = 0

    private var restTimeExercise: CountDownTimer? = null
    private var restProgressExercise = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExercisesBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExercise?.setNavigationOnClickListener{
            customDialogForBackButton()
        }

        exerciseList = Constants.defaultExerciseList()
        tts = TextToSpeech(this, this)

        playStartSound()
        setupRestView()
        setupExerciseRecyclerView()
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomeBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.btnYes.setOnClickListener {
            this@ExercisesActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener{
            customDialog.dismiss()
        }
        customDialog.show()
    }

    private fun setupExerciseRecyclerView() {
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
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

            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
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

            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {

                if (currentExercisePosition < exerciseList?.size!! -1) {
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }else {
                    Log.d("ExercisesActivity", "All exercises completed. Starting FinishActivity...")
                    val intent =
                        Intent(this@ExercisesActivity, FinishActivity::class.java)
                    startActivity(intent)
                    finish()
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
        if (player != null){
            player!!.stop()
            player?.release()
            player = null
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
    private fun playStartSound() {
        try {
            val soundURI = Uri.parse(
                "android.resource://com.example.a10minutesworkout/" + R.raw.start_engine)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}













