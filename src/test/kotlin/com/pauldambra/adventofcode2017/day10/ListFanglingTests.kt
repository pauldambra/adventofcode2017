package com.pauldambra.adventofcode2017.day10

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

object ListFanglingTests : Spek({
    describe("list fangling") {
        it("can reverse the first two items in a list") {
            expect(ListFangler.reverseSubList(listOf(0, 1, 2, 3), 2)).to.equal(listOf(1, 0, 2, 3))
        }

        it("can reverse the first three items in a list") {
            expect(ListFangler.reverseSubList(listOf(0, 1, 2, 3), 3)).to.equal(listOf(2, 1, 0, 3))
        }

        it("can reverse two items starting at a specified index") {
            expect(ListFangler.reverseSubList(listOf(0, 1, 2, 3), 2, startIndex = 1)).to.equal(listOf(0, 2, 1, 3))
        }

        it("circles around the list") {
            expect(ListFangler.reverseSubList(listOf(0, 1, 2, 3), 3, startIndex = 2)).to.equal(listOf(2, 1, 0, 3))
        }
    }

})