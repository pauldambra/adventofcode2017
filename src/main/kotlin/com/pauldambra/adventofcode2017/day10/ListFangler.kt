package com.pauldambra.adventofcode2017.day10

object ListFangler {
    private fun reverseMapValues(indicesToChange: Map<Int, Int>): MutableMap<Int, Int> {
        val values = indicesToChange.values.reversed()
        val newIndexValues = mutableMapOf<Int, Int>()
        indicesToChange.keys.withIndex().forEach { newIndexValues.put(it.value, values[it.index]) }
        return newIndexValues
    }

    fun reverseSubList(originalList: List<Int>, lengthToReverse: Int, startIndex: Int = 0): List<Int> {
        val limit = originalList.size - 1
        val indicesToChange = (startIndex..(startIndex + lengthToReverse - 1)).map {
            if (it > limit) {
                it - originalList.size
            } else {
                it
            }
        }.associateBy({it}, {originalList[it]})
        val newIndexValues = reverseMapValues(indicesToChange)
        return originalList.withIndex().map {
            newIndexValues.getOrElse(it.index) { originalList[it.index] }
        }
    }
}