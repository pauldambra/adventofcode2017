package com.pauldambra.adventofcode2017.day21

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.it


object PatternTests : Spek({
    it("can report its size") {
        val pattern = Pattern.parse(".#.\n" +
                "..#\n" +
                "###")
        expect(pattern.size()).to.equal(3)
    }

    context("splitting patterns") {
        it("can split a 2x2 square into 1 split") {
            val p = Pattern.parse("..\n..")
            val s = p.split()
            expect(s.count()).to.equal(1)
        }

//        it("can split a 4x4 square into 4 splits") {
//            val p = Pattern.parse("1234\n5678\n9abc\ndefg")
//            val s = p.split()
//            expect(s.count()).to.equal(4)
//        }
    }
})



class Pattern constructor(private val patternGrid: List<CharArray>) {
    companion object {
        fun parse(description: String) : Pattern {
            return Pattern(description.split("\n").map { it.toCharArray() })

        }
    }

    //assumes it's a square
    fun size() = patternGrid.count()

    fun split(): List<Pattern> {
        return listOf(this)
    }
}