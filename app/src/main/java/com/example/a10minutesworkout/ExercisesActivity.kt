package com.example.a10minutesworkout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a10minutesworkout.databinding.ActivityExercisesBinding

class ExercisesActivity : AppCompatActivity() {

    private var binding : ActivityExercisesBinding? = null

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
    }
}












