package com.pauldambra.adventofcode2017.day15

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

object NumberToBinaryTests : Spek({
    it("converts numbers to binary") {

        assertArraysEqual(1092455L.toBinary(), listOf(0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,1,0,1,0,1,1,0,1,1,0,0,1,1,1))
//        expect(1181022009L.toBinary()).to.have.all.elements(0,1,0,0,0,1,1,0,0,1,1,0,0,1,0,0,1,1,1,1,0,1,1,1,0,0,1,1,1,0,0,1)
//        expect(245556042L.toBinary()).to.have.all.elements(0,0,0,0,1,1,1,0,1,0,1,0,0,0,1,0,1,1,1,0,0,0,1,1,0,1,0,0,1,0,1,0)
//        expect(1744312007L.toBinary()).to.have.all.elements(0,1,1,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1,0,1,1,0,1,1,0,0,0,1,1,1)
//        expect(1352636452L.toBinary()).to.have.all.elements(0,1,0,1,0,0,0,0,1,0,0,1,1,1,1,1,1,0,0,1,1,0,0,0,0,0,1,0,0,1,0,0)
//        expect(430625591L.toBinary()).to.have.all.elements(0,0,0,1,1,0,0,1,1,0,1,0,1,0,1,0,1,1,0,1,0,0,1,1,0,0,1,1,0,1,1,1)
//        expect(1233683848L.toBinary()).to.have.all.elements(0,1,0,0,1,0,0,1,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,1,1,0,0,0,1,0,0,0)
//        expect(1431495498L.toBinary()).to.have.all.elements(0,1,0,1,0,1,0,1,0,1,0,1,0,0,1,0,1,1,1,0,0,0,1,1,0,1,0,0,1,0,1,0)
//        expect(137874439L.toBinary()).to.have.all.elements(0,0,0,0,1,0,0,0,0,0,1,1,0,1,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,1,1,1)
//        expect(285222916L.toBinary()).to.have.all.elements(0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,1,0,0)
    }
})

fun assertArraysEqual(actual: List<Long>, expected: List<Long>) {
    if (actual.size != expected.size) {
        generateArrayAssertionError(actual, expected)
    }

    actual.withIndex().forEach {
        if (it.value != expected[it.index]) {
            generateArrayAssertionError(actual, expected)
        }
    }
}

private fun generateArrayAssertionError(actual: List<Long>, expected: List<Long>) {
    var message = ""
    message += "arrays are not equal!\n"
    message += "actual has ${actual.size} elements\n"
    message += "expected has ${expected.size} elements\n"
    message += "actual:   $actual\n"
    message += "expected: $expected\n"
    throw AssertionError(message)
}
