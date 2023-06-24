package com.example.gq

import Question
import QuizViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
class MainActivity : AppCompatActivity() {

    private val quizViewModel: QuizViewModel by
    lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView
    private var clickCount = 0
    private var otv = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)



        val currentIndex =
            savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        val provider: ViewModelProvider = ViewModelProviders.of(this)
        val quizViewModel =
            provider.get(QuizViewModel::class.java)
        Log.d(TAG, "Got a QuizViewModel:$quizViewModel")

        trueButton =
            findViewById(R.id.true_button)
        falseButton =
            findViewById(R.id.false_button)
        nextButton =
            findViewById(R.id.next_button)
        nextButton.isEnabled = false
        questionTextView =
            findViewById(R.id.question_text_view)
        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            trueButton.isEnabled = false
            falseButton.isEnabled = false
            nextButton.isEnabled = true
        }
        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            trueButton.isEnabled = false
            falseButton.isEnabled = false
            nextButton.isEnabled = true
        }

        nextButton.setOnClickListener {
            nextButton.isEnabled = false
            clickCount++
            if (clickCount >= 6) {
                trueButton.isEnabled = false
                falseButton.isEnabled = false
                nextButton.isEnabled = false
                    //startNewActivity()
                val intent = Intent(this, CheatActivity::class.java)
                intent.putExtra("otvValue", otv)
                startActivity(intent)
            }

            quizViewModel.moveToNext()
            updateQuestion()
            updateQuestion()
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
        updateQuestion()
    }
    private fun startNewActivity() {
        val intent = Intent(this, CheatActivity::class.java)
        startActivity(intent)
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun
            onSaveInstanceState(savedInstanceState: Bundle)
    {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX,
            quizViewModel.currentIndex)
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
    private fun updateQuestion() {
        val questionTextResId =
            quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer:
                            Boolean) {
        val correctAnswer =
            quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer ==
            correctAnswer) {
            otv++
            R.string.correct_toast

        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId,
            Toast.LENGTH_SHORT)
            .show()
    }
}