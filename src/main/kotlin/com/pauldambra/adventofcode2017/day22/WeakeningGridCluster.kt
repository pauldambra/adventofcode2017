package com.pauldambra.adventofcode2017.day22

import com.pauldambra.adventofcode2017.Direction
import com.pauldambra.adventofcode2017.day19.Coordinate

class WeakeningGridCluster(nodes: MutableMap<Int, MutableMap<Int, InfectedNode>>, private var currentCoordinate: Coordinate) {

    val grid = Grid(nodes)

    companion object {
        fun parse(drawing: String): WeakeningGridCluster {
            val nodes = mutableMapOf<Int, MutableMap<Int, InfectedNode>>()
            mapFromDrawing(drawing, nodes)

            val currentCoordinate = midpoint(nodes)
            return WeakeningGridCluster(nodes, currentCoordinate)
        }
    }

    private var direction = Direction.UP

    fun burst() {
        val node = grid.safeGet(currentCoordinate)

        changeDirection(node)

        grid.weaken(node)

        currentCoordinate = currentCoordinate.nextWhenHeading(direction)
    }

    private fun changeDirection(node: InfectedNode?) {
        if (node == null) return

        val state = node.state?: NodeState.CLEANED
        direction = when (state) {
            NodeState.CLEANED -> direction.turnLeftFrom(direction)
            NodeState.WEAKENED -> direction
            NodeState.INFECTED -> direction.turnRightFrom(direction)
            NodeState.FLAGGED -> direction.reverseFrom(direction)
        }
    }
}