package com.example.fifteenpuzzleaplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var resetButton: Button
    private val tiles = Array(4) { IntArray(4) }
    private var emptyTileRow = 3
    private var emptyTileCol = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)
        resetButton = findViewById(R.id.resetButton)

        resetButton.setOnClickListener { resetGame() }

        initializeGame()
        setupTiles()
    }

    private fun initializeGame() {
        var number = 1
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                if (number < 16) {
                    tiles[i][j] = number++
                } else {
                    tiles[i][j] = 0
                }
            }
        }
        shuffleTiles()
    }

    private fun shuffleTiles() {
        for (i in 0 until 100) {
            val direction = Random.nextInt(4)
            when (direction) {
                0 -> moveTile(emptyTileRow - 1, emptyTileCol) // вверх
                1 -> moveTile(emptyTileRow + 1, emptyTileCol) // вниз
                2 -> moveTile(emptyTileRow, emptyTileCol - 1) // влево
                3 -> moveTile(emptyTileRow, emptyTileCol + 1) // вправо
            }
        }
    }

    private fun moveTile(row: Int, col: Int) {
        if (row in 0..3 && col in 0..3) {
            if (isAdjacent(row, col)) {
                tiles[emptyTileRow][emptyTileCol] = tiles[row][col]
                tiles[row][col] = 0

                emptyTileRow = row
                emptyTileCol = col
            }
        }
    }

    private fun setupTiles() {
        gridLayout.removeAllViews()
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                val button = Button(this)
                button.text = if (tiles[i][j] == 0) "" else tiles[i][j].toString()
                button.setOnClickListener { onTileClick(i, j) }
                gridLayout.addView(button, GridLayout.LayoutParams().apply {
                    width = 0
                    height = 0
                    columnSpec = GridLayout.spec(j, 1f)
                    rowSpec = GridLayout.spec(i, 1f)
                })
            }
        }
    }

    private fun onTileClick(row: Int, col: Int) {
        if (isAdjacent(row, col)) {
            tiles[emptyTileRow][emptyTileCol] = tiles[row][col]
            tiles[row][col] = 0
            emptyTileRow = row
            emptyTileCol = col
            setupTiles()
        }
    }

    private fun isAdjacent(row: Int, col: Int): Boolean {
        return (row == emptyTileRow && Math.abs(col - emptyTileCol) == 1) ||
                (col == emptyTileCol && Math.abs(row - emptyTileRow) == 1)
    }

    private fun resetGame() {
        initializeGame()
        setupTiles()
    }
}
