package com.pauldambra.adventofcode2017.day22

import com.pauldambra.adventofcode2017.Direction
import com.pauldambra.adventofcode2017.day19.Coordinate
import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

val puzzleInput =
        "##.###.....##..#.####....\n" +
                "##...#.#.#..##.#....#.#..\n" +
                "...#..#.###.#.###.##.####\n" +
                "..##..###....#.##.#..##.#\n" +
                "###....#####..###.#..#..#\n" +
                ".....#.#...#..##..#.##...\n" +
                ".##.#.###.#.#...##.#.##.#\n" +
                "......######.###......###\n" +
                "#.....##.#....#...#......\n" +
                "....#..###.#.#.####.##.#.\n" +
                ".#.#.##...###.######.####\n" +
                "####......#...#...#..#.#.\n" +
                "###.##.##..##....#..##.#.\n" +
                "..#.###.##..#...#######..\n" +
                "...####.#...###..#..###.#\n" +
                "..#.#.......#.####.#.....\n" +
                "..##..####.######..##.###\n" +
                "..#..#..##...#.####....#.\n" +
                ".#..#.####.#..##..#..##..\n" +
                "......#####...#.##.#....#\n" +
                "###..#...#.#...#.#..#.#.#\n" +
                ".#.###.#....##..######.##\n" +
                "##.######.....##.#.#.#..#\n" +
                "..#..##.##..#.#..###.##..\n" +
                "#.##.##..##.#.###.......#"

object GridComputingTests : Spek({
    it("can have a map with no activity") {
        val drawing =
                "..#\n" +
                        "#..\n" +
                        "..."
        val gridComputer = GridCluster.parse(drawing)
        expect(gridComputer.nodesInfectedByActivity()).to.equal(0)
    }

    it("can have infected nodes after one burst") {
        val drawing =
                "..#\n" +
                        "#..\n" +
                        "..."
        val gridComputer = GridCluster.parse(drawing)
        gridComputer.burst()
        expect(gridComputer.nodesInfectedByActivity()).to.equal(1)
    }

    it("infects 5 nodes after 7 bursts of activity") {
        val drawing =
                "..#\n" +
                        "#..\n" +
                        "..."
        val gridComputer = GridCluster.parse(drawing)
        repeat(7) { gridComputer.burst() }
        expect(gridComputer.nodesInfectedByActivity()).to.equal(5)
    }

    it("infects 41 nodes after 70 bursts of activity") {
        val drawing =
                "..#\n" +
                        "#..\n" +
                        "..."
        val gridComputer = GridCluster.parse(drawing)
        repeat(70) { gridComputer.burst() }
        expect(gridComputer.nodesInfectedByActivity()).to.equal(41)
    }

    it("After a total of 10000 bursts of activity, 5587 bursts will have caused an infection.") {
        val drawing =
                "..#\n" +
                        "#..\n" +
                        "..."
        val gridComputer = GridCluster.parse(drawing)
        repeat(10000) { gridComputer.burst() }
        expect(gridComputer.nodesInfectedByActivity()).to.equal(5587)
    }

    it("can count infected nodes after 10000 bursts of activity on the puzzle input grid") {
        val gridComputer = GridCluster.parse(puzzleInput)
        repeat(10000) { gridComputer.burst() }
        println("day 22 part 1: there were ${gridComputer.nodesInfectedByActivity()} nodes infected during 10000 bursts of activity")
    }
})

data class InfectedNode(val row: Int, val col: Int)

class GridCluster(val nodes: MutableMap<Int, MutableMap<Int, InfectedNode>>, var currentCoordinate: Coordinate) {

    companion object {
        fun parse(drawing: String): GridCluster {
            val nodes = mutableMapOf<Int, MutableMap<Int, InfectedNode>>()
            drawing.split("\n").withIndex().forEach { r ->
                r.value.toCharArray().withIndex().filter { it.value == '#' }.forEach { c ->
                    if (!nodes.containsKey(r.index)) {
                        nodes.put(r.index, mutableMapOf())
                    }
                    nodes[r.index]!!.put(c.index, InfectedNode(r.index, c.index))
                }
            }

            val halfTheGrid = nodes.keys.count() / 2
            val currentCoordinate = Coordinate(halfTheGrid, halfTheGrid)
            println("starting at $currentCoordinate")
            return GridCluster(nodes, currentCoordinate)
        }
    }

    private var infectedNodeCount = 0
    private var direction = Direction.UP

    fun nodesInfectedByActivity() = infectedNodeCount

    private fun isInfectedCoord() =
            nodes.containsKey(currentCoordinate.row)
                    && nodes[currentCoordinate.row]!!.containsKey(currentCoordinate.column)

    fun burst() {
        val isInfected = isInfectedCoord()
        println("$currentCoordinate is infected? $isInfected")
        println("facing $direction")
        direction = if (isInfected) direction.turnRightFrom(direction) else direction.turnLeftFrom(direction)
        println("turned to face $direction")
        if (isInfected) {
            clean(currentCoordinate)
        } else {
            infect(currentCoordinate)
        }

        currentCoordinate = currentCoordinate.nextWhenHeading(direction)
        println("at $currentCoordinate now")
    }

    private fun clean(c: Coordinate) {
        nodes[c.row]!!.remove(c.column)
    }

    private fun infect(c: Coordinate) {
        println("infecting $c")

        if (!nodes.containsKey(c.row)) {
            nodes.put(c.row, mutableMapOf())
        }
        nodes[c.row]!!.put(c.column, InfectedNode(c.row, c.column))
        infectedNodeCount++
    }
}
