package com.pauldambra.adventofcode2017.day11

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

object DayElevenTests : Spek({
    it("can solve part one") {
        val directions = this::class.java
                .getResource("/day11.txt")
                .readText()
        val startingHex = Hex(0, 0)
        val finalHex = startingHex.walk(directions)
        val distanceHome = finalHex.distanceTo(startingHex)
        println("day 11, part 1 - distance home from final hex: $finalHex is $distanceHome")
    }
    it("can solve part two") {
        val directions = this::class.java
                .getResource("/day11.txt")
                .readText()
        val startingHex = Hex(0, 0)
        val pathWalked = startingHex.recordPath(directions)
        val maxDistanceReached = pathWalked.map { it.distanceTo(startingHex) }.max()
        println("day 11, part 2 - furthest distance reached on path is $maxDistanceReached")
    }
})