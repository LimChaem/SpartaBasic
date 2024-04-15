package com.example.sparta_basic_week4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.sparta_basic_week4.databinding.ActivityMainBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var job: Job? = null
    private var randomValue = (1..100).random()
    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"
    private var counter = 1
    private var counter1 = 1
    private var isClickedButton = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            println("Null")
        } else (
                println(" Not null ")
                )

        if (savedInstanceState != null) {
            onRestoreInstance(savedInstanceState)

            //counter1 = savedInstanceState.getInt("counter")
            //randomValue = savedInstanceState.getInt("random")
            //isClickedButton = savedInstanceState.getBoolean("isClickedButton")

            binding.spartaTextView.text = counter1.toString()

        } else {
            setJobAndLaunch()
        }

        /*  savedInstanceState?.let {
              counter = it.getInt("counter")
          }*/

        setupButton()
        setRandomValueBetweenOneToHundred()

    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")

    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")


    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
        job?.cancel()

    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")

    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
        checkedTrueOrFalse()

        //if (!isClickedButton) {
        //    if (counter1 < 100) {
        //        counter = counter1
        //        setJobAndLaunch()
        //    }
        //}
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState")

        //counter = savedInstanceState.getInt("counter")

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")

        onSavedInstance(outState)

        //outState.putInt("counter", counter1)
        //outState.putInt("random", randomValue)
        //outState.putBoolean("isClickedButton", isClickedButton)
    }

    private fun setupButton() {
        binding.clickButton.setOnClickListener {
            checkAnswerAndShowToast()
            job?.cancel()
            isClickedButton = true
        }
    }

    private fun setRandomValueBetweenOneToHundred() {
        binding.textViewRandom.text = randomValue.toString()
    }

    private fun setJobAndLaunch() {
        job?.cancel() // job is uninitialized exception
        job = lifecycleScope.launch {
            while (counter <= 100) {
                if (isActive) {

                    binding.spartaTextView.text = counter.toString()
                    counter1 = counter++

                    delay(100) // 1초 = 1000

                } else {
                    break
                }
            }
        }
    }

    private fun checkAnswerAndShowToast() {
        val spartaText = binding.spartaTextView.text.toString()
        val randomText = binding.textViewRandom.text.toString()
        if (spartaText == randomText) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onSavedInstance(outState: Bundle) {
        outState.putInt("counter", counter1)
        outState.putInt("random", randomValue)
        outState.putBoolean("isClickedButton", isClickedButton)
    }

    private fun onRestoreInstance(savedInstanceState: Bundle) {
        counter1 = savedInstanceState.getInt("counter")
        randomValue = savedInstanceState.getInt("random")
        isClickedButton = savedInstanceState.getBoolean("isClickedButton")
    }

    private fun checkedTrueOrFalse() {
        if (!isClickedButton) {
            if (counter1 < 100) {
                counter = counter1
                setJobAndLaunch()
            }
        }
    }
}