package com.pauldambra.adventofcode2017.day3

import java.util.*

class SideCounter {

    private var changeEveryTwo = 0
    private var steps = 2

    private var fixedValues : Queue<Int> = LinkedList(mutableListOf(2, 1))

    //this is a generator that returns 2, then 1, then 2 twice, then 3 twice, 4 twice, 5 twice etc
    internal fun next(): Int {
        if(fixedValues.any()) {
            return fixedValues.poll()
        }

        return trackAroundEqualSides()
    }

    private fun SideCounter.trackAroundEqualSides(): Int {
        changeEveryTwo += 1
        if (changeEveryTwo == 3) {
            changeEveryTwo = 1
            steps += 1
        }
        return steps
    }

}