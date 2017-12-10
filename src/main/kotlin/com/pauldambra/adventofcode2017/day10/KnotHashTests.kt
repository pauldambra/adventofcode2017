package com.pauldambra.adventofcode2017.day10

data class TiedList(val finalList: List<Int>, val position: Int, val checkValue: Int)
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