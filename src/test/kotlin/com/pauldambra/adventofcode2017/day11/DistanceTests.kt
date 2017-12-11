package com.pauldambra.adventofcode2017.day11

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

object DistanceTests : Spek({
    it("can calculate distance between hexes") {
        expect(Hex(0, 0).distanceTo(Hex(0, 0))).to.equal(0)
        expect(Hex(0, 0).distanceTo(Hex(1, -1))).to.equal(1)
        expect(Hex(0, 0).distanceTo(Hex(0, 0))).to.equal(0)
        expect(Hex(0, 0).distanceTo(Hex(2, 0))).to.equal(2)
        expect(Hex(0, 0).distanceTo(Hex(-1, 3))).to.equal(3)
    }
})