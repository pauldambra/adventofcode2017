package com.pauldambra.adventofcode2017.day17

class SpinLock(private val steps: Int) {

    private val buffer = mutableListOf(0)
    private var currentPosition = 0
    private var max = 0

    fun current(): List<Int> {
        return buffer.toList()
    }

//    private var start = System.currentTimeMillis()
    fun step() {
        val next = buffer.stepForward(currentPosition, steps)
        buffer.insertAfter(next, ++max)
        currentPosition = next + 1


//        if (currentPosition%1_00_000==0) {
//            val elapsed = System.currentTimeMillis() - start
//            val secondsForFiftyMillion = ((elapsed.toDouble() / 1_00_000) * 50_000_000) / 1000
//            println("stepped to $currentPosition after inserting $max - $elapsed - predict $secondsForFiftyMillion for fifty million steps")
//            start = System.currentTimeMillis()
//        }
    }

    fun valueAfter(needle: Int): Int {
        val i = buffer.indexOf(needle)
        val targetIndex = if (i + 1 == buffer.size) 0 else i + 1
        return buffer.elementAt(targetIndex)
    }

}