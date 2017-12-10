package com.pauldambra.adventofcode2017.day10

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it


object KnotHashTests : Spek({
    describe("the knot hash") {
        it("can process one length") {
            val circularList = mutableListOf(0, 1, 2, 3, 4)
            val lengths = listOf(3)
            val (finalList, position) = Knot().tie(circularList, lengths)
            expect(finalList).to.equal(listOf(2, 1, 0, 3, 4))
            expect(position).to.equal(3)
        }
        it("can process two lengths - where the second length wraps around the list") {
            val circularList = mutableListOf(0, 1, 2, 3, 4)
            val lengths = listOf(3, 4)
            val (finalList, position) = Knot().tie(circularList, lengths)
            expect(finalList).to.equal(listOf(4, 3, 0, 1, 2))
            expect(position).to.equal(3)
        }
        it("can process three lengths - with a length of 1 having no effect") {
            val circularList = mutableListOf(0, 1, 2, 3, 4)
            val lengths = listOf(3, 4, 1)
            val (finalList, position) = Knot().tie(circularList, lengths)
            expect(finalList).to.equal(listOf(4, 3, 0, 1, 2))
            expect(position).to.equal(1)
        }
        it("can process four lengths - where the final sublist will be the entire list") {
            val circularList = mutableListOf(0, 1, 2, 3, 4)
            val lengths = listOf(3, 4, 1, 5)
            val (finalList, position) = Knot().tie(circularList, lengths)
            expect(finalList).to.equal(listOf(3, 4, 2, 1, 0))
            expect(position).to.equal(4)
        }
        it("can calculate the expected check value after the example input") {
            val circularList = mutableListOf(0, 1, 2, 3, 4)
            val lengths = listOf(3, 4, 1, 5)
            val tiedList = Knot().tie(circularList, lengths)
            expect(tiedList.checkValue).to.equal(12)
        }
        it("can solve the check value for the puzzle input") {
            val circularList = 0..255
            val puzzleInput = listOf(130, 126, 1, 11, 140, 2, 255, 207, 18, 254, 246, 164, 29, 104, 0, 224)
            val tiedList = Knot().tie(circularList.toMutableList(), puzzleInput)
            println("check value for part one is ${tiedList.checkValue}")
        }

        it("can solve part two") {
            val circularList = 0..255
            val lengths = "130,126,1,11,140,2,255,207,18,254,246,164,29,104,0,224"
            val hashString = Knot().calculateDenseHash(circularList.toMutableList(), lengths)
            println("puzzle input converts to hexstring of $hashString")
        }
    }
})

