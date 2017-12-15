package com.pauldambra.adventofcode2017.day13

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

object ScannerTests : Spek({
    it("starts at the top") {
        val layer = CalculatingLayer(0, 3)
        expect(layer.scannerPosition(0)).to.equal(0)
    }
    it("moves to the bottom of the layer's range") {
        val range = 5
        val layer = CalculatingLayer(0, range)
        val picoseconds = 0..4
        picoseconds.forEach {
            expect(layer.scannerPosition(it)).to.equal(it)
        }
    }
})