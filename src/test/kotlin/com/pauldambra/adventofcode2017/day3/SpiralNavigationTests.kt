package com.pauldambra.adventofcode2017.day3

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it


object SpiralNavigationTests : Spek({
    describe("can navigate around a spiral") {
        it("Data from square 1 is carried 0 steps, since it's at the access port.") {
            val spiral = CountingSpiral(23)
            val dataAtAddress = 1
            expect(spiral.stepsToAccessPortFor(dataAtAddress)).to.equal(0)
        }

        /**
        17  16  15  14  13
        18   5   4   3  12
        19   6   1   2  11
        20   7   8   9  10
        21  22  23---> ...

         so path options are
            12 -> 3 -> 3 -> 1
            12 -> 11 -> 2 -> 1

        **/
        it("Data from square 12 is carried 3 steps, such as: down, left, left.") {
            val spiral = CountingSpiral(23)
            val dataAtAddress = 12
            expect(spiral.stepsToAccessPortFor(dataAtAddress)).to.equal(3)
        }

        it("can navigate back from 3120") {
            val spiral = CountingSpiral(3120)
            val dataAtAddress = 3120
            val steps = spiral.stepsToAccessPortFor(dataAtAddress)
            println("the quickest path back from 3120 has $steps steps")
        }

        it("can navigate back from 312051") {
            val spiral = CountingSpiral(312051)
            val dataAtAddress = 312051
            val steps = spiral.stepsToAccessPortFor(dataAtAddress)
            println("the quickest path back from 312051 has $steps steps")
        }
    }
})