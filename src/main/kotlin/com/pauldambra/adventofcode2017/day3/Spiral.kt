package com.pauldambra.adventofcode2017.day3

import java.util.*

class Spiral(private val maximumAddress: Int) {
    private val rows : Deque<Deque<Int>> = LinkedList()
    private val sideCounter : SideCounter = SideCounter()

    enum class Direction {
        RIGHT, UP, LEFT, DOWN
    }

    init {
        var turnAfter = sideCounter.next()
        var steps = 0
        var direction = Direction.RIGHT

        rows.push(LinkedList())
        for (n in 1..maximumAddress) {
            takeStep(n, direction)
            steps += 1
            if (steps == turnAfter) {
                direction = changeDirection(direction)
                println("now heading $direction")
                turnAfter = sideCounter.next()
                println("next turn after $turnAfter steps")
                steps = 0
            }
        }
    }

    private fun takeStep(n: Int, direction: Direction) {
        when(direction) {
            Direction.RIGHT -> rows.last.addLast(n)
            Direction.UP -> {
                val widthOfLastRow = rows.last.size
                val row = rows.reversed().find { it.size < widthOfLastRow }
                if (row == null) {
                    rows.addFirst(LinkedList())
                    rows.first.addLast(n)
                } else {
                    row.addLast(n)
                }
            }
            Direction.LEFT -> {
                rows.first.addFirst(n)
            }
            Direction.DOWN -> {
                val widthOfFirstRow = rows.first.size
                val row = rows.find { it.size < widthOfFirstRow }
                if (row == null) {
                    rows.addLast(LinkedList())
                    rows.last.addFirst(n)
                } else {
                    row.addFirst(n)
                }
            }
        }
        println("Stepped $direction to address $n")
    }

    fun rowsEqual(other: Deque<Deque<Int>>) : Boolean {
        if (other.size != rows.size) return false

        rows.forEachIndexed { index, row ->
            val otherRow = other.elementAt(index)

            if (row.size != otherRow.size) return false
            row.forEachIndexed { charIndex, n ->
                if (otherRow.elementAt(charIndex) != n) return false
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

    override fun toString(): String {
        val rowWidth = rowWidthToUseForPrinting()

        return rows.joinToString("\n") {
            printRow(it).padStart(rowWidth, ' ')
        }
    }

    /** finds the printing length of the longest row... ignores the bottom row which is sometimes
        one longer on the right
        e.g.
          5 4 3
          6 1 2
          7 8 9 10

        here no lines need padding so it ignores that the bottom row has 4 numbers in it

        crikey!
    **/
    private fun rowWidthToUseForPrinting(): Int {
        val rowsToLookAt = if (rows.size <= 2) rows else rows.take(rows.size - 1)

        val longestRow = rowsToLookAt.fold(rows.first) { longestRow, row ->
            if (row.size > longestRow.size) row else longestRow
        }
        return printRow(longestRow).length
    }

    private fun printRow(row: Deque<Int>) = row.joinToString(" ") { memoryAddressToString(it) }

    private fun memoryAddressToString(it: Int) =
        it.toString().padStart(maximumAddress.toString().length, ' ')

}