package com.pauldambra.adventofcode2017.day19

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

object MapTests : Spek({
    it("can instantiate a map with a single straight line path") {
        val map = Map.parse(
                "  |  \n" +
                        "  A  ")
        map.walkPath()
        expect(map.lettersSeen()).to.equal(listOf("A"))
        expect(map.stepsTaken()).to.equal(2)
    }

    it("can instantiate a map with a single straight line path through two letters") {
        val map = Map.parse(
                "  |  \n" +
                        "  A  \n" +
                        "  |  \n" +
                        "  B  \n")
        map.walkPath()
        expect(map.lettersSeen()).to.equal(listOf("A", "B"))
        expect(map.stepsTaken()).to.equal(4)
    }

    it("can turn a corner") {
        val map = Map.parse(
                "  |  \n" +
                        "  A  \n" +
                        "  +-B")
        map.walkPath()
        expect(map.lettersSeen()).to.equal(listOf("A", "B"))
        expect(map.stepsTaken()).to.equal(5)
    }

    it("can turn two corners") {
        val map = Map.parse(
                "  |   C\n" +
                        "  A   |\n" +
                        "  +-B-+")
        map.walkPath()
        expect(map.lettersSeen()).to.equal(listOf("A", "B", "C"))
        expect(map.stepsTaken()).to.equal(9)
    }

    it("can turn and cross its previous path (going over)") {
        val map = Map.parse(
                "  |    \n" +
                        "D-----+\n" +
                        "  |   |\n" +
                        "  A   |\n" +
                        "  +-B-+")
        map.walkPath()
        expect(map.lettersSeen()).to.equal(listOf("A", "B", "D"))
        expect(map.stepsTaken()).to.equal(18)
    }

    it("can turn and cross its previous path (going under)") {
        val map = Map.parse(
                "  |    \n" +
                        "D-|---+\n" +
                        "  |   |\n" +
                        "  A   |\n" +
                        "  +-B-+")
        map.walkPath()
        expect(map.lettersSeen()).to.equal(listOf("A", "B", "D"))
        expect(map.stepsTaken()).to.equal(18)
    }

    it("can detect an end inside the map") {
        val map = Map.parse(
                "  |    \n" +
                        "E | D-+\n" +
                        "  |   |\n" +
                        "  A   |\n" +
                        "  +-B-+")
        map.walkPath()
        expect(map.lettersSeen()).to.equal(listOf("A", "B", "D"))
        expect(map.stepsTaken()).to.equal(14)
    }

    it("can solve the example input") {
        val map = Map.parse("     |          \n" +
                "     |  +--+    \n" +
                "     A  |  C    \n" +
                " F---|----E|--+ \n" +
                "     |  |  |  D \n" +
                "     +B-+  +--+ \n")
        map.walkPath()
        expect(map.lettersSeen()).to.equal(listOf("A", "B", "C", "D", "E", "F"))
        expect(map.stepsTaken()).to.equal(38)
    }

    it("can solve the puzzle input") {
        val drawing = this::class.java
                .getResource("/day19.txt")
                .readText()
        val map = Map.parse(drawing)
        map.walkPath()
        println("day 19 part 1: path visits: ${map.lettersSeen()}")
        println("day 19 part 2: path takes ${map.stepsTaken()} steps")
    }
})

