package com.pauldambra.adventofcode2017.day8

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

val exampleInput = "b inc 5 if a > 1\n" +
        "a inc 1 if b < 5\n" +
        "c dec -10 if a >= 1\n" +
        "c inc -20 if c == 10"

data class Condition(private val target: String, private val comparison: String, private val amount: Int) {

    fun isSatisfiedBy(registers: Map<String, Int>): Boolean {
        val register = registers[target]
        return if (register != null) {
            when (comparison) {
                "==" -> register == amount
                "!=" -> register != amount
                ">" -> register > amount
                ">=" -> register >= amount
                "<" -> register < amount
                "<=" -> register <= amount
                else -> false
            }
        } else {
            println("could not find `$target` in registers")
            false
        }



    }
}

class Instruction(s: String) {
    val targetRegister: String
    val direction: String
    val amount: Int
    val condition: Condition

    init {
        val parts = s.split(" ")
        targetRegister = parts[0]
        direction = parts[1]
        amount = parts[2].toInt()

        condition = Condition(parts[4], parts[5], parts[6].toInt())
    }

}

object RegisterTests : Spek({
    describe("part one") {

        it("can solve the example input") {
            val registers = Registers()
            val instructions = Registers.splitInstructions(exampleInput)
            val registerResults = registers.process(instructions)
            expect(registerResults.finalMaxValue).to.equal(1)
            expect(registerResults.highestSeenValue).to.equal(10)
        }
        it("can solve the puzzle input") {
            val instructions = this::class.java
                    .getResource("/day_8.txt")
                    .readText()
            val maxRegister = Registers().process(Registers.splitInstructions(instructions))
            println("max for puzzle input is $maxRegister")
            expect(maxRegister.finalMaxValue).to.be.below(6807)
        }
    }
})

class Registers {
    companion object {
        fun splitInstructions(instructions: String) : List<String> = instructions.split("\n")

        fun initialiseTheRegisters(instructions: List<String>): MutableMap<String, Int> {
            val registerNames = instructions
                    .map { it.split(" ", limit = 2) }
                    .map { it[0] }
                    .distinct()

            return registerNames
                    .associateBy({ it }, { 0 })
                    .toMutableMap()
        }
    }
    fun process(instructions: List<String>): RegisterResults {
        val registers = initialiseTheRegisters(instructions)
        val maxValuesSeen = mutableListOf<Int>()
        instructions.map { Instruction(it) }
                .forEach { i ->

                    if (i.condition.isSatisfiedBy(registers.toMap())) {
                        when (i.direction) {
                            "inc" -> registers[i.targetRegister] = registers[i.targetRegister]?.plus(i.amount) ?: 0
                            "dec" -> registers[i.targetRegister] = registers[i.targetRegister]?.minus(i.amount) ?: 0
                        }

                    }
                    maxValuesSeen.add(registers.values.max()!!)
                }

        return RegisterResults(registers.values.max()!!, maxValuesSeen.max()!!)
    }

}

data class RegisterResults(val finalMaxValue: Int, val highestSeenValue: Int)