package com.pauldambra.adventofcode2017.day17

fun <E> Collection<E>.stepForward(startIndex: Int, steps: Int): Int = (startIndex + steps) % this.size
fun <E> MutableList<E>.insertAfter(index: Int, value: E) {
    this.add(index + 1, value)
}