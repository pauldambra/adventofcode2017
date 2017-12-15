package com.pauldambra.adventofcode2017.day13

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

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

object FirewallTests : Spek({
    val exampleInput = "0: 3\n" +
            "1: 2\n" +
            "4: 4\n" +
            "6: 4"

    it("your packet moves through the layers one picosecond at a time") {
        val firewall = Firewall(CalculatingLayer.parse(
                "0: 4\n" +
                        "3: 3"))
        val positions = (0..3).map { firewall.tick() }
        expect(positions).to.equal(listOf(0, 1, 2, 3))
    }
    it("can catch the packet when it moves onto a scanner - entering the firewall at 0 guarantees the packet is caught") {
        val firewall = Firewall(CalculatingLayer.parse(
                "0: 4\n" +
                        "3: 3"))
        firewall.tick()
        val captures = firewall.caughtPackageAt
        expect(captures).to.equal(listOf(0))
    }
    it("can catch the packet as expected for the example input") {
        val firewall = Firewall(CalculatingLayer.parse(exampleInput))

        while (!firewall.packetHasCrossed()) {
            firewall.tick()
        }

        val captures = firewall.caughtPackageAt
        expect(captures).to.equal(listOf(0, 6))
    }
    it("can calculate the severity of caught packages") {
        val firewall = Firewall(CalculatingLayer.parse(exampleInput))

        while (!firewall.packetHasCrossed()) {
            firewall.tick()
        }

        expect(firewall.capturesSeverity()).to.equal(24)
    }
    it("can calculate severity for the puzzle input") {
        val firewall = Firewall(CalculatingLayer.parse(puzzleInput))
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
                val firewall = Firewall(CalculatingLayer.parse(exampleInput), delay)

                while (!firewall.packetHasCrossed()) {
                    firewall.tick()
                }
            } while (firewall.caughtPackageAt.any())

            expect(delay).to.equal(10)
        }
        it("can calculate the delay to not be caught for the puzzle input") {

            var delay = 0
            do {
                delay++
                val firewall = Firewall(CalculatingLayer.parse(puzzleInput), delay)

                while (!firewall.packetHasCrossed() && firewall.caughtPackageAt.isEmpty()) {
                    firewall.tick(true)
                }
            } while (firewall.caughtPackageAt.any())

            println("day 13 part2: smallest delay to not be caught is $delay")
        }
    }
})


