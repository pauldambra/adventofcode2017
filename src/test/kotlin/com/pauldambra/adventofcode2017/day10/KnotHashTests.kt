package com.pauldambra.adventofcode2017.day10

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it


data class TiedList(val finalList: List<Int>, val position: Int, val checkValue: Int)

object KnotHashTests : Spek({
    describe("the knot hash") {
        it("can process one length") {
            val circularList = mutableListOf(0, 1, 2, 3, 4)
            val lengths = listOf(3)
            val (finalList, position) = Knot().tie(circularList, lengths)
            expect(finalList).to.equal(listOf(2, 1, 0, 3, 4))
            expect(position).to.equal(3)
        }
        it("can process two lengths - where the second length wraps around the list") {
            val circularList = mutableListOf(0, 1, 2, 3, 4)
            val lengths = listOf(3, 4)
            val (finalList, position) = Knot().tie(circularList, lengths)
            expect(finalList).to.equal(listOf(4, 3, 0, 1, 2))
            expect(position).to.equal(3)
        }
        it("can process three lengths - with a length of 1 having no effect") {
            val circularList = mutableListOf(0, 1, 2, 3, 4)
            val lengths = listOf(3, 4, 1)
            val (finalList, position) = Knot().tie(circularList, lengths)
            expect(finalList).to.equal(listOf(4, 3, 0, 1, 2))
            expect(position).to.equal(1)
        }
        it("can process four lengths - where the final sublist will be the entire list") {
            val circularList = mutableListOf(0, 1, 2, 3, 4)
            val lengths = listOf(3, 4, 1, 5)
            val (finalList, position) = Knot().tie(circularList, lengths)
            expect(finalList).to.equal(listOf(3, 4, 2, 1, 0))
            expect(position).to.equal(4)
        }
        it("can calculate the expected check value after the example input") {
            val circularList = mutableListOf(0, 1, 2, 3, 4)
            val lengths = listOf(3, 4, 1, 5)
            val tiedList = Knot().tie(circularList, lengths)
            expect(tiedList.checkValue).to.equal(12)
        }
        it("can solve the check value for the puzzle input") {
            val circularList = 0..255
            val puzzleInput = listOf(130, 126, 1, 11, 140, 2, 255, 207, 18, 254, 246, 164, 29, 104, 0, 224)
            val tiedList = Knot().tie(circularList.toMutableList(), puzzleInput)
            println("check value for part one is ${tiedList.checkValue}")
        }

        it("does ranges as I think it does 1..64 has length 64") {
            expect((1..64).toList().size).to.equal(64)
        }
        it("can solve part two") {
            val circularList = 0..255
            val lengths = "130,126,1,11,140,2,255,207,18,254,246,164,29,104,0,224"
            val hashString = Knot().calculateDenseHash(circularList.toMutableList(), lengths)
            println("puzzle input converts to hexstring of $hashString")
        }
    }
})

object ASCIIFanglerTests : Spek({
    it("can convert arrays to their ascii characters (including commas!)") {
        val original = "1,2,3"
        expect(ASCIIFangler.toASCII(original)).to.equal(listOf(49, 44, 50, 44, 51))
    }

    it("can add a suffix of 17, 31, 73, 47, 23") {
        val original = "1,2,3"
        expect(ASCIIFangler.toASCII(original).withSuffix()).to.equal(listOf(49, 44, 50, 44, 51, 17, 31, 73, 47, 23))
    }
})

object HashFanglerTests : Spek({
    it("can bitwise xor a sparse hash of 16 numbers") {
        val sparseHash = listOf(65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22)
        expect(HashFangler.toDenseHash(sparseHash)).to.equal(64)
    }

    it("can hexstring 7 to 07") {
        expect(HashFangler.toHexString(listOf(7))).to.equal("07")
    }

    it("can hexstring 64, 7, 255 to 4007ff") {
        expect(HashFangler.toHexString(listOf(64, 7, 255))).to.equal("4007ff")
    }
})

object HashFangler {
    fun toDenseHash(sparseHash: List<Int>): Int = sparseHash.reduce { acc, i -> acc.xor(i) }

    fun toHexString(denseHash: List<Int>): String {
        return denseHash.joinToString("") { Integer.toHexString(it).padStart(2, '0') }
    }
}

fun List<Int>.withSuffix(): List<Int> = this + listOf(17, 31, 73, 47, 23)

object ASCIIFangler {
    fun toASCII(original: String): List<Int> {
        return original.toCharArray().map(Char::toInt)
    }
}

class Knot {

    fun calculateDenseHash(circularList: List<Int>, lengths: String): String {
        val tiedList = calculateSparseHash(circularList, ASCIIFangler.toASCII(lengths))
        val denseHash = tiedList.finalList.chunked(16).map {
            it.reduce { acc, x -> acc.xor(x) }
        }
        return HashFangler.toHexString(denseHash)
    }

    private fun calculateSparseHash(circularList: List<Int>, lengths: List<Int>): TiedList {
        val asciiLengths = lengths.withSuffix()
        return tie(circularList.toMutableList(), asciiLengths, 64)
    }

    fun tie(circularList: MutableList<Int>, lengths: List<Int>, numberOfIterations: Int = 1): TiedList {
        var latestList = circularList.toList()
        var skip = 0
        var nextIndex = 0

        for (i: Int in 1..numberOfIterations) {
            lengths.forEach {
                latestList = ListFangler.reverseSubList(latestList, it, nextIndex)
                nextIndex = incrementCircularIndex(nextIndex, it + skip, circularList.size - 1)
                skip += 1
            }
        }

        return TiedList(latestList, nextIndex, latestList[0] * latestList[1])
    }

    private fun incrementCircularIndex(current: Int, increase: Int, limit: Int): Int {
        var step = increase
        var finalIndex = current
        while (step > 0) {
            finalIndex++
            if (finalIndex > limit) finalIndex = 0
            step--
        }
        return finalIndex
    }

}

