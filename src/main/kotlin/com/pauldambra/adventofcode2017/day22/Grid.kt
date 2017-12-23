package com.pauldambra.adventofcode2017.day22

import com.pauldambra.adventofcode2017.day19.Coordinate

class Grid(private val nodes: MutableMap<Int, MutableMap<Int, InfectedNode>>) {
    var infectedNodeCount = 0

    private fun ensureCleanNodeAt(c: Coordinate) {
        if (!nodes.containsKey(c.row)) {
            nodes.put(c.row, mutableMapOf())
        }
        nodes[c.row]!![c.column] = InfectedNode(c.row, c.column, NodeState.CLEANED)
    }

    fun safeGet(c: Coordinate): InfectedNode {
        if (!thereIsANodeAt(c)) {
            ensureCleanNodeAt(c)
        }
        return nodes[c.row]!![c.column]!!
    }

    fun thereIsANodeAt(c: Coordinate) =
            nodes.containsKey(c.row)
                    && nodes[c.row]!!.containsKey(c.column)

    fun weaken(node: InfectedNode) {
        val newState = node.state!!.weaken()
        if (newState == NodeState.INFECTED) infectedNodeCount++
        nodes[node.row]!![node.col]!!.state = newState
    }

    fun clean(c: Coordinate) {
        nodes[c.row]!!.remove(c.column)
    }

    fun infect(c: Coordinate) {
        ensureCleanNodeAt(c)
        infectedNodeCount++
    }
}