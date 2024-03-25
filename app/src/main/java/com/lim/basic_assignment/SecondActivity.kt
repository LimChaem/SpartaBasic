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

            // startActivity(intent) 만으로도 MainActivity로 이동이 가능함
            // 다만, 그럴 경우엔 기존의 MainActivitiy는 살아있고, 그 위에 차곡 차곡 쌓인다고 함.
            // FLAG_ACTIVITY_CLEAR_TOP를 사용하면 액티비티 이동 시 이동하려는 Activity를 최상단으로 올리고 나머지는 모두 삭제.
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()

        }
    }
}

