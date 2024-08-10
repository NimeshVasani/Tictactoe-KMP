package org.nmvasani.tictactoe.repositories

class MainRepository {
    var board: Array<Array<String>> = Array(3) { Array(3) { "" } }
    var currentPlayer: String = "X"
    var gameOver: Boolean = false
    var winner: String? = null
    var difficulty: Difficulty = Difficulty.EASY
    var winningCells: List<Pair<Int, Int>>? = null


    fun makeMove(row: Int, col: Int): Boolean {
        if (board[row][col].isEmpty() && !gameOver) {
            board[row][col] = currentPlayer
            if (checkWin(currentPlayer)) {
                winner = currentPlayer
                gameOver = true
            } else if (board.all { row -> row.all { it.isNotEmpty() } }) {
                gameOver = true
                winningCells = null
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
            }
            return true
        }
        return false
    }

    fun computerMove() {

        when (difficulty) {
            Difficulty.EASY -> makeRandomMove()
            Difficulty.MEDIUM -> makeMediumMove()
            Difficulty.HARD -> makeBestMove()
        }
    }

    private fun makeRandomMove() {
        val availableMoves = mutableListOf<Pair<Int, Int>>()
        for (row in 0..2) {
            for (col in 0..2) {
                if (board[row][col].isEmpty()) {
                    availableMoves.add(row to col)
                }
            }
        }
        if (availableMoves.isNotEmpty()) {
            val (row, col) = availableMoves.random()
            makeMove(row, col)
        }
    }

    private fun makeMediumMove() {
        // Block the player's winning move, otherwise make a random move
        for (row in 0..2) {
            for (col in 0..2) {
                if (board[row][col].isEmpty()) {
                    board[row][col] = "X"
                    if (checkWin("X")) {
                        board[row][col] = "O"
                        return
                    }
                    board[row][col] = ""
                }
            }
        }
        makeRandomMove()
    }

    private fun makeBestMove() {
        // Implement Minimax or any optimal strategy for Hard mode
        var bestScore = Int.MIN_VALUE
        var bestMove: Pair<Int, Int>? = null

        for (row in 0..2) {
            for (col in 0..2) {
                if (board[row][col].isEmpty()) {
                    board[row][col] = "O"
                    val score = minimax(board, 0, false)
                    board[row][col] = ""
                    if (score > bestScore) {
                        bestScore = score
                        bestMove = row to col
                    }
                }
            }
        }

        bestMove?.let { (row, col) ->
            makeMove(row, col)
        }
    }

    private fun minimax(board: Array<Array<String>>, depth: Int, isMaximizing: Boolean): Int {
        val winner = checkWinner()
        if (winner == "O") return 10 - depth
        if (winner == "X") return depth - 10
        if (board.all { row -> row.all { it.isNotEmpty() } }) return 0

        if (isMaximizing) {
            var bestScore = Int.MIN_VALUE
            for (row in 0..2) {
                for (col in 0..2) {
                    if (board[row][col].isEmpty()) {
                        board[row][col] = "O"
                        val score = minimax(board, depth + 1, false)
                        board[row][col] = ""
                        bestScore = maxOf(score, bestScore)
                    }
                }
            }
            return bestScore
        } else {
            var bestScore = Int.MAX_VALUE
            for (row in 0..2) {
                for (col in 0..2) {
                    if (board[row][col].isEmpty()) {
                        board[row][col] = "X"
                        val score = minimax(board, depth + 1, true)
                        board[row][col] = ""
                        bestScore = minOf(score, bestScore)
                    }
                }
            }
            return bestScore
        }
    }

    private fun checkWinner(): String? {

        // Check rows, columns, and diagonals for a win
        for (player in arrayOf("X", "O")) {
            // Check rows
            for (row in 0..2) {
                if (board[row].all { it == player }) {
                    winningCells = listOf(Pair(row, 0), Pair(row, 1), Pair(row, 2))
                    return player
                }
            }

            // Check columns
            for (col in 0..2) {
                if (board.all { row -> row[col] == player }) {
                    winningCells = listOf(Pair(0, col), Pair(1, col), Pair(2, col))
                    return player
                }
            }

            // Check the first diagonal (top-left to bottom-right)
            if ((0..2).all { i -> board[i][i] == player }) {
                winningCells = listOf(Pair(0, 0), Pair(1, 1), Pair(2, 2))
                return player
            }

            // Check the second diagonal (top-right to bottom-left)
            if ((0..2).all { i -> board[i][2 - i] == player }) {
                winningCells = listOf(Pair(0, 2), Pair(1, 1), Pair(2, 0))
                return player
            }
        }

        return null
    }


    private fun checkWin(player: String): Boolean {
        return checkWinner() == player
    }

    fun resetGame() {
        board = Array(3) { Array(3) { "" } }
        currentPlayer = "X"
        gameOver = false
        winner = null
        winningCells = null
    }
}

enum class Difficulty {
    EASY, MEDIUM, HARD
}
