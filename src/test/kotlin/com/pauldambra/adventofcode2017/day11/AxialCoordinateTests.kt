package com.pauldambra.adventofcode2017.day11

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it


object AxialCoordinateTests : Spek({
    it("changes axial coordinates when moving") {
        expect(Hex(0, 0).move(Direction.NORTH)).to.equal(Hex(0, -1))
        expect(Hex(0, 0).move(Direction.NORTH_EAST)).to.equal(Hex(1, -1))
        expect(Hex(0, 0).move(Direction.SOUTH_EAST)).to.equal(Hex(1, 0))
        expect(Hex(0, 0).move(Direction.SOUTH)).to.equal(Hex(0, 1))
        expect(Hex(0, 0).move(Direction.SOUTH_WEST)).to.equal(Hex(-1, 1))
        expect(Hex(0, 0).move(Direction.NORTH_WEST)).to.equal(Hex(-1, 0))
    }
    it("can move some other example hex cells") {
        expect(Hex(-3, 1).move(Direction.SOUTH_EAST)).to.equal(Hex(-2, 1))
        expect(Hex(2, 1).move(Direction.SOUTH_WEST)).to.equal(Hex(1, 2))
        expect(Hex(1, -3).move(Direction.NORTH_WEST)).to.equal(Hex(0, -3))
    }
})

