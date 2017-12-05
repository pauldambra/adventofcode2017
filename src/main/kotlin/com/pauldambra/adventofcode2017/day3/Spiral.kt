package com.pauldambra.adventofcode2017.day3

import kotlin.collections.HashMap

class Spiral(maximumAddress: Int) {
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

        startAtOne(y, x)

        for (n in 2..maximumAddress) {
            val newCoordinate = step(direction, x, y, n)
            x = newCoordinate.first
            y = newCoordinate.second
            println("Stepped $direction to address $n at x:$x, y:$y")

            val coordinateToLeft = coordinateToLeftFor(direction, x, y)
            if (!squareToLeftHasData(coordinateToLeft)) {
                direction = changeDirection(direction)
            }

        }
    }

    private fun startAtOne(y: Int, x: Int) {
        rows[y]?.put(x, 1)
        dataAddressCoordinates.put(1, Pair(x, y))
    }

    private fun step(direction: Direction, currentX: Int, currentY: Int, n: Int): Pair<Int, Int> {
        var x = currentX
        var y = currentY

        when (direction) {
            Direction.RIGHT -> rows[y]!!.put(++x, n)
            Direction.LEFT -> rows[y]!!.put(--x, n)
            Direction.UP -> {
                y++
                if (!rows.containsKey(y)) {
                    rows[y] = HashMap()
                }
                rows[y]!!.put(x, n)
            }
            Direction.DOWN -> {
                y--
                if (!rows.containsKey(y)) {
                    rows[y] = HashMap()
                }
                rows[y]!!.put(x, n)

            }
        }
        dataAddressCoordinates.put(n, Pair(x, y))
        return Pair(x, y)
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

    fun stepsToAccessPortFor(dataAtAddress: Int): Int {

        if (dataAtAddress == 1) return 0

        val startingCoordinates = dataAddressCoordinates[dataAtAddress]
        println("starting for address $dataAtAddress at $startingCoordinates")

        val paths = walkPath(dataAtAddress)
        println("found candidate paths: $paths")

        return paths.map { it.size }.filter { it != 0 }.min()!!

    }

    private var shortestPathSoFar = Int.MAX_VALUE
    private val visitedSquares : HashMap<Int, Int> = HashMap()

    private fun walkPath(dataAtAddress: Int,  path: List<Int> = listOf()) : List<List<Int>> {
        if (path.size > shortestPathSoFar) {
            // prune this path
            return listOf()
        }

        if (dataAtAddress == 1) {
            if (path.size < shortestPathSoFar) {
                shortestPathSoFar = path.size
                println("new shortest path has $shortestPathSoFar steps")
            }
            return listOf(path)
        }

        //find current address
        val startingCoordinates = dataAddressCoordinates[dataAtAddress]

        //find neighbours
        val nextLowestAddresses = generateNeighbours(startingCoordinates!!)
                .filter { rows[it.second]?.get(it.first) != null }
                .map { rows[it.second]?.get(it.first)!! }
                .filter { it < dataAtAddress }


        val newPath = path + dataAtAddress

        return prunePaths(nextLowestAddresses, newPath)
                .map { walkPath(it, newPath) }
                .flatMap { it }
    }

    private fun prunePaths(nextLowestAddresses: List<Int>, newPath: List<Int>): MutableList<Int> {
        val toTest = mutableListOf<Int>()
        nextLowestAddresses.forEach {
            if (visitedSquares.containsKey(it)) {
                val currentBest = visitedSquares[it]
                if (currentBest!! > newPath.size) {
                    //this is a better route!
                    toTest.add(it)
                    visitedSquares[it] = newPath.size
                }
            } else {
                visitedSquares.put(it, newPath.size) //took n steps to get to this square
                toTest.add(it)
            }
        }
        return toTest
    }

    private fun generateNeighbours(start: Pair<Int, Int>): List<Pair<Int, Int>> {
        val x = start.first
        val y = start.second
        return listOf(
                Pair(x, y - 1),
                Pair(x, y + 1),
                Pair(x - 1, y),
                Pair(x + 1, y)
        )
    }

}