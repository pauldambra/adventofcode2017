package com.pauldambra.adventofcode2017.day15

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.math.exp


object GeneratorTests : Spek({

    on("generating the remainder of dividing the previous value * the generators factor by 2147483647") {
        it("generator A can produce its first value based on provided input") {
            val generatorA = Generator.A()
            expect(generatorA.next()).to.equal(1092455)
        }

        it("generator B can produce its first value based on provided input") {
            val generatorB = Generator.B()
            expect(generatorB.next()).to.equal(430625591)
        }

        it("generator A uses the last generated remainder as its next input") {
            val generatorA = Generator.A()
            generatorA.next()
            val secondOutput = generatorA.next()
            expect(secondOutput).to.equal(1181022009)
        }

        it("generator B uses the last generated remainder as its next input") {
            val generatorB = Generator.B()
            generatorB.next()
            val secondOutput = generatorB.next()
            expect(secondOutput).to.equal(1233683848)
        }

        it("can generate the first five values for generator A") {
            val genny = Generator.A()
            val values = (0..4).map { genny.next() }
            expect(values).to.equal(listOf(1092455, 1181022009, 245556042, 1744312007, 1352636452))
        }

        it("can generate the first five values for generator B") {
            val genny = Generator.B()
            val values = (0..4).map { genny.next() }
            expect(values).to.equal(listOf(430625591, 1233683848, 1431495498, 137874439, 285222916))
        }
    }
})

object PuzzleInputTests : Spek({
    it("can find pairs in the puzzle input for part one") {
        val generatorA = Generator.A(699L)
        val generatorB = Generator.B(124L)
        val matchCount: Int = Judge(generatorA, generatorB).compare(40_000_000)
        println("day 15 - part 1: match count for 40,000,000 pairs is $matchCount")
    }

    it("can find pairs in the puzzle input for part two") {
        val generatorA = Generator.A(699L, multiple = 4)
        val generatorB = Generator.B(124L, multiple = 8)
        val matchCount: Int = Judge(generatorA, generatorB).compare(5_000_000)
        println("day 15 - part 2: match count for 5,000,000 pairs is $matchCount")
    }
})

object JudgeTests : Spek({
    it("compares the last 16 bits of generator A and B") {
        val matchCount: Int = Judge().compare(1)
        expect(matchCount).to.equal(0)
    }

    it("can compare more than one pair from generator a and b") {
        val matchCount: Int = Judge().compare(5)
        expect(matchCount).to.equal(1)
    }

    it("can find 588 pairs in 40 million example comparisons") {
        val matchCount: Int = Judge().compare(40_000_000)
        expect(matchCount).to.equal(588)
    }

    it("can find 1 match in 1056 when the generators only provide multiples") {
        val generatorA = Generator.A(multiple = 4)
        val generatorB = Generator.B(multiple = 8)
        val matchCount: Int = Judge(generatorA, generatorB).compare(1056)
        expect(matchCount).to.equal(1)
    }

    it("can find 309 pairs in 5 million example comparisons when the generators only provide multiples") {
        val generatorA = Generator.A(multiple = 4)
        val generatorB = Generator.B(multiple = 8)
        val matchCount: Int = Judge(generatorA, generatorB).compare(5_000_000)
        expect(matchCount).to.equal(309)
    }
})

class Judge(private val generatorA: Generator = Generator.A(), private val generatorB: Generator = Generator.B()) {
    fun compare(pairCount: Int): Int {
        return (1..pairCount).fold(0) { acc, _ ->
            val a = generatorA.next().toBinary().takeLast(16)
            val b = generatorB.next().toBinary().takeLast(16)
            if (a == b) {
                acc + 1
            } else {
                acc
            }
        }
    }


}

class Generator(private val startingValue: Long, private val factor: Int, val multiple: Int?) {
    companion object {

        fun A(startingValue: Long = 65, multiple: Int? = null) : Generator = Generator(startingValue, 16807, multiple)
        fun B(startingValue: Long = 8921, multiple: Int? = null) : Generator = Generator(startingValue, 48271, multiple)
    }
    private val magicDivisor = 2147483647

    private var previousReturn: Long? = null
    fun next(): Long {
        if (multiple == null) {
            return generateNextNumber()
        }
        else {
            var nextNumber: Long
            do {
                nextNumber = generateNextNumber()
            } while (nextNumber % multiple != 0L)
            return nextNumber
        }
    }

    private fun Generator.generateNextNumber(): Long {
        previousReturn = previousReturn ?: startingValue
        previousReturn = (previousReturn!! * factor) % magicDivisor
        return previousReturn!!
    }

}