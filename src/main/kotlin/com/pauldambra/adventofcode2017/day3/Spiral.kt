package com.pauldambra.adventofcode2017.day3


open class Spiral {
    protected fun coordinateToLeftFor(direction: Direction, x: Int, y: Int)
            = when (direction) {
        Direction.RIGHT -> listOf(x, y + 1)
        Direction.UP -> listOf(x - 1, y)
        Direction.LEFT -> listOf(x, y - 1)
        Direction.DOWN -> listOf(x + 1, y)
    }

    protected fun squareToLeftHasData(coordinateToLeft: List<Int>, rows: HashMap<Int, HashMap<Int, Int>>) : Boolean {
        val yIsPresent = rows.containsKey(coordinateToLeft[1])
        val xIsPresent = rows[coordinateToLeft[1]]?.contains(coordinateToLeft[0]) ?: false
        val dataAddress = rows[coordinateToLeft[1]]?.get(coordinateToLeft[0])
        val dataAddressIsInSquare = dataAddress != null && dataAddress > 0
        return xIsPresent && yIsPresent && dataAddressIsInSquare
    }

    protected fun changeDirection(direction: Direction)
            = when(direction) {
                Direction.RIGHT -> Direction.UP
                Direction.UP -> Direction.LEFT
                Direction.LEFT -> Direction.DOWN
                Direction.DOWN -> Direction.RIGHT
            }

    protected fun compareRows(other: HashMap<Int, HashMap<Int, Int>>, ownRows: HashMap<Int, HashMap<Int, Int>>): Boolean {
        if (other.size != ownRows.size) {
            println("this square has ${ownRows.size} rows but the other has ${other.size}")
            return false
        }

        for ((y, cols) in ownRows) {
            for ((x, dataAddress) in cols) {
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
}