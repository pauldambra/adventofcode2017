package com.pauldambra.adventofcode2017.day14

import com.pauldambra.adventofcode2017.day10.Knot
import com.pauldambra.adventofcode2017.day12.Pipes
import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

fun gridNumberer(x: Int, y: Int): Int {
    check(x <= 127)
    return x + (128 * y)
}

object GridNumberingTests : Spek({
    it("if y is 0 and x is 0 then square number is 0") {
        expect(gridNumberer(0, 0)).to.equal(0)
    }
    it("all the way to if y is 0 and x is 127 it's numbered 127") {
        expect(gridNumberer(127, 0)).to.equal(127)
    }
    it("if y is 1 and x is 0 then it's 128") {
        expect(gridNumberer(0, 1)).to.equal(128)
    }
    it("if y is 1 and x is 1 then it's 129") {
        expect(gridNumberer(1, 1)).to.equal(129)
    }
    it("if y is 1 and x is 2 then it's 130") {
        expect(gridNumberer(2, 1)).to.equal(130)
    }
    it("if y is 1 and x is 127 then it's 256") {
        expect(gridNumberer(127, 1)).to.equal(255)
    }
    it("if y is 2 and x is 0 then its 255") {
        expect(gridNumberer(0, 2)).to.equal(256)
    }
})


object DayFourteen : Spek({
    it("can make an expected binary knot hash") {
        val knotHash = Knot().calculateDenseHash((0..255).toMutableList(), "flqrgnkx")
        println(knotHash)
        println(hexToBinary(knotHash))
    }
    it("can convert hash to binary") {
        expect(hexToBinary("a0c2017")).to.equal("1010000011000010000000010111")
    }
    it("can make the example grid") {
        val grid = grid("flqrgnkx")
        expect(grid[0].take(8)).to.equal("11010100")
        expect(grid[1].take(8)).to.equal("01010101")
        expect(grid[2].take(8)).to.equal("00001010")
        expect(grid[3].take(8)).to.equal("10101101")
        expect(grid[4].take(8)).to.equal("01101000")
        expect(grid[5].take(8)).to.equal("11001001")
        expect(grid[6].take(8)).to.equal("01000100")
        expect(grid[7].take(8)).to.equal("11010110")
    }
//    it("can count used squares in the example grid") {
//        val expectedUsedSquares = 8108
//        val grid = grid("flqrgnkx")
//        val actual = grid.fold(0) { acc, row -> acc + row.count { it == '1' }}
//        expect(actual).to.equal(expectedUsedSquares)
//    }
//    it("can count used squares in the puzzle input grid") {
//        val puzzleInput = "xlqgujun"
//        val grid = grid(puzzleInput)
//        val actual = grid.fold(0) { acc, row -> acc + row.count { it == '1' }}
//        println("day 14 part 1: there were $actual used squares in grid")
//    }
//    it("can build the graph of connections") {
//
//        val map: MutableMap<Int, MutableList<Int>> = mutableMapOf() // maps numbered square to connections to numbered square
//
//        val grid = grid("flqrgnkx")
//
//        val regions = knotGridRegionsCounter(grid, map)
//        println("there are $regions groups in the grid")
//        expect(regions).to.equal(1242)
//
//    }
    it("can build the graph of connections for the puzzle input") {
        val puzzleInput = "xlqgujun"
        val map: MutableMap<Int, MutableList<Int>> = mutableMapOf() // maps numbered square to connections to numbered square

        val grid = grid(puzzleInput)

        val regions = knotGridRegionsCounter(grid, map)
        println("Day 14 Part 2: there are $regions groups in the grid")

    }
})

private fun knotGridRegionsCounter(grid: List<String>, map: MutableMap<Int, MutableList<Int>>): Int {
    grid.withIndex().forEach { y ->
        // y coord
        y.value.toCharArray().withIndex().forEach { x ->
            // x coord
            if (x.value == '1') {
                val squareNumber = gridNumberer(x.index, y.index)
                ensureMapContains(map, squareNumber)
                val neighbours = neighboursOf(x.index, y.index)
                neighbours.filter { grid[it.second][it.first] == '1' }.forEach {
                    val neighbourNumber = gridNumberer(it.first, it.second)
                    map[squareNumber]!!.add(neighbourNumber)
                    ensureMapContains(map, neighbourNumber)
                    map[neighbourNumber]!!.add(squareNumber)
                }
            }
        }
    }

    val connections: MutableMap<Int, List<Int>> = mutableMapOf()
    map.forEach { k, v -> connections.put(k, v.distinct()) }
    val regions = Pipes(connections).countAllGroups()
    return regions
}

private fun ensureMapContains(map: MutableMap<Int, MutableList<Int>>, squareNumber: Int) {
    if (!map.containsKey(squareNumber)) {
        map.put(squareNumber, mutableListOf())
    }
}

fun neighboursOf(x: Int, y: Int): List<Pair<Int, Int>> {
    val possibles = listOf(
        Pair(x-1, y),
        Pair(x+1, y),
        Pair(x, y-1),
        Pair(x, y+1)
    )
    return possibles.filter { it.first >= 0 && it.second >= 0 }.filter { it.first <=127 && it.second <=127 }
}


fun grid(seed: String) : List<String> {
    return (0..127).map { Knot().calculateDenseHash((0..255).toMutableList(), "$seed-$it") }.map { hexToBinary(it) }
}

fun hexToBinary(hex: String): String {
    return hex.toCharArray().map(::hexDigitToBinary).joinToString("")
}

fun hexDigitToBinary(hex: Char): String {
    return when (hex.toUpperCase()) {
        '0' -> "0000"
        '1' -> "0001"
        '2' -> "0010"
        '3' -> "0011"
        '4' -> "0100"
        '5' -> "0101"
        '6' -> "0110"
        '7' -> "0111"
        '8' -> "1000"
        '9' -> "1001"
        'A' -> "1010"
        'B' -> "1011"
        'C' -> "1100"
        'D' -> "1101"
        'E' -> "1110"
        'F' -> "1111"
        else -> throw Exception("wat?! $hex")
    }
}