package com.pauldambra.adventofcode2017.day9

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

private fun assertCountOfGroupsInStream(stream: String, expectedCountOfGroups: Int) {
    val countOfGroups = StreamReader().read(stream).countOfGroups
    expect(countOfGroups).to.equal(expectedCountOfGroups)
}

object StreamTests : Spek({
    describe("counting groups in a stream") {
        it("starts and ends with curly braces - and so is one group") {
            val stream = "{}"
            assertCountOfGroupsInStream(stream, 1)
        }
        it("with a single nested group") {
            val stream = "{{}}"
            assertCountOfGroupsInStream(stream, 2)
        }
        it("with two nested groups") {
            val stream = "{{{}}}"
            assertCountOfGroupsInStream(stream, 3)
        }
        it("with groups separated by commas") {
            val stream = "{{},{}}"
            assertCountOfGroupsInStream(stream, 3)
        }
        it("with 6 groups") {
            val stream = "{{{},{},{{}}}}"
            assertCountOfGroupsInStream(stream, 6)
        }
        it("doesn't count garbage (which is delimited by < and >)") {
            val stream = "{<{},{},{{}}>}"
            assertCountOfGroupsInStream(stream, 1)
        }
        it("ignores multiple garbage blocks") {
            val stream = "{<a>,<a>,<a>,<a>}"
            assertCountOfGroupsInStream(stream, 1)
        }
        it("counts nested groups that contain garbage") {
            val stream = "{{<a>},{<a>},{<a>},{<a>}}"
            assertCountOfGroupsInStream(stream, 5)
        }
        it("ignores characters escaped by a ! - here there is only one unescaped garbage closing >") {
            val stream = "{{<!>},{<!>},{<!>},{<a>}}"
            assertCountOfGroupsInStream(stream, 2)
        }
    }
})