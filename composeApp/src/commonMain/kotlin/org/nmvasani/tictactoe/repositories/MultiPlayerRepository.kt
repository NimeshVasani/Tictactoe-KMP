package org.nmvasani.tictactoe.repositories


class MultiplayerRepository {
    var board: Array<Array<String>> = Array(3) { Array(3) { "" } }
    var currentPlayer: String = "X"
    var gameOver: Boolean = false
    var winner: String? = null
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

    private fun checkWin(player: String): Boolean {
        // Logic to check rows, columns, and diagonals for a win
        return checkWinner() == player
    }

    private fun checkWinner(): String? {
        // Logic to determine the winner, similar to the single-player version
        // Check rows, columns, and diagonals for a win
        val emptyCells = board.sumOf { row -> row.count { it.isEmpty() } }

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

    fun resetGame() {
        board = Array(3) { Array(3) { "" } }
        currentPlayer = "X"
        gameOver = false
        winner = null
        winningCells = null
    }
}
