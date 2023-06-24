package com.example.gq

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class CheatActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var answerTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerTextView = findViewById(R.id.answer_text_view)

        val otvValue = intent.getIntExtra("otvValue", 0)
        answerTextView.text = otvValue.toString()

        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }
}