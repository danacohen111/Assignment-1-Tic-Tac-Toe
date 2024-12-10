package com.example.tictactoeassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tictactoeassignment.ui.theme.TicTacToeAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeAssignmentTheme {
                TicTacToeGame()
            }
        }
    }
}

@Composable
fun TicTacToeGame() {
    // 3x3 grid game state
    var board by remember { mutableStateOf(Array(3) { Array(3) { "" } }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var gameOver by remember { mutableStateOf(false) }
    var winnerMessage by remember { mutableStateOf("") }

    // Check for a win or draw
    fun checkForWin(): Boolean {
        for (i in 0 until 3) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                return true
            }
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) {
                return true
            }
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true
        }
        return false
    }

    // Check for a draw (board full)
    fun checkForDraw(): Boolean {
        return board.flatten().all { it.isNotEmpty() }
    }

    // Handle player move
    fun onCellClick(row: Int, col: Int) {
        if (board[row][col].isEmpty() && !gameOver) {
            board[row][col] = currentPlayer
            if (checkForWin()) {
                winnerMessage = "$currentPlayer Wins!"
                gameOver = true
            } else if (checkForDraw()) {
                winnerMessage = "It's a Draw!"
                gameOver = true
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
            }
        }
    }

    // Reset game
    fun resetGame() {
        board = Array(3) { Array(3) { "" } }
        currentPlayer = "X"
        gameOver = false
        winnerMessage = ""
    }

    // UI Layout
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                Text(
                    text = if (gameOver) winnerMessage else "Player $currentPlayer's Turn",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(20.dp))

                // Game Grid
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    for (i in 0 until 3) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            for (j in 0 until 3) {
                                Box(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .size(80.dp)
                                        .background(MaterialTheme.colorScheme.primary)
                                        .clickable { onCellClick(i, j) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = board[i][j],
                                        style = MaterialTheme.typography.headlineLarge
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Play Again Button
                if (gameOver) {
                    Button(onClick = { resetGame() }) {
                        Text(text = "Play Again")
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TicTacToeAssignmentTheme {
        TicTacToeGame()
    }
}
