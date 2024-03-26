package com.lim.basic_assignment

import android.content.Intent
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
            checkAnswerAndShowToast()
        }
    }

    private fun setRandomValueBetweenOneToHundred() {
        val randomTextView = findViewById<TextView>(R.id.textViewRandom)
        val random = (1..100).random() // Random.nextInt(1 .. 100) or (1..100).random()

        // val random = Random
        // val randomValue = random.nextInt(1,100)
        // randomTextView.text = randomValue.toString()

        randomTextView.text = random.toString()

    }

    private fun setJobAndLaunch() {
        val textView = findViewById<TextView>(R.id.spartaTextView)
        /* 피드백 수정 -> job?.cancel()을 주지 않을 경우 이전 작업이 유지된 상태에서 새로운 작업 시작하므로
         메모리에 쌓이게 되어 이전 작업을 취소 하고 새로운 작업을 시작 해야 한다?*/
        job?.cancel()
        job = lifecycleScope.launch {
            for (i in 1..100) {
                if (isActive) {
                    textView.text = i.toString()
                    delay(100)

                    if (i == 100) {
                        /* job?.cancel()를 이 위치에 넣어도 작업이 완료된 후에 다시 시작 하기 전에 작업을 취소 하고
                         setJobAndLaunch()가 시작 되면서 새로운 작업이 시작 되지 않을까? */
                        setJobAndLaunch()       // 100이 됐을 때 다시 1부터 시작 하기.
                    }
                }
            }
        }
    }

    private fun checkAnswerAndShowToast() {
        val textView = findViewById<TextView>(R.id.spartaTextView)
        val randomTextView = findViewById<TextView>(R.id.textViewRandom)

        if (textView.text == randomTextView.text) {
            Toast.makeText(this, "숫자가 일치합니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SecondActivity::class.java)
            val count = textView.text.toString()
            intent.putExtra("Count", count) // Intent 사용에 익숙해지기 위해 사용해봄.
            startActivity(intent)
        } else {
            Toast.makeText(this, "숫자가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            setJobAndLaunch()                // 틀렸을 시 다시 1부터 시작
        }
    }
}