package com.pauldambra.adventofcode2017.day15

//shamelessly adapted from https://www.programiz.com/kotlin-programming/examples/binary-decimal-convert
internal fun Long.toBinary() : List<Long> {
    var n = this
    var binaryNumber = mutableListOf<Long>()
    var remainder: Long

    while (n != 0L) {
        remainder = n % 2
        n /= 2
        binaryNumber.add(remainder)
    }

    var binaryResult = binaryNumber.reversed().toList()
    if (binaryResult.size < 32) {
        binaryResult = (1..(32-binaryResult.size)).map { 0L } + binaryResult
    }

    try {
        check(binaryResult.distinct().containsAll(listOf(0L,1L)))
    } catch (ise: IllegalStateException) {
        throw ProbablyNotCorrectlyConvertedToBinary(this, binaryResult)
    }

    return binaryResult
}

class ProbablyNotCorrectlyConvertedToBinary(original: Long, binaryResult: List<Long>)
    : Throwable("problem converting $original to $binaryResult")