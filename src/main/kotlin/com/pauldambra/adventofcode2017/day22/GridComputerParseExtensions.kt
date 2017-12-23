package com.pauldambra.adventofcode2017.day22

import com.pauldambra.adventofcode2017.day19.Coordinate

fun mapFromDrawing(drawing: String, nodes: MutableMap<Int, MutableMap<Int, InfectedNode>>) {
    drawing.split("\n").withIndex().forEach { r ->
        r.value.toCharArray().withIndex().filter { it.value == '#' }.forEach { c ->
            if (!nodes.containsKey(r.index)) {
                nodes.put(r.index, mutableMapOf())
            }
            nodes[r.index]!!.put(c.index, InfectedNode(r.index, c.index, NodeState.INFECTED))
        }
    }
}

fun midpoint(nodes: MutableMap<Int, MutableMap<Int, InfectedNode>>): Coordinate {
    val halfTheGrid = nodes.keys.count() / 2
    return Coordinate(halfTheGrid, halfTheGrid)
}