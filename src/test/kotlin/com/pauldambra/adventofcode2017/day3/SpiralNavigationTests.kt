package com.pauldambra.adventofcode2017.day3

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it


object SpiralNavigationTests : Spek({
    describe("can navigate around a spiral") {
        it("Data from square 1 is carried 0 steps, since it's at the access port.") {
            val spiral = Spiral(23)
            val dataAtAddress = 1
            expect(spiral.stepsToAccessPortFor(dataAtAddress)).to.equal(0)
        }

        it("Data from square 12 is carried 3 steps, such as: down, left, left.") {
            val spiral = Spiral(23)
            val dataAtAddress = 12
            expect(spiral.stepsToAccessPortFor(dataAtAddress)).to.equal(3)
        }
    }
})