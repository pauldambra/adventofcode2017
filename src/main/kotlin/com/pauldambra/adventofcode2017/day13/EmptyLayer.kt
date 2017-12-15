package com.pauldambra.adventofcode2017.day13

data class EmptyLayer(override val depth: Int) : FirewallLayer {
    private val noScanner = -1
    private val noRange = -1

    override fun layerRange(): Int {
        return noRange
    }

    override fun scannerPosition(atPicosecond: Int): Int {
        return noScanner
    }
}