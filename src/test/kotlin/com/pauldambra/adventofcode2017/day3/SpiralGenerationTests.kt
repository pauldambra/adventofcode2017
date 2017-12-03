package com.pauldambra.adventofcode2017.day3

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import java.util.*

//            expect(piral(1)}o.equal(
//                    "5   4   3\n" +
//                            "6   1   2\n" +
//                            "7   8   9  10")

private fun rowWithContents(c: List<Int>): Deque<Int> {
    val rowOne: Deque<Int> = LinkedList()
    c.forEach {rowOne.addLast(it)}
    return rowOne
}

object SpiralGenerationTests : Spek({
    describe("generating the spiral memory") {
        it("can generate 1") {
            val expected : Deque<Deque<Int>> = LinkedList()
            expected.addFirst(rowWithContents(listOf(1)))

            expect(Spiral(1).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 2") {
            val expected : Deque<Deque<Int>> = LinkedList()
            expected.addFirst(rowWithContents(listOf(1, 2)))

            expect(Spiral(2).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 3") {
            val expected : Deque<Deque<Int>> = LinkedList()
            expected.addFirst(rowWithContents(listOf(1, 2)))
            expected.addFirst(rowWithContents(listOf(3)))

            expect(Spiral(3).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 4") {
            val expected : Deque<Deque<Int>> = LinkedList()
            expected.addFirst(rowWithContents(listOf(1, 2)))
            expected.addFirst(rowWithContents(listOf(4, 3)))

            expect(Spiral(4).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 5") {
            val expected : Deque<Deque<Int>> = LinkedList()
            expected.addFirst(rowWithContents(listOf(1, 2)))
            expected.addFirst(rowWithContents(listOf(5, 4, 3)))

            expect(Spiral(5).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 6") {
            val expected : Deque<Deque<Int>> = LinkedList()
            expected.addFirst(rowWithContents(listOf(6, 1, 2)))
            expected.addFirst(rowWithContents(listOf(5, 4, 3)))

            expect(Spiral(6).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 7") {
            val expected : Deque<Deque<Int>> = LinkedList()
            expected.addLast(rowWithContents(listOf(5, 4, 3)))
            expected.addLast(rowWithContents(listOf(6, 1, 2)))
            expected.addLast(rowWithContents(listOf(7)))

            expect(Spiral(7).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 8") {
            val expected : Deque<Deque<Int>> = LinkedList()
            expected.addLast(rowWithContents(listOf(5, 4, 3)))
            expected.addLast(rowWithContents(listOf(6, 1, 2)))
            expected.addLast(rowWithContents(listOf(7, 8)))

            expect(Spiral(8).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 9") {
            val expected : Deque<Deque<Int>> = LinkedList()
            expected.addLast(rowWithContents(listOf(5, 4, 3)))
            expected.addLast(rowWithContents(listOf(6, 1, 2)))
            expected.addLast(rowWithContents(listOf(7, 8, 9)))

            expect(Spiral(9).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 10") {
            val expected : Deque<Deque<Int>> = LinkedList()
            expected.addLast(rowWithContents(listOf(5, 4, 3)))
            expected.addLast(rowWithContents(listOf(6, 1, 2)))
            expected.addLast(rowWithContents(listOf(7, 8, 9, 10)))

            expect(Spiral(10).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 13") {
            val expected : Deque<Deque<Int>> = LinkedList()
            expected.addLast(rowWithContents(listOf(13)))
            expected.addLast(rowWithContents(listOf(5, 4, 3, 12)))
            expected.addLast(rowWithContents(listOf(6, 1, 2, 11)))
            expected.addLast(rowWithContents(listOf(7, 8, 9, 10)))

            expect(Spiral(13).rowsEqual(expected)).to.be.`true`
        }


        it("can generate 17") {
            val expected : Deque<Deque<Int>> = LinkedList()
            expected.addLast(rowWithContents(listOf(17, 16, 15, 14, 13)))
            expected.addLast(rowWithContents(listOf(5, 4, 3, 12)))
            expected.addLast(rowWithContents(listOf(6, 1, 2, 11)))
            expected.addLast(rowWithContents(listOf(7, 8, 9, 10)))

            expect(Spiral(17).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 21") {
            val expected : Deque<Deque<Int>> = LinkedList()
            expected.addLast(rowWithContents(listOf(17, 16, 15, 14, 13)))
            expected.addLast(rowWithContents(listOf(18, 5, 4, 3, 12)))
            expected.addLast(rowWithContents(listOf(19, 6, 1, 2, 11)))
            expected.addLast(rowWithContents(listOf(20, 7, 8, 9, 10)))
            expected.addLast(rowWithContents(listOf(21)))

            expect(Spiral(21).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 26") {
            val expected : Deque<Deque<Int>> = LinkedList()
            expected.addLast(rowWithContents(listOf(17, 16, 15, 14, 13)))
            expected.addLast(rowWithContents(listOf(18, 5, 4, 3, 12)))
            expected.addLast(rowWithContents(listOf(19, 6, 1, 2, 11)))
            expected.addLast(rowWithContents(listOf(20, 7, 8, 9, 10)))
            expected.addLast(rowWithContents(listOf(21, 22, 23, 24, 25, 26)))

            expect(Spiral(26).rowsEqual(expected)).to.be.`true`
        }

        it("can generate 27") {
            val expected : Deque<Deque<Int>> = LinkedList()
            expected.addLast(rowWithContents(listOf(17, 16, 15, 14, 13)))
            expected.addLast(rowWithContents(listOf(18, 5, 4, 3, 12)))
            expected.addLast(rowWithContents(listOf(19, 6, 1, 2, 11)))
            expected.addLast(rowWithContents(listOf(20, 7, 8, 9, 10, 27)))
            expected.addLast(rowWithContents(listOf(21, 22, 23, 24, 25, 26)))

            expect(Spiral(27).rowsEqual(expected)).to.be.`true`
        }


    }
})

