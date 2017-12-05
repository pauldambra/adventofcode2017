package com.pauldambra.adventofcode2017.day3

class SummingSpiral(targetAddress: Int) {
    var maximumDataAddress: Int = 0
    private val rows : HashMap<Int, HashMap<Int, Int>> = HashMap()

    private val dataAddressCoordinates : HashMap<Int, Pair<Int, Int>> = HashMap()
    enum class Direction {
        RIGHT, UP, LEFT, DOWN
    }

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
            if (!squareToLeftHasData(coordinateToLeft)) {
                direction = changeDirection(direction)
            }

        }
    }

    private fun startAtOne() {
        rows[0]?.put(0, 1)
        dataAddressCoordinates.put(1, Pair(0, 0))
        println("Started with address value 1 at x:0, y:0")
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
        println("Stepped $direction to add address value $sumOfNeighbours at x:${nextSquare.second}, y:${nextSquare.first}")

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

    private fun coordinateToLeftFor(direction: Direction, x: Int, y: Int)
            = when (direction) {
        Direction.RIGHT -> listOf(x, y + 1)
        Direction.UP -> listOf(x - 1, y)
        Direction.LEFT -> listOf(x, y - 1)
        Direction.DOWN -> listOf(x + 1, y)
    }

    private fun squareToLeftHasData(coordinateToLeft: List<Int>) : Boolean {
        val yIsPresent = rows.containsKey(coordinateToLeft[1])
        val xIsPresent = rows[coordinateToLeft[1]]?.contains(coordinateToLeft[0]) ?: false
        val dataAddress = rows[coordinateToLeft[1]]?.get(coordinateToLeft[0])
        val dataAddressIsInSquare = dataAddress != null && dataAddress > 0
        return xIsPresent && yIsPresent && dataAddressIsInSquare
    }

    fun rowsEqual(other: HashMap<Int, HashMap<Int, Int>>) : Boolean {
        if (other.size != rows.size) {
            println("this square has ${rows.size} rows but the other has ${other.size}")
            return false
        }

        for((y, cols) in rows) {
            for((x, dataAddress) in cols) {
                if (!other.containsKey(y)) {
                    println("other does not contain entry at y: $y")
                    return false
                }

                if (!other[y]!!.containsKey(x)) {
                    println("other does not contain square at x: $x, y: $y")
                    return false
                }

                val otherDataAddress = other[y]!![x]
                if (otherDataAddress != dataAddress) {
                    println("this square has $dataAddress at x: $x, y: $y but the other has $otherDataAddress")
                    return false
                }
            }
        }

        return true
    }

    private fun changeDirection(direction: Direction)
            = when(direction) {
        Direction.RIGHT -> Direction.UP
        Direction.UP -> Direction.LEFT
        Direction.LEFT -> Direction.DOWN
        Direction.DOWN -> Direction.RIGHT
    }

}