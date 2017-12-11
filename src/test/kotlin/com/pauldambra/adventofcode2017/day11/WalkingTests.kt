package com.pauldambra.adventofcode2017.day11

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

object WalkingTests : Spek({
    it("can follow a path") {
        expect(Hex(0, 0).walk("ne")).to.equal(Hex(1, -1))
        expect(Hex(0, 0).walk("ne,ne,ne")).to.equal(Hex(3, -3))
        expect(Hex(0, 0).walk("ne,ne,sw,sw")).to.equal(Hex(0, 0))
        expect(Hex(0, 0).walk("ne,ne,s,s")).to.equal(Hex(2, 0))
        expect(Hex(0, 0).walk("se,sw,se,sw,sw")).to.equal(Hex(-1, 3))
    }
})