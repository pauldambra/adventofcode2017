package com.pauldambra.adventofcode2017.day9

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

object GroupScoringTests : Spek({
    describe("groups have a score of one more than their containing group") {
        it("an empty stream {} has a score of 1.") {
            val stream = "{}"
            val expectedScore = 1
            expect(StreamReader().read(stream).score).to.equal(expectedScore)
        }
        it("{{{}}} has a score of 1 + 2 + 3 = 6.") {
            val stream = "{{{}}}"
            val expectedScore = 6
            expect(StreamReader().read(stream).score).to.equal(expectedScore)
        }
        it("{{},{}} has a score of 1 + 2 + 2 = 5.") {
            val stream = "{{},{}}"
            val expectedScore = 5
            expect(StreamReader().read(stream).score).to.equal(expectedScore)
        }
        it("{{{},{},{{}}}} has a score of 1 + 2 + 3 + 3 + 3 + 4 = 16.") {
            val stream = "{{{},{},{{}}}}"
            val expectedScore = 16
            expect(StreamReader().read(stream).score).to.equal(expectedScore)
        }
        it("{<a>,<a>,<a>,<a>} has a score of 1.") {
            val stream = "{<a>,<a>,<a>,<a>}"
            val expectedScore = 1
            expect(StreamReader().read(stream).score).to.equal(expectedScore)
        }
        it("{{<ab>},{<ab>},{<ab>},{<ab>}} has a score of 1 + 2 + 2 + 2 + 2 = 9.") {
            val stream = "{{<ab>},{<ab>},{<ab>},{<ab>}}"
            val expectedScore = 9
            expect(StreamReader().read(stream).score).to.equal(expectedScore)
        }
        it("{{<!!>},{<!!>},{<!!>},{<!!>}} has a score of 1 + 2 + 2 + 2 + 2 = 9.") {
            val stream = "{{<!!>},{<!!>},{<!!>},{<!!>}}"
            val expectedScore = 9
            expect(StreamReader().read(stream).score).to.equal(expectedScore)
        }
        it("{{<a!>},{<a!>},{<a!>},{<ab>}} has a score of 1 + 2 = 3.") {
            val stream = "{{<a!>},{<a!>},{<a!>},{<ab>}}"
            val expectedScore = 3
            expect(StreamReader().read(stream).score).to.equal(expectedScore)
        }
    }
})