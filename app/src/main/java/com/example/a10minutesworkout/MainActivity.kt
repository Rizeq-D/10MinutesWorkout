package com.example.a10minutesworkout

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.a10minutesworkout.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {

    private var binding : ActivityMainBinding? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        binding?.flStart?.setOnClickListener{
            val intent = Intent(this@MainActivity, ExercisesActivity::class.java)
            startActivity(intent)
            Toast.makeText(this@MainActivity,
                "Here we will start the exercises.", Toast.LENGTH_LONG).show()

        }

    }
    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}
