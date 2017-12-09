package com.pauldambra.adventofcode2017.day8

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

object ParsingTests : Spek({
    it("can parse out the sections") {
        val i = Instruction("c inc -20 if c == 10")
        expect(i.targetRegister).to.equal("c")
        expect(i.direction).to.equal("inc")
        expect(i.amount).to.equal(-20)
        expect(i.condition).to.equal(Condition("c", "==", 10))
    }

    it("can split instructions") {
        val splitInstructions = Registers.splitInstructions(this::class.java
                .getResource("/day_8.txt")
                .readText())
        expect(splitInstructions.size).to.equal(1000)

        val registers = Registers.initialiseTheRegisters(splitInstructions)
        expect(registers).to.contain(Pair("znl", 0))
    }
})