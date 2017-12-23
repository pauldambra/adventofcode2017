package com.pauldambra.adventofcode2017.day22

import com.pauldambra.adventofcode2017.Direction
import com.pauldambra.adventofcode2017.day19.Coordinate

class GridCluster(nodes: MutableMap<Int, MutableMap<Int, InfectedNode>>, private var currentCoordinate: Coordinate) {

    val grid = Grid(nodes)

    companion object {
        fun parse(drawing: String): GridCluster {
            val nodes = mutableMapOf<Int, MutableMap<Int, InfectedNode>>()
            mapFromDrawing(drawing, nodes)

            val currentCoordinate = midpoint(nodes)
            return GridCluster(nodes, currentCoordinate)
        }
    }

    private var direction = Direction.UP

    fun burst() {
        val isInfected = grid.thereIsANodeAt(currentCoordinate)
        changeDirection(isInfected)
        if (isInfected) {
            grid.clean(currentCoordinate)
        } else {
            grid.infect(currentCoordinate)
        }

        currentCoordinate = currentCoordinate.nextWhenHeading(direction)
    }

    private fun changeDirection(isInfected: Boolean) {
        direction = if (isInfected) direction.turnRightFrom(direction) else direction.turnLeftFrom(direction)
    }
}