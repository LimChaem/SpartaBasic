package com.lim.spartabasic

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // val = value = 변경 불가능
        // var = variable = 변경 가능한.
        val textView = findViewById<TextView>(R.id.textView)
        val button = findViewById<Button>(R.id.btn_click)

        button.setOnClickListener {
            textView.text = "welcom to basic class!"
        }

        var i = 0
        while (i < 100) {
            //textView.text = "$i"
            textView.text = i.toString()
            i += 1 // i++, ++i 증감 연산자.
        }
    }
}