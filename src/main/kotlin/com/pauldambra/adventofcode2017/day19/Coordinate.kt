package com.pauldambra.adventofcode2017.day19

import com.pauldambra.adventofcode2017.Direction

data class Coordinate(val row: Int, val column: Int) {
    fun nextWhenHeading(d: Direction) =
            when (d) {
                Direction.UP -> Coordinate(row - 1, column)
                Direction.RIGHT -> Coordinate(row, column + 1)
                Direction.LEFT -> Coordinate(row, column - 1)
                Direction.DOWN -> Coordinate(row + 1, column)
            }

    fun neighbours() = mapOf(
            Pair(Direction.UP, Coordinate(row - 1, column)),
            Pair(Direction.RIGHT, Coordinate(row, column + 1)),
            Pair(Direction.DOWN, Coordinate(row + 1, column)),
            Pair(Direction.LEFT, Coordinate(row, column - 1))
    )
}

