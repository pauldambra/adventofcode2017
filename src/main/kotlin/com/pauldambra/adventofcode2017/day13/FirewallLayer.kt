package com.pauldambra.adventofcode2017.day13

interface FirewallLayer {
    fun scannerPosition(atPicosecond: Int): Int
    fun layerRange(): Int
    val depth: Int
}