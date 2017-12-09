package com.pauldambra.adventofcode2017.day8

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

object ConditionTests: Spek({
    it("can be satisified") {
        val c = Condition("c", "==", 2)
        val satisfied = c.isSatisfiedBy(hashMapOf(Pair("b", 0), Pair("c", 2)))
        expect(satisfied).to.be.`true`
    }

    it("can not be satisfied") {
        val c = Condition("c", "==", 3)
        val satisfied = c.isSatisfiedBy(hashMapOf(Pair("b", 0), Pair("c", 2)))
        expect(satisfied).to.be.`false`
    }

    it("can !=") {
        val c = Condition("c", "!=", 2)
        val satisfied = c.isSatisfiedBy(hashMapOf(Pair("b", 0), Pair("c", 3)))
        expect(satisfied).to.be.`true`
    }

    it("can >") {
        val c = Condition("b", ">", 2)
        val satisfied = c.isSatisfiedBy(hashMapOf(Pair("b", 3), Pair("c", 2)))
        expect(satisfied).to.be.`true`
    }

    it("can >=") {
        val c = Condition("b", ">=", 2)
        val satisfied = c.isSatisfiedBy(hashMapOf(Pair("b", 2), Pair("c", 2)))
        expect(satisfied).to.be.`true`
    }

    it("can <") {
        val c = Condition("b", "<", 2)
        val satisfied = c.isSatisfiedBy(hashMapOf(Pair("b", 1), Pair("c", 2)))
        expect(satisfied).to.be.`true`
    }

    it("can <=") {
        val c = Condition("b", "<=", 2)
        val satisfied = c.isSatisfiedBy(hashMapOf(Pair("b", 2), Pair("c", 2)))
        expect(satisfied).to.be.`true`
    }
})