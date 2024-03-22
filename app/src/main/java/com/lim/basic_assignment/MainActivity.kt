package com.lim.basic_assignment

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupButton()
        setRandomValueBetweenOneToHundred()
        setJobAndLaunch()
    }


    private fun setupButton() {
        val button = findViewById<Button>(R.id.clickButton)
        button.setOnClickListener {
            job?.cancel()
            checkAnswerAndShowToas()
        }
    }

    private fun setRandomValueBetweenOneToHundred() {
        val randomTextView = findViewById<TextView>(R.id.textViewRandom)
        val random = Random
        var randomValue = random.nextInt(1, 100)

        randomTextView.text = randomValue.toString()
    }

    private fun setJobAndLaunch() {
        val textView = findViewById<TextView>(R.id.spartaTextView)
        job = lifecycleScope.launch {
            for (i in 1..100) {
                if (isActive) {
                    textView.text = i.toString()
                    delay(500)

                    /* if (i == 100) {         // 100이 됐을 때 다시 1부터 시작하기.
                        setJobAndLaunch()
                    }*/
                }
            }
        }
    }

    private fun checkAnswerAndShowToas() {
        val textView = findViewById<TextView>(R.id.spartaTextView)
        val randomTextView = findViewById<TextView>(R.id.textViewRandom)

        if (textView == randomTextView) {
            Toast.makeText(this, "숫자가 일치합니다.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "숫자가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}