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
import com.lim.basic_assignment.databinding.ActivityMainBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var job: Job? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
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
        binding.clickButton.setOnClickListener {
            job?.cancel()
            checkAnswerAndShowToast()
        }
    }

    private fun setRandomValueBetweenOneToHundred() {
        val random = (1..100).random()
        binding.textViewRandom.text = random.toString()

    }

    private fun setJobAndLaunch() {

        job?.cancel()
        job = lifecycleScope.launch {
            for (i in 1..100) {
                if (isActive) {
                    binding.spartaTextView.text = i.toString()
                    delay(100)

                    if (i == 100) {
                        setJobAndLaunch()
                    }
                }
            }
        }
    }

    private fun checkAnswerAndShowToast() {

        if (binding.spartaTextView.text.toString() == binding.textViewRandom.text.toString()) {
            Toast.makeText(this, "숫자가 일치합니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("Count", binding.spartaTextView.text.toString())
            startActivity(intent)
        } else {
            Toast.makeText(this, "숫자가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            setJobAndLaunch()                // 틀렸을 시 다시 1부터 시작
        }
    }
}