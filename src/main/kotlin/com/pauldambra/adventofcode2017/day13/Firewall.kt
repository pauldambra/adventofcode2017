package com.pauldambra.adventofcode2017.day13

class Firewall(private val layers: List<FirewallLayer>, private val delay: Int = 0) {
    var caughtPackageAt: MutableList<Int> = mutableListOf()

    var currentPacketPosition = -1
    var picosecond = 0
    var step = 1
    fun tick(failfast: Boolean = false): Int {
        if (picosecond == 0) {
            picosecond += delay
        }

        currentPacketPosition += 1

        if (currentPacketPosition >= layers.size) {
            return currentPacketPosition
        }

        val currentLayer = layers[currentPacketPosition]
        if (currentLayer.scannerPosition(picosecond) == 0) {
            caughtPackageAt.add(currentPacketPosition)
            if (failfast) {
                return currentPacketPosition
            }
        }

        picosecond += step
        return currentPacketPosition
    }

    fun packetHasCrossed(): Boolean = currentPacketPosition > layers.size
    fun capturesSeverity(): Int {
        val captureLayers = caughtPackageAt.toList()
        val captureRanges = captureLayers.map { layers[it].layerRange() }
        return captureLayers.zip(captureRanges).map { it.first * it.second }.sum()
    }

}