package com.pauldambra.adventofcode2017

enum class Direction {
    RIGHT, UP, LEFT, DOWN;

    fun turnRightFrom(direction: Direction): Direction =
            when (direction) {
                Direction.RIGHT -> DOWN
                Direction.UP -> RIGHT
                Direction.LEFT -> UP
                Direction.DOWN -> LEFT
            }

    fun turnLeftFrom(direction: Direction): Direction =
            when (direction) {
                Direction.RIGHT -> UP
                Direction.UP -> LEFT
                Direction.LEFT -> DOWN
                Direction.DOWN -> RIGHT
            }
}