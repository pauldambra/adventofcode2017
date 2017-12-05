package com.pauldambra.adventofcode2017.day3

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import kotlin.collections.HashMap

object SpiralGenerationTests : Spek({
    describe("generating the spiral memory") {
        it("can generate 1") {
            val expected : HashMap<Int, HashMap<Int, Int>> = HashMap()
            expected.put(0, hashMapOf(Pair(0, 1)))

            expect(CountingSpiral(1).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 2") {
            val expected : HashMap<Int, HashMap<Int, Int>> = HashMap()
            expected.put(0, hashMapOf(Pair(0, 1), Pair(1, 2)))

            expect(CountingSpiral(2).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 3") {
            val expected : HashMap<Int, HashMap<Int, Int>> = HashMap()
            expected.put(1, hashMapOf(Pair(1, 3)))
            expected.put(0, hashMapOf(Pair(0, 1), Pair(1, 2)))

            expect(CountingSpiral(3).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 4") {
            val expected : HashMap<Int, HashMap<Int, Int>> = HashMap()
            expected.put(1, hashMapOf(Pair(0, 4), Pair(1, 3)))
            expected.put(0, hashMapOf(Pair(0, 1), Pair(1, 2)))

            expect(CountingSpiral(4).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 5") {
            val expected : HashMap<Int, HashMap<Int, Int>> = HashMap()
            expected.put(1, hashMapOf(Pair(-1, 5), Pair(0, 4), Pair(1, 3)))
            expected.put(0, hashMapOf(Pair(0, 1), Pair(1, 2)))

            expect(CountingSpiral(5).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 6") {
            val expected : HashMap<Int, HashMap<Int, Int>> = HashMap()
            expected.put(1, hashMapOf(Pair(-1, 5), Pair(0, 4), Pair(1, 3)))
            expected.put(0, hashMapOf(Pair(-1, 6), Pair(0, 1), Pair(1, 2)))


            expect(CountingSpiral(6).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 7") {
            val expected : HashMap<Int, HashMap<Int, Int>> = HashMap()
            expected.put(1, hashMapOf(Pair(-1, 5), Pair(0, 4), Pair(1, 3)))
            expected.put(0, hashMapOf(Pair(-1, 6), Pair(0, 1), Pair(1, 2)))
            expected.put(-1, hashMapOf(Pair(-1, 7)))

            expect(CountingSpiral(7).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 8") {
            val expected : HashMap<Int, HashMap<Int, Int>> = HashMap()
            expected.put(1, hashMapOf(Pair(-1, 5), Pair(0, 4), Pair(1, 3)))
            expected.put(0, hashMapOf(Pair(-1, 6), Pair(0, 1), Pair(1, 2)))
            expected.put(-1, hashMapOf(Pair(-1, 7), Pair(0, 8)))

            expect(CountingSpiral(8).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 9") {
            val expected : HashMap<Int, HashMap<Int, Int>> = HashMap()
            expected.put(1, hashMapOf(Pair(-1, 5), Pair(0, 4), Pair(1, 3)))
            expected.put(0, hashMapOf(Pair(-1, 6), Pair(0, 1), Pair(1, 2)))
            expected.put(-1, hashMapOf(Pair(-1, 7), Pair(0, 8), Pair(1, 9)))

            expect(CountingSpiral(9).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 10") {
            val expected : HashMap<Int, HashMap<Int, Int>> = HashMap()
            expected.put(1, hashMapOf(Pair(-1, 5), Pair(0, 4), Pair(1, 3)))
            expected.put(0, hashMapOf(Pair(-1, 6), Pair(0, 1), Pair(1, 2)))
            expected.put(-1, hashMapOf(Pair(-1, 7), Pair(0, 8), Pair(1, 9), Pair(2, 10)))

            expect(CountingSpiral(10).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 13") {
            val expected : HashMap<Int, HashMap<Int, Int>> = HashMap()
            expected.put(2, hashMapOf(Pair(2, 13)))
            expected.put(1, hashMapOf(Pair(-1, 5), Pair(0, 4), Pair(1, 3), Pair(2, 12)))
            expected.put(0, hashMapOf(Pair(-1, 6), Pair(0, 1), Pair(1, 2), Pair(2, 11)))
            expected.put(-1, hashMapOf(Pair(-1, 7), Pair(0, 8), Pair(1, 9), Pair(2, 10)))

            expect(CountingSpiral(13).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 17") {
            val expected : HashMap<Int, HashMap<Int, Int>> = HashMap()
            expected.put(2, hashMapOf(Pair(-2, 17), Pair(-1, 16), Pair(0, 15), Pair(1, 14), Pair(2, 13)))
            expected.put(1, hashMapOf(Pair(-1, 5), Pair(0, 4), Pair(1, 3), Pair(2, 12)))
            expected.put(0, hashMapOf(Pair(-1, 6), Pair(0, 1), Pair(1, 2), Pair(2, 11)))
            expected.put(-1, hashMapOf(Pair(-1, 7), Pair(0, 8), Pair(1, 9), Pair(2, 10)))

            expect(CountingSpiral(17).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 21") {
            val expected : HashMap<Int, HashMap<Int, Int>> = HashMap()
            expected.put(2, hashMapOf(Pair(-2, 17), Pair(-1, 16), Pair(0, 15), Pair(1, 14), Pair(2, 13)))
            expected.put(1, hashMapOf(Pair(-2, 18), Pair(-1, 5), Pair(0, 4), Pair(1, 3), Pair(2, 12)))
            expected.put(0, hashMapOf(Pair(-2, 19), Pair(-1, 6), Pair(0, 1), Pair(1, 2), Pair(2, 11)))
            expected.put(-1, hashMapOf(Pair(-2, 20), Pair(-1, 7), Pair(0, 8), Pair(1, 9), Pair(2, 10)))
            expected.put(-2, hashMapOf(Pair(-2, 21)))

            expect(CountingSpiral(21).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 26") {
            val expected : HashMap<Int, HashMap<Int, Int>> = HashMap()
            expected.put(2, hashMapOf(Pair(-2, 17), Pair(-1, 16), Pair(0, 15), Pair(1, 14), Pair(2, 13)))
            expected.put(1, hashMapOf(Pair(-2, 18), Pair(-1, 5), Pair(0, 4), Pair(1, 3), Pair(2, 12)))
            expected.put(0, hashMapOf(Pair(-2, 19), Pair(-1, 6), Pair(0, 1), Pair(1, 2), Pair(2, 11)))
            expected.put(-1, hashMapOf(Pair(-2, 20), Pair(-1, 7), Pair(0, 8), Pair(1, 9), Pair(2, 10)))
            expected.put(-2, hashMapOf(Pair(-2, 21), Pair(-1, 22), Pair(0, 23), Pair(1, 24), Pair(2, 25), Pair(3, 26)))

            expect(CountingSpiral(26).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 27") {
            val expected : HashMap<Int, HashMap<Int, Int>> = HashMap()
            expected.put(2, hashMapOf(Pair(-2, 17), Pair(-1, 16), Pair(0, 15), Pair(1, 14), Pair(2, 13)))
            expected.put(1, hashMapOf(Pair(-2, 18), Pair(-1, 5), Pair(0, 4), Pair(1, 3), Pair(2, 12)))
            expected.put(0, hashMapOf(Pair(-2, 19), Pair(-1, 6), Pair(0, 1), Pair(1, 2), Pair(2, 11)))
            expected.put(-1, hashMapOf(Pair(-2, 20), Pair(-1, 7), Pair(0, 8), Pair(1, 9), Pair(2, 10), Pair(3, 27)))
            expected.put(-2, hashMapOf(Pair(-2, 21), Pair(-1, 22), Pair(0, 23), Pair(1, 24), Pair(2, 25), Pair(3, 26)))

            expect(CountingSpiral(27).rowsEqual(expected)).to.be.`true`
        }


    }
})

