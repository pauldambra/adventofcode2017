package com.pauldambra.adventofcode2017.day11

import kotlin.math.absoluteValue

data class Hex(private val column: Int, private val row: Int) {
    fun move(direction: Direction): Hex {
        return when (direction) {
            Direction.NORTH -> Hex(column, row - 1)
            Direction.NORTH_EAST -> Hex(column + 1, row - 1)
            Direction.SOUTH_EAST -> Hex(column + 1, row)
            Direction.SOUTH -> Hex(column, row + 1)
            Direction.SOUTH_WEST -> Hex(column - 1, row + 1)
            Direction.NORTH_WEST -> Hex(column - 1, row)
        }
    }

    fun walk(path: String): Hex {
        var current = this.copy()
        path.split(",").map { Direction.fromString(it) }.forEach {
            current = current.move(it)
        }
        return current
    }

    fun recordPath(path: String): List<Hex> {
        var current = this.copy()
        val visited = mutableListOf<Hex>()
        path.split(",").map { Direction.fromString(it) }.forEach {
            current = current.move(it)
            visited.add(current.copy())
        }
        return visited.toList()
    }

    fun distanceTo(other: Hex): Int {
        return ((column - other.column).absoluteValue
                + (column + row - other.column - other.row).absoluteValue
                + (row - other.row).absoluteValue) / 2
    }
}

enum class Direction {
    NORTH,
    NORTH_EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    NORTH_WEST;

    companion object {
        fun fromString(s: String): Direction {
            return when (s) {
                "n" -> NORTH
                "ne" -> NORTH_EAST
                "se" -> SOUTH_EAST
                "s" -> SOUTH
                "sw" -> SOUTH_WEST
                "nw" -> NORTH_WEST
                else -> {
                    throw Exception(String.format("There aren't any other directions... certainly not %s", s))
                }
            }
        }
    }
}