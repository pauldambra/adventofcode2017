package com.pauldambra.adventofcode2017.day10

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

object HashFanglerTests : Spek({
    it("can bitwise xor a sparse hash of 16 numbers") {
        val sparseHash = listOf(65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22)
        expect(HashFangler.toDenseHash(sparseHash)).to.equal(64)
    }

    it("can hexstring 7 to 07") {
        expect(HashFangler.toHexString(listOf(7))).to.equal("07")
    }

    it("can hexstring 64, 7, 255 to 4007ff") {
        expect(HashFangler.toHexString(listOf(64, 7, 255))).to.equal("4007ff")
    }
})