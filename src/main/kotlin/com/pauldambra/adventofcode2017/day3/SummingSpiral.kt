package com.pauldambra.adventofcode2017.day3

import com.pauldambra.adventofcode2017.Direction

class SummingSpiral(targetAddress: Int) : Spiral() {
    var maximumDataAddress: Int = 0
    private val rows : HashMap<Int, HashMap<Int, Int>> = HashMap()

    private val dataAddressCoordinates : HashMap<Int, Pair<Int, Int>> = HashMap()

    init {
        var direction = Direction.RIGHT


        var x = 0
        var y = 0
        rows[y] = HashMap()

        startAtOne()

        while (maximumDataAddress <= targetAddress) {
            val newCoordinate = step(direction, x, y)
            x = newCoordinate.first
            y = newCoordinate.second

            val coordinateToLeft = coordinateToLeftFor(direction, x, y)
            if (!squareToLeftHasData(coordinateToLeft, rows)) {
                direction = changeDirection(direction)
            }

        }
    }

    private fun startAtOne() {
        rows[0]?.put(0, 1)
        dataAddressCoordinates.put(1, Pair(0, 0))
    }

    private fun step(direction: Direction, currentX: Int, currentY: Int): Pair<Int, Int> {

        val nextSquare = when (direction) {
            Direction.RIGHT -> Pair(currentX + 1, currentY)
            Direction.LEFT -> Pair(currentX - 1, currentY)
            Direction.UP -> {
                val y = currentY + 1
                if (!rows.containsKey(y)) {
                    rows[y] = HashMap()
                }
                Pair(currentX, y)
            }
            Direction.DOWN -> {
                val y = currentY - 1
                if (!rows.containsKey(y)) {
                    rows[y] = HashMap()
                }
                Pair(currentX, y)
            }
        }
        val sumOfNeighbours = generateNeighbours(nextSquare)
                .map { rows[it.second]?.get(it.first) }
                .sumBy { it?:0 }

        dataAddressCoordinates.put(sumOfNeighbours, nextSquare)
        rows[nextSquare.second]!!.put(nextSquare.first, sumOfNeighbours)

        maximumDataAddress = sumOfNeighbours
        return nextSquare
    }

    private fun generateNeighbours(square: Pair<Int, Int>): List<Pair<Int, Int>> {
        return listOf(
                Pair(square.first, square.second -1),
                Pair(square.first + 1, square.second -1),
                Pair(square.first + 1, square.second),
                Pair(square.first + 1, square.second + 1),
                Pair(square.first, square.second + 1),
                Pair(square.first - 1, square.second + 1),
                Pair(square.first - 1, square.second),
                Pair(square.first - 1, square.second - 1)
        )
    }

    fun rowsEqual(other: HashMap<Int, HashMap<Int, Int>>) : Boolean {
        return compareRows(other, rows)
    }

}