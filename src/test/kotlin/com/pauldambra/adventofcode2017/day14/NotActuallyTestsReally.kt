package com.pauldambra.adventofcode2017.day14

import com.pauldambra.adventofcode2017.day10.Knot
import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it


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
    it("can count used squares in the example grid") {
        val expectedUsedSquares = 8108
        val grid = grid("flqrgnkx")
        val actual = grid.fold(0) { acc, row -> acc + row.count { it == '1' }}
        expect(actual).to.equal(expectedUsedSquares)
    }
    it("can count used squares in the puzzle input grid") {
        val puzzleInput = "xlqgujun"
        val grid = grid(puzzleInput)
        val actual = grid.fold(0) { acc, row -> acc + row.count { it == '1' }}
        println("day 14 part 1: there were $actual used squares in grid")
    }
})

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