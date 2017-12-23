package com.pauldambra.adventofcode2017.day22

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

val exampleInput =
        "..#\n" +
                "#..\n" +
                "..."

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
        val gridComputer = GridCluster.parse(exampleInput)
        expect(gridComputer.grid.infectedNodeCount).to.equal(0)
    }

    it("can have infected nodes after one burst") {
        val gridComputer = GridCluster.parse(exampleInput)
        gridComputer.burst()
        expect(gridComputer.grid.infectedNodeCount).to.equal(1)
    }

    it("infects 5 nodes after 7 bursts of activity") {
        val gridComputer = GridCluster.parse(exampleInput)
        repeat(7) { gridComputer.burst() }
        expect(gridComputer.grid.infectedNodeCount).to.equal(5)
    }

    it("infects 41 nodes after 70 bursts of activity") {
        val gridComputer = GridCluster.parse(exampleInput)
        repeat(70) { gridComputer.burst() }
        expect(gridComputer.grid.infectedNodeCount).to.equal(41)
    }

    it("After a total of 10000 bursts of activity, 5587 bursts will have caused an infection.") {
        val gridComputer = GridCluster.parse(exampleInput)
        repeat(10000) { gridComputer.burst() }
        expect(gridComputer.grid.infectedNodeCount).to.equal(5587)
    }

    it("can count infected nodes after 10000 bursts of activity on the puzzle input grid") {
        val gridComputer = GridCluster.parse(puzzleInput)
        repeat(10000) { gridComputer.burst() }
        println("day 22 part 1: there were ${gridComputer.grid.infectedNodeCount} nodes infected during 10000 bursts of activity")
    }

    it("can count infected states for part two's example input after 100 bursts") {
        val gridComputer = WeakeningGridCluster.parse(exampleInput)
        repeat(100) { gridComputer.burst() }
        expect(gridComputer.grid.infectedNodeCount).to.equal(26)
    }

    it("can count infected states after 10000000 bursts for the example input") {
        val gridComputer = WeakeningGridCluster.parse(exampleInput)
        repeat(10000000) { gridComputer.burst() }
        expect(gridComputer.grid.infectedNodeCount).to.equal(2511944)
    }

    it("can count infected states after 10000000 bursts for the puzzle input") {
        val gridComputer = WeakeningGridCluster.parse(puzzleInput)
        repeat(10000000) { gridComputer.burst() }
        println("day 22 part 2: nodes infected by activity after 10000000 bursts is ${gridComputer.grid.infectedNodeCount}")
    }
})

