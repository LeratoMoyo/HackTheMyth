package com.example.hackthemyth

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class MainActivity : Activity() {

    data class Question(
        val statement: String,
        val correctAnswer: Boolean,
        val explanation: String
    )

    private val questions = arrayOf(
        Question(
            "Toothpaste on headlights makes them shiny and clean.",
            false,
            "Myth:It may clean on one side, but it does not clean on the inside or provide a lasting fix."
        ),
        Question(
            "Rub a bar of soap on both sides of a tough zipper to make is slide smoothly.",
            true,
            "Hack:Bar soap contains fats and oils that create a slippery, thin layer over the metal or plastic teeth."
        ),
        Question(
            "Closing unused apps always saves a lot of battery.",
            false,
            "Myth:Smart phones manage background apps automatically. Closing apps may allow use of more power."
        )
    )
    private var currentQuestionIndex = 0
    private var score = 0
    private var hasAnswered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showWelcomeScreen()
    }

    private fun showWelcomeScreen() {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(40, 80, 40, 40)
        layout.setBackgroundColor(Color.rgb(30, 144, 255))

        val title = TextView(this)
        title.text = "Welcome to Myth or Hack!"
        title.textSize = 28f
        title.setTextColor(Color.WHITE)
        title.gravity = android.view.Gravity.CENTER
        title.setTypeface(title.typeface, android.graphics.Typeface.BOLD)

        val description = TextView(this)
        description.text =
            "Test your ability to sniff out the truths and lies.\n\nIs it a Hack or a Myth"
        description.textSize = 18f
        description.setTextColor(Color.WHITE)
        description.gravity = android.view.Gravity.CENTER
        description.setPadding(0, 30, 0, 30)

        val startButton = Button(this)
        startButton.text = "Let the quiz begin"

        layout.addView(title)
        layout.addView(description)
        layout.addView(startButton)

        setContentView(layout)

        startButton.setOnClickListener {
            currentQuestionIndex = 0
            score = 0
            showQuestionScreen()
        }
    }

    private fun showQuestionScreen() {
        hasAnswered = false

        val question = questions[currentQuestionIndex]

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(40, 80, 40, 40)

        val questionNumber = TextView(this)
        questionNumber.text = "Question ${currentQuestionIndex + 1} of ${questions.size}"
        questionNumber.textSize = 18f

        val statement = TextView(this)
        statement.text = question.statement
        statement.textSize = 22f
        statement.setPadding(0, 30, 0, 30)

        val trueButton = Button(this)
        trueButton.text = "Hack"

        val falseButton = Button(this)
        falseButton.text = "Myth"

        val feedback = TextView(this)
        feedback.textSize = 20f
        feedback.setPadding(0, 30, 0, 30)

        val nextButton = Button(this)
        nextButton.text = "Next"
        nextButton.isEnabled = false

        layout.addView(questionNumber)
        layout.addView(statement)
        layout.addView(trueButton)
        layout.addView(falseButton)
        layout.addView(feedback)
        layout.addView(nextButton)

        setContentView(layout)

        trueButton.setOnClickListener {
            checkAnswer(true, feedback, nextButton, trueButton, falseButton)
        }

        falseButton.setOnClickListener {
            checkAnswer(false, feedback, nextButton, trueButton, falseButton)
        }

        nextButton.setOnClickListener {
            currentQuestionIndex++

            if (currentQuestionIndex < questions.size) {
                showQuestionScreen()
            } else {
                showScoreScreen()
            }
        }
    }

    private fun checkAnswer(
        userAnswer: Boolean,
        feedback: TextView,
        nextButton: Button,
        trueButton: Button,
        falseButton: Button
    ) {
        if (hasAnswered) return

        val question = questions[currentQuestionIndex]

        if (userAnswer == question.correctAnswer) {
            score++
            feedback.text = "\uD83D\uDC4D\uD83C\uDFFD Correct! That is spot on."
            feedback.setTextColor(Color.GREEN)
        } else {
            feedback.text = "\uD83D\uDC4E\uD83C\uDFFD Wrong! False alarm."
            feedback.setTextColor(Color.RED)
        }

        hasAnswered = true
        nextButton.isEnabled = true
        trueButton.isEnabled = false
        falseButton.isEnabled = false
    }

    private fun showScoreScreen() {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = android.view.Gravity.CENTER
        layout.setPadding(40, 80, 40, 40)

        val scoreText = TextView(this)
        scoreText.text = "Your Score: $score out of ${questions.size}"
        scoreText.textSize = 26f
        score.gravity = android.view.Gravity.CENTER
        scoreText.setPadding(0, 0, 0, 30)

        val verdict = TextView(this)
        verdict.textSize = 24f
        verdict.gravity = android.view.Gravity.CENTER
        verdict.setTypeface(verdict.typeface, android.graphics.Typeface.BOLD)
        verdict.setPadding(0, 0, 0, 30)

        verdict.text = when (score) {
            3 -> "THE VERDICT: \nMaster Hacker!\nYou know your real-life hacks well, great work."
            2 -> "THE VERDICT:\nSmart Solver!\nYou can spot most hacks and myths, most days."
            else -> "THE VERDICT:\nBe vigilant!\nSome hacks are just for clicks."
        }

        val reviewButton = Button(this)
        reviewButton.text = "Review Answers"

        layout.addView(scoreText)
        layout.addView(verdict)
        layout.addView(reviewButton)

        setContentView(layout)

        reviewButton.setOnClickListener {
            showReviewScreen()
        }
    }

    private fun showReviewScreen() {
        val ScrollView = ScrollView(this)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        setPadding(40, 60, 40, 40)

        val title = TextView(this)
        title.text = "Review Answers"
        title.textSize = 26f
        title.gravity = android.view.Gravity.CENTER
        title.setTypeface(title.typeface, android.graphics.Typeface.BOLD)
        title.setPadding(0, 0, 0, 30)

        layout.addView(reviewText)

        for ((index, question) in questions.withIndex()) {
            val answerText = if (question.correctAnswer) "Hack" else "Myth"

            val reviewText = TextView(this)
            reviewText.text =
                "Question ${index + 1}\n\n" +
                        "Statement: \n${question.statement}\n\n" +
                        "Explanation: \n${question.explanation}\n"

            reviewText.textSize = 17f
            reviewText.setPadding(20, 20, 20, 40)

            layout.addView(reviewText)
        }
        val restartButton = Button(this)
        restartButton.text = "Restart Quiz"

        layout.addView(restartButton)

        scrollView.addView(layout)
        setContentView(scrollView)

        restartButton.setOnClickListener {
            showWelcomeScreen()
        }
    }
}