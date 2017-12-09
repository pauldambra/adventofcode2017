package com.pauldambra.adventofcode2017.day9

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

/**
 * `<>`, 0 characters.
 * `<random characters>`, 17 characters.
 * `<<<<>`, 3 characters.
 * `<{!>}>`, 2 characters.
 * `<!!>`, 0 characters.
 * `<!!!>>`, 0 characters.
 * `<{o"i!a,<{i<a>`, 10 characters.
 */
object GarbageCountingTests : Spek({
    describe("inside a garbage block") {
        it("can handle an empty garbage block") {
            val stream = "<>"
            expect(StreamReader().read(stream).garbageCharactersCount).to.equal(0)
        }

        it("can count the characters in a single garbage block") {
            val stream = "<random characters>"
            expect(StreamReader().read(stream).garbageCharactersCount).to.equal(17)
        }

        it("garbage opening character (<) has no special meaning") {
            val stream = "<<<<>"
            expect(StreamReader().read(stream).garbageCharactersCount).to.equal(3)
        }

        it("block opening and closing characters have no special meaning") {
            val stream = "<{}>"
            expect(StreamReader().read(stream).garbageCharactersCount).to.equal(2)
        }

        it("escaped characters and the escaping character (!) are not counted") {
            val stream = "<{!>}>"
            expect(StreamReader().read(stream).garbageCharactersCount).to.equal(2)
        }

        it("the escape character can be escaped inside a garbage block and is not counted") {
            val stream = "<!!>"
            expect(StreamReader().read(stream).garbageCharactersCount).to.equal(0)
        }

        it("can count a mix of escaped and unescaped characters") {
            val stream = "<{o\"i!a,<{i<a>"
            expect(StreamReader().read(stream).garbageCharactersCount).to.equal(10)
        }
    }
})