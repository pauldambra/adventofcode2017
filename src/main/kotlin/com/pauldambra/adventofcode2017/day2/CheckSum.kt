package com.pauldambra.adventofcode2017.day2

fun findFirstEvenlyDivisible(numbers: List<Int>): Int {
    for (i in 0 until numbers.size) {
        (0 until numbers.size)
                .filter { i != it }
                .map { numbers[i] / numbers[it].toDouble() }
                .filter { it - Math.floor(it) == 0.0 }
                .forEach { return it.toInt() }
    }
    return 0 // implicitly not found :(
}

class CheckSum {
    fun calculate(spreadsheet: String)
            = asRowsOfNumbers(spreadsheet)
                .map { r -> listOf(r.min(), r.max()) }
                .map { r -> r[0]?.let { r[1]?.minus(it) } } // wat kotlin
                .sumBy { r -> r?:0 }

    fun calculatePartTwo(spreadsheet: String)
            = asRowsOfNumbers(spreadsheet)
                .map { r -> r.sortedDescending() }
                .map { r -> findFirstEvenlyDivisible(r) }
                .sum()

    private fun asRowsOfNumbers(spreadsheet: String): List<List<Int>> {
        return spreadsheet.split("\n")
                .map { r -> r.split("\t") }
                .map { r -> r.map { n -> n.toInt() } }
    }
}