package com.lim.basic_assignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val count = intent.getStringExtra("Count")
        val countText = findViewById<TextView>(R.id.tv_count)
        countText.text = count

        val btn_back = findViewById<Button>(R.id.btn_back)
        btn_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

            // 그냥 finish()만 할 경우 기존의 숫자가 맞은 상태로 멈춰 있음.
            // 그래서 Intent를 통해서 startActivity(intent)로 새로운 액티비티를 시작하니까 처음부터 진행되는 것을 확인

            // 개인적으로 생각해 본 것은 SecondActivity가 종료 되면서,
            // startActivity(intent)를 통해 액티비티 생명 주기에 따라 새로운 Activity가 onCreate()되면서
            // 새로운 값이 랜덤으로 형성 되고, 카운트가 시작 되는 것이 아닐까 생각함.

        }
    }
}