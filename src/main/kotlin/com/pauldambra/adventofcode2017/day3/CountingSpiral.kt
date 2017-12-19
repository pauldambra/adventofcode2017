package com.pauldambra.adventofcode2017.day3

import com.pauldambra.adventofcode2017.Direction
import kotlin.collections.HashMap

class CountingSpiral(maximumAddress: Int) : Spiral() {
    private val rows : HashMap<Int, HashMap<Int, Int>> = HashMap()
    private val dataAddressCoordinates : HashMap<Int, Pair<Int, Int>> = HashMap()

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

            val coordinateToLeft = coordinateToLeftFor(direction, x, y)
            if (!squareToLeftHasData(coordinateToLeft, rows)) {
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

    fun rowsEqual(other: HashMap<Int, HashMap<Int, Int>>) : Boolean {
        return compareRows(other, rows)
    }

    fun stepsToAccessPortFor(dataAtAddress: Int): Int {

        if (dataAtAddress == 1) return 0

        val paths = walkPath(dataAtAddress)

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