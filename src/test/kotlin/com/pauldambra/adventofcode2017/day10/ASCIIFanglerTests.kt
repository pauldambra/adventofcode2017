package com.pauldambra.adventofcode2017.day10

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

object ASCIIFanglerTests : Spek({
    it("can convert arrays to their ascii characters (including commas!)") {
        val original = "1,2,3"
        expect(ASCIIFangler.toASCII(original)).to.equal(listOf(49, 44, 50, 44, 51))
    }

    it("can add a suffix of 17, 31, 73, 47, 23") {
        val original = "1,2,3"
        expect(ASCIIFangler.toASCII(original).withSuffix()).to.equal(listOf(49, 44, 50, 44, 51, 17, 31, 73, 47, 23))
    }
})