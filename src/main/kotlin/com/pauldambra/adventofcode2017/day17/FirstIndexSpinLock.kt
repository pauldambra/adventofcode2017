package com.pauldambra.adventofcode2017.day17

class FirstIndexSpinLock(private val steps: Int) {
    private var currentPosition = 0
    private var max = 0
    private var trackedNumber = 0

//    private var start = System.currentTimeMillis()
    fun step() {

        val next = (currentPosition + steps) % (max + 1)

        max += 1

        if (next == 0) {
            // this only tracks numbers that would have been inserted at position one in the buffer
            trackedNumber = max
        }

        currentPosition = next + 1


//        if (currentPosition%1_00_000==0) {
//            val elapsed = System.currentTimeMillis() - start
//            val secondsForFiftyMillion = ((elapsed.toDouble() / 1_00_000) * 50_000_000) / 1000
//            println("stepped to $currentPosition after inserting $max - $elapsed - predict $secondsForFiftyMillion for fifty million steps")
//            start = System.currentTimeMillis()
//        }
    }

    fun valueAfterZero(): Int {
        return trackedNumber;
    }
}