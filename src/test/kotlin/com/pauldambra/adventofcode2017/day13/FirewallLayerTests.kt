package com.pauldambra.adventofcode2017.day13

import com.sun.org.apache.xpath.internal.operations.Bool
import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.util.stream.Collector
import java.util.stream.Collectors

val puzzleInput = "0: 5\n" +
        "1: 2\n" +
        "2: 3\n" +
        "4: 4\n" +
        "6: 6\n" +
        "8: 4\n" +
        "10: 6\n" +
        "12: 10\n" +
        "14: 6\n" +
        "16: 8\n" +
        "18: 6\n" +
        "20: 9\n" +
        "22: 8\n" +
        "24: 8\n" +
        "26: 8\n" +
        "28: 12\n" +
        "30: 12\n" +
        "32: 8\n" +
        "34: 8\n" +
        "36: 12\n" +
        "38: 14\n" +
        "40: 12\n" +
        "42: 10\n" +
        "44: 14\n" +
        "46: 12\n" +
        "48: 12\n" +
        "50: 24\n" +
        "52: 14\n" +
        "54: 12\n" +
        "56: 12\n" +
        "58: 14\n" +
        "60: 12\n" +
        "62: 14\n" +
        "64: 12\n" +
        "66: 14\n" +
        "68: 14\n" +
        "72: 14\n" +
        "74: 14\n" +
        "80: 14\n" +
        "82: 14\n" +
        "86: 14\n" +
        "90: 18\n" +
        "92: 17"

object FirewallLayerTests : Spek({

    it("can create a firewall layer") {
        expect(Layer("0: 3")).to.equal(Layer(0, 3))
    }
    it("can create two consecutive layers from a description of the firewall") {
        val layers: List<FirewallLayer> = Layer.parse(
                "0: 3\n" +
                        "1: 2")
        expect(layers).to.equal(listOf(Layer(0, 3), Layer(1, 2)))
    }
    it("fills in empty layers when description has gaps in the layers") {
        val layers: List<FirewallLayer> = Layer.parse(
                "0: 4\n" +
                        "3: 3")
        expect(layers).to.equal(listOf(Layer(0, 4), EmptyLayer(1), EmptyLayer(2), Layer(3, 3)))
    }

    describe("the scanner") {
        it("starts at the top") {
            val layer = Layer(0, 3)
            expect(layer.scannerPosition()).to.equal(0)
        }
        it("moves to the bottom of the layer's range") {
            val range = 5
            val layer = Layer(0, range)
            val picoseconds = 0..4
            picoseconds.forEach {
                expect(layer.scannerPosition()).to.equal(it)
                layer.tick()
            }
        }
        it("moves back up from the bottom of the range") {
            val expectedScannerPositions = listOf(0, 1, 2, 3, 4, 3, 2, 1, 0)
            val picoseconds = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
            val layer = Layer(0, 5)
            val actualScannerPositions = picoseconds.map {
                val pos = layer.scannerPosition()
                layer.tick()
                pos
            }
            expect(actualScannerPositions).to.equal(expectedScannerPositions)
        }
    }

    describe("the firewall") {
        val exampleInput = "0: 3\n" +
                "1: 2\n" +
                "4: 4\n" +
                "6: 4"

        it("your packet moves through the layers one picosecond at a time") {
            val firewall = Firewall(Layer.parse(
                    "0: 4\n" +
                            "3: 3"))
            val positions = (0..3).map { firewall.tick() }
            expect(positions).to.equal(listOf(0, 1, 2, 3))
        }
        it("can catch the packet when it moves onto a scanner - entering the firewall at 0 guarantees the packet is caught") {
            val firewall = Firewall(Layer.parse(
                    "0: 4\n" +
                            "3: 3"))
            firewall.tick()
            val captures = firewall.caughtPackageAt
            expect(captures).to.equal(listOf(0))
        }
        it("can catch the packet as expected for the example input") {
            val firewall = Firewall(Layer.parse(exampleInput))

            while (!firewall.packetHasCrossed()) {
                firewall.tick()
            }

            val captures = firewall.caughtPackageAt
            expect(captures).to.equal(listOf(0, 6))
        }
        it("can calculate the severity of caught packages") {
            val firewall = Firewall(Layer.parse(exampleInput))

            while (!firewall.packetHasCrossed()) {
                firewall.tick()
            }

            expect(firewall.capturesSeverity()).to.equal(24)
        }
        it("can calculate severity for the puzzle input") {
            val firewall = Firewall(Layer.parse(puzzleInput))
            while (!firewall.packetHasCrossed()) {
                firewall.tick()
            }
            println("day 13: firewall capture severity is ${firewall.capturesSeverity()}")
        }

        on("part two") {
            it("can calculate the delay to not be caught for the example input") {

                var delay = -1
                do {
                    delay++
                    val firewall = Firewall(Layer.parse(exampleInput), delay)

                    while (!firewall.packetHasCrossed()) {
                        firewall.tick()
                    }
                } while (firewall.caughtPackageAt.any())

                expect(delay).to.equal(10)
            }
            it("can calculate the delay to not be caught for the puzzle input") {

                var delay = 3_000_000 // erm this is a guess

                val delaysWithNoCaughtPackets: MutableList<Pair<Int, MutableList<Int>>> = mutableListOf()
                do {
                    val step = 500
                    val finds = (delay..delay + step)
                            .toList()
                            .parallelStream()
                            .map {
                                val caughtPacketsAt = checkForCaughtPackages(it)
                                Pair(it, caughtPacketsAt)
                            }
                            .filter { !it.second.any() }
                            .collect(Collectors.toList())
                    delaysWithNoCaughtPackets.addAll(finds)
                    delay += step
                } while (delaysWithNoCaughtPackets.isEmpty())

                val smallestDElayThatLetsPacketThrough = delaysWithNoCaughtPackets!!.map { it.first }.min()

                println("smallest delay to not be caught is $smallestDElayThatLetsPacketThrough")
            }
        }
    }
})

