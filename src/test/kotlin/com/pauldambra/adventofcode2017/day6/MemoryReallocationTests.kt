package com.pauldambra.adventofcode2017.day6

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

val puzzleInput = "11\t11\t13\t7\t0\t15\t5\t5\t4\t4\t1\t1\t7\t1\t15\t11"

object MemoryReallocationTests : Spek({
    describe("part one and two") {
        it("can solve the example problem") {
            val finalState = MemoryAllocator().stepsToSeenBefore("0\t2\t7\t0")

            expect(finalState.cycles).to.equal(5)
            expect(finalState.stateSeenBefore).to.equal(listOf(2,4,1,2))
            expect(finalState.loopSize).to.equal(4)
        }

        it("can count cycles for the example input") {
            val finalState = MemoryAllocator().stepsToSeenBefore(puzzleInput)
            println("finishes after ${finalState.cycles} cycles")
            println("finishes with loop of size ${finalState.loopSize}")
        }
    }
})

data class FinalState(val cycles: Int, val loopSize: Int, val stateSeenBefore: List<Int>)

class MemoryAllocator {
    fun stepsToSeenBefore(startingBlocksInBanks: String): FinalState {
        val blocks = startingBlocksInBanks.split("\t").map { it.toInt() }.toMutableList()

        var cycles = 0
        val previousSteps = HashMap<Int, MutableList<List<Int>>>()
        var result: FinalState? = null

        while (result == null) {
            cycles++

            var currentLargest = blocks.max()
            var position = blocks.indexOfFirst { it == currentLargest }

            blocks[position] = 0
            while(currentLargest!! > 0) {
                position = nextIndex(position, blocks.size)
                blocks[position] += 1
                currentLargest--
            }

            val matches = previousSteps.filter { it.value.contains(blocks) }
            if (matches.any()) {
                matches.entries.forEach { println("seen this memory state: $blocks before in cycle ${it.key}") }
                val loopSize = cycles - matches.entries.first().key
                result = FinalState(cycles, loopSize, blocks)
                break
            }

            addPreviousStep(cycles, previousSteps, blocks.toList())
        }

        return result!!
    }

    private fun addPreviousStep(cycles: Int, previousSteps: HashMap<Int, MutableList<List<Int>>>, currentMemoryState: List<Int>) {
        if (!previousSteps.containsKey(cycles)) {
            previousSteps[cycles] = mutableListOf()
        }
        previousSteps[cycles]!!.add(currentMemoryState)
    }

    private fun nextIndex(current: Int, size: Int): Int = if (current + 1 >= size) 0 else current + 1

}
