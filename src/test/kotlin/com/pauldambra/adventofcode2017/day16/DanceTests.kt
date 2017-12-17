package com.pauldambra.adventofcode2017.day16

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.it


object DanceTests : Spek({
    context("spinning: written sX, makes X programs move from the end to the front, but maintain their order otherwise. (For example, s3 on abcde produces cdeab).") {
        it("s3 on abcde produces cdeab") {
            val group = Group("abcde".toCharArray().toMutableList())
            group.singleDanceStep("s3")
            expect(group).to.equal(Group("cdeab".toCharArray().toMutableList()))
        }

        it("s1 on abcde produces eabdc") {
            val group = Group("abcde".toCharArray().toMutableList())
            group.singleDanceStep("s1")
            expect(group).to.equal(Group("eabcd".toCharArray().toMutableList()))
        }
    }
    context("exchanging: written x0/1, makes the programs at positions 0 and 1 swap places") {
        it("eabcd x3/4, swapping the last two programs: eabdc") {
            val group = Group("eabcd".toCharArray().toMutableList())
            group.singleDanceStep("x3/4")
            expect(group).to.equal(Group("eabdc".toCharArray().toMutableList()))
        }
    }
    context("Partner, written pA/B, makes the programs named A and B swap places.") {
        it(" pe/b, swapping programs e and b: baedc") {
            val group = Group("eabdc".toCharArray().toMutableList())
            group.singleDanceStep("pe/b")
            expect(group).to.equal(Group("baedc".toCharArray().toMutableList()))
        }
    }
    context("taking multiple instructions") {
        it("can take three steps from abcde") {
            val group = Group("abcde".toCharArray().toMutableList())
            group.dance("s1,x3/4,pe/b", 1)
            expect(group).to.equal(Group("baedc".toCharArray().toMutableList()))
        }
    }
    context("repeating the dance") {
        it("can repeat the dance twice") {
            val group = Group("abcde".toCharArray().toMutableList())
            group.dance("s1,x3/4,pe/b", 2)
            expect(group).to.equal(Group("ceadb".toCharArray().toMutableList()))
        }
    }
    context("puzzle input") {
        it("can dance the puzzle input for part one") {
            val moves = this::class.java
                    .getResource("/day16.txt")
                    .readText()

            val group = Group("abcdefghijklmnop".toCharArray().toMutableList())
            group.dance(moves, 1)

            println("day 16 part 1: the programs are standing in this order once the dance is over: $group")
            expect(group.state).to.equal("ionlbkfeajgdmphc".toCharArray().toMutableList())
        }

        it("can dance the puzzle input for part two") {
            val moves = this::class.java
                    .getResource("/day16.txt")
                    .readText()

            val group = Group("abcdefghijklmnop".toCharArray().toMutableList())
            group.dance(moves, 1_000_000_000)

            val finalState = group.state.joinToString("")
            println("day 16 part 2: the programs are standing in this order once the dance is over: $finalState")
            expect(finalState).to.equal("fdnphiegakolcmjb")
        }
    }
})


