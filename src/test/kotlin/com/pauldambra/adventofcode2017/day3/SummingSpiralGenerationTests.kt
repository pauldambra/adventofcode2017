package com.pauldambra.adventofcode2017.day3

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it


object SummingSpiralGenerationTests : Spek({
    describe("the summing spiral") {
        it("can generate a square with target address 1 adding three squares") {
            val expected : HashMap<Int, HashMap<Int, Int>> = HashMap()
            expected.put(1, hashMapOf(Pair(1, 2)))
            expected.put(0, hashMapOf(Pair(0, 1), Pair(1, 1)))

            val summingSpiral = SummingSpiral(1)
            expect(summingSpiral.rowsEqual(expected)).to.be.`true`
            expect(summingSpiral.maximumDataAddress).to.equal(2)
        }
        it("Target 11 ends at 23") {
            val expected : HashMap<Int, HashMap<Int, Int>> = HashMap()
            expected.put(1, hashMapOf(Pair(-1, 5), Pair(0, 4), Pair(1, 2)))
            expected.put(0, hashMapOf(Pair(-1, 10), Pair(0, 1), Pair(1, 1)))
            expected.put(-1, hashMapOf(Pair(-1, 11), Pair(0, 23)))

            val summingSpiral = SummingSpiral(11)
            expect(summingSpiral.rowsEqual(expected)).to.be.`true`
            expect(summingSpiral.maximumDataAddress).to.equal(23)
        }
        it("can find the address reached after 312051") {
            val summingSpiral = SummingSpiral(312051)
            println("for target 312051 spiral writes a maximum of ${summingSpiral.maximumDataAddress}")
        }
    }
})