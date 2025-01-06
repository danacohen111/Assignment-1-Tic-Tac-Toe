package com.example.tictactoeassignment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var statusTextView: TextView
    private val buttons = mutableListOf<Button>()
    private var currentPlayer = "X"
    private var gameOver = false
    private var movesCount = 0
    private val board = Array(3) { Array(3) { "" } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.statusTextView)

        buttons.addAll(
            listOf(
                findViewById(R.id.button_00), findViewById(R.id.button_01), findViewById(R.id.button_02),
                findViewById(R.id.button_10), findViewById(R.id.button_11), findViewById(R.id.button_12),
                findViewById(R.id.button_20), findViewById(R.id.button_21), findViewById(R.id.button_22)
            )
        )

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener { onCellClicked(index, button) }
        }
    }

    private fun onCellClicked(index: Int, button: Button) {
        val row = index / 3
        val col = index % 3

        if (board[row][col].isEmpty() && !gameOver) {

            board[row][col] = currentPlayer
            button.text = currentPlayer
            movesCount++

            if (checkForWin()) {
                statusTextView.text = "$currentPlayer Wins!"
                gameOver = true
            } else if (movesCount == 9) {
                statusTextView.text = "It's a Draw!"
                gameOver = true
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                statusTextView.text = "Player $currentPlayer's Turn"
            }
        }
    }

    private fun checkForWin(): Boolean {
        for (i in 0 until 3) {
            // Check rows
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                return true
            }
            // Check columns
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) {
                return true
            }
        }
        // Check diagonals
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true
        }
        return false
    }

    fun resetGame(view: View) {
        for (button in buttons) {
            button.text = ""
        }
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                board[i][j] = ""
            }
        }
        currentPlayer = "X"
        gameOver = false
        movesCount = 0
        statusTextView.text = "Player $currentPlayer's Turn"
    }
}
