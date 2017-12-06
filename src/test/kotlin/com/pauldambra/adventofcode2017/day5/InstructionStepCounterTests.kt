package com.pauldambra.adventofcode2017.day5

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it


object InstructionStepCounterTests : Spek({
    describe("part one") {
        it("can exit the example instruction in 5 cycles") {
            val instruction = mutableListOf(0, 3, 0, 1, -3)

            val stepsToExit = stepsToExit(instruction)
            expect(stepsToExit).to.equal(5)
        }

        it("can count the puzzle input cycles") {
            val instructions = this::class.java
                    .getResource("/day5message.txt")
                    .readText()
                    .split("\n")
                    .map { it.toInt() }
                    .toMutableList()
            val stepsToExit = stepsToExit(instructions)
            println("took $stepsToExit to exit")
        }
    }
    describe("part two") {
        it("Using this rule with the above example, the process now takes 10 cycles, and the offset values after finding the exit are left as 2 3 2 3 -1.") {
            val instruction = mutableListOf(0, 3, 0, 1, -3)

            val stepsToExit = variableInstructionStepsToExit(instruction)
            expect(stepsToExit).to.equal(10)
        }
        it("can count the puzzle input cycles") {
            val instructions = this::class.java
                    .getResource("/day5message.txt")
                    .readText()
                    .split("\n")
                    .map { it.toInt() }
                    .toMutableList()
            val stepsToExit = variableInstructionStepsToExit(instructions)
            println("took $stepsToExit to exit")
        }
    }
})


    fun stepsToExit(instruction: MutableList<Int>) : Int {
        var steps = 0
        var position = 0
        while (position < instruction.size) {
            val currentInstruction = instruction[position]
            instruction[position] = currentInstruction + 1
            position += currentInstruction
            steps++
        }
        return steps
    }

fun variableInstructionStepsToExit(instruction: MutableList<Int>) : Int {
    var steps = 0
    var position = 0
    while (position < instruction.size) {
        val offset = instruction[position]
        instruction[position] = if (offset >= 3) offset - 1 else offset + 1
        position += offset
        steps++
    }
    return steps
}
