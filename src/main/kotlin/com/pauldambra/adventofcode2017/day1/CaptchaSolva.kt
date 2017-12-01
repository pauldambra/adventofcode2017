package com.pauldambra.adventofcode2017.day1

class CaptchaSolva {
    fun solutionFor(digits: String, offset: Int = 1)
            = digits.foldIndexed(0,  { i, total, c ->
                val nextIndex = wrapToNextIndex(i, offset, digits.length)
                val next = digits[nextIndex].toString().toInt()
                updateTotalIfMatching(c.toString().toInt(), next, total)
            })

    private fun updateTotalIfMatching(current: Int, next: Int, total: Int)
            = if (current == next) {
                total + current
            } else {
                total
            }

    fun solutionForStepTwo(digits: String): Any {
        val offset = digits.length / 2
        return solutionFor(digits, offset)
    }

}

fun wrapToNextIndex(start: Int, step: Int, listSize: Int): Int {
    return (start + step) % listSize
}