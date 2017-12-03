package com.pauldambra.adventofcode2017.day3

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

object SideCounterTests : Spek({
    it("returns 2") {
        val sideCounter = SideCounter()
        val results = (1..1).map { _ -> sideCounter.next() }
        expect(results).to.have.all.elements(2)
    }
    it("returns 2, 1") {
        val sideCounter = SideCounter()
        val results = (1..2).map { _ -> sideCounter.next() }
        expect(results).to.have.all.elements(2, 1)

    }
    it("returns 2, 1, 2") {
        val sideCounter = SideCounter()
        val results = (1..3).map { _ -> sideCounter.next() }
        expect(results).to.have.all.elements(2, 1, 2)
    }
    it("returns 2, 1, 2, 2") {
        val sideCounter = SideCounter()
        val results = (1..4).map { _ -> sideCounter.next() }
        expect(results).to.have.all.elements(2, 1, 2, 2)
    }
    it("returns 2, 1, 2, 2, 3") {
        val sideCounter = SideCounter()
        val results = (1..5).map { _ -> sideCounter.next() }
        expect(results).to.have.all.elements(2, 1, 2, 2, 3)
    }
    it("returns 2, 1, 2, 2, 3, 3") {
        val sideCounter = SideCounter()
        val results = (1..6).map { _ -> sideCounter.next() }
        expect(results).to.have.all.elements(2, 1, 2, 2, 3, 3)
    }
    it("returns 2, 1, 2, 2, 3, 3, 4") {
        val sideCounter = SideCounter()
        val results = (1..7).map { _ -> sideCounter.next() }
        expect(results).to.have.all.elements(2, 1, 2, 2, 3, 3, 4)
    }
    it("returns 2, 1, 2, 2, 3, 3, 4, 4") {
        val sideCounter = SideCounter()
        val results = (1..8).map { _ -> sideCounter.next() }
        expect(results).to.have.all.elements(2, 1, 2, 2, 3, 3, 4, 4)
    }
    it("returns 2, 1, 2, 2, 3, 3, 4, 4, 5") {
        val sideCounter = SideCounter()
        val results = (1..9).map { _ -> sideCounter.next() }
        expect(results).to.have.all.elements(2, 1, 2, 2, 3, 3, 4, 4, 5)
    }
    it("returns 2, 1, 2, 2, 3, 3, 4, 4, 5, 5") {
        val sideCounter = SideCounter()
        val results = (1..10).map { _ -> sideCounter.next() }
        expect(results).to.have.all.elements(2, 1, 2, 2, 3, 3, 4, 4, 5, 5)
    }
    it("returns 2, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6") {
        val sideCounter = SideCounter()
        val results = (1..11).map { _ -> sideCounter.next() }
        expect(results).to.have.all.elements(2, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6)
    }
    it("returns 2, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6") {
        val sideCounter = SideCounter()
        val results = (1..12).map { _ -> sideCounter.next() }
        expect(results).to.have.all.elements(2, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6)
    }
    it("returns 2, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7") {
        val sideCounter = SideCounter()
        val results = (1..13).map { _ -> sideCounter.next() }
        expect(results).to.have.all.elements(2, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7)
    }
    it("returns 2, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7") {
        val sideCounter = SideCounter()
        val results = (1..14).map { _ -> sideCounter.next() }
        expect(results).to.have.all.elements(2, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7)
    }
    it("returns 2, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8") {
        val sideCounter = SideCounter()
        val results = (1..15).map { _ -> sideCounter.next() }
        expect(results).to.have.all.elements(2, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8)
    }
    it("returns 2, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8") {
        val sideCounter = SideCounter()
        val results = (1..16).map { _ -> sideCounter.next() }
        expect(results).to.have.all.elements(2, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8)
    }
    it("returns 2, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9") {
        val sideCounter = SideCounter()
        val results = (1..17).map { _ -> val x = sideCounter.next(); println("x is $x"); x }
        println(results)
        expect(results).to.have.all.elements(2, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9)
    }
})