private fun checkForCaughtPackages(delay: Int): MutableList<Int> {
    val firewall = Firewall(Layer.parse(puzzleInput), delay)

    while (!firewall.packetHasCrossed()) {
        val failfast = true
        firewall.tick(failfast)
    }

    if (delay % 10 == 0) {
        println("after delaying $delay picoseconds. package was caught at ${firewall.caughtPackageAt}")
    }
    return firewall.caughtPackageAt
}

class Firewall(private val layers: List<FirewallLayer>, private val delay: Int = 0) {
    var caughtPackageAt: MutableList<Int> = mutableListOf()

    var currentPacketPosition = -1
    var picosecond = 0
    fun tick(failfast: Boolean = false): Int {
        // don't enter the firewall until delay has passed
        if (picosecond >= delay) {
            currentPacketPosition += 1

            if (currentPacketPosition >= layers.size) {
                return currentPacketPosition
            }

            val currentLayer = layers[currentPacketPosition]
            if (currentLayer.scannerPosition() == 0) {
                caughtPackageAt.add(currentPacketPosition)
                if(failfast) {
                    return currentPacketPosition
                }
            }
        }

        layers.forEach { it.tick() }

        picosecond++
        return currentPacketPosition
    }

    fun packetHasCrossed(): Boolean = currentPacketPosition > layers.size
    fun capturesSeverity(): Int {
        val captureLayers = caughtPackageAt.toList()
        val captureRanges = captureLayers.map { layers[it].layerRange() }
        return captureLayers.zip(captureRanges).map { it.first * it.second }.sum()
    }

}

interface FirewallLayer {
    fun tick()
    fun scannerPosition(): Int
    fun layerRange(): Int
}

data class EmptyLayer(val depth: Int) : FirewallLayer {
    private val noScanner = -1
    private val noRange = -1

    override fun layerRange(): Int {
        return noRange
    }

    override fun scannerPosition(): Int {
        return noScanner
    }

    override fun tick() {
        // does nothing on an empty layer?
    }
}

data class Layer(val depth: Int, private val range: Int) : FirewallLayer {
    override fun layerRange(): Int = range

    companion object {

        fun parse(description: String): List<FirewallLayer> {
            val providedLayers = description
                    .split("\n")
                    .map { Layer(it) }
                    .associateBy({ it.depth }, { it })

            val depthsRange = providedLayers.keys.min()!!.rangeTo(providedLayers.keys.max()!!)

            return depthsRange.fold(emptyList()) { layers, depth ->
                layers + providedLayers.getOrDefault(depth, EmptyLayer(depth))
            }
        }
    }

    constructor(description: String) : this(
            description.split(": ")[0].toInt(),
            description.split(": ")[1].toInt())

    enum class ScannerDirection {

        UP, DOWN
    }

    var scannerCurrentPosition = 0

    var scannerDirection = ScannerDirection.DOWN
    override fun tick() {
        when (scannerDirection) {
            Layer.ScannerDirection.UP -> {
                scannerCurrentPosition--
            }
            Layer.ScannerDirection.DOWN -> {
                scannerCurrentPosition++
            }
        }

        if (scannerCurrentPosition >= range - 1) {
            scannerDirection = ScannerDirection.UP
        } else if (scannerCurrentPosition == 0 && scannerDirection == ScannerDirection.UP) {
            scannerDirection = ScannerDirection.DOWN
        }
    }

    override fun scannerPosition(): Int = scannerCurrentPosition

}
