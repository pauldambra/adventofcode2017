package com.pauldambra.adventofcode2017.day16

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.it


object DanceTests : Spek({
    context("spinning: written sX, makes X programs move from the end to the front, but maintain their order otherwise. (For example, s3 on abcde produces cdeab).") {
        it("s3 on abcde produces cdeab") {
            val final: Group =  Group("abcde").singleDanceStep("s3")
            expect(final).to.equal(Group("cdeab"))
        }

        it("s1 on abcde produces eabdc") {
            val final: Group =  Group("abcde").singleDanceStep("s1")
            expect(final).to.equal(Group("eabcd"))
        }
    }
    context("exchanging: written x0/1, makes the programs at positions 0 and 1 swap places") {
        it("eabcd x3/4, swapping the last two programs: eabdc") {
            val final: Group =  Group("eabcd").singleDanceStep("x3/4")
            expect(final).to.equal(Group("eabdc"))
        }
    }
    context("Partner, written pA/B, makes the programs named A and B swap places.") {
        it(" pe/b, swapping programs e and b: baedc") {
            val final: Group =  Group("eabdc").singleDanceStep("pe/b")
            expect(final).to.equal(Group("baedc"))
        }
    }
    context("taking multiple instructions") {
        it("can take three steps from abcde") {
            val final: Group =  Group("abcde").dance("s1,x3/4,pe/b")
            expect(final).to.equal(Group("baedc"))
        }
    }
    context("puzzle input") {
        it("can dance the puzzle input for part one") {
            val startingGroup = Group("abcdefghijklmnop")
            val moves = this::class.java
                    .getResource("/day16.txt")
                    .readText()
            val final: Group =  startingGroup.dance(moves)
            println("day 16 part 1: the programs are standing in this order once the dance is over: $final")
        }
    }
})

data class Group(private val startingState: String) {
    fun dance(moves: String): Group {
        var current = this
        moves.split(",").forEach {
            current = current.singleDanceStep(it)
        }
        return current
    }

    fun singleDanceStep(danceMove: String): Group {
        val type = danceMove.first()
        return when (type) {
            's' -> spin(danceMove)
            'x' -> exchange(danceMove)
            'p' -> partner(danceMove)
            else -> throw Exception("unknown dance move $type")
        }
    }

    private fun partner(danceMove: String): Group {
        val exchangePrograms = danceMove.drop(1).split(("/"))
        val firstIndex = startingState.indexOf(exchangePrograms[0])
        val secondIndex = startingState.indexOf(exchangePrograms[1])
        return Group(swapCharactersAt(firstIndex, secondIndex))

    }

    private fun exchange(danceMove: String): Group {
        val exchangePositions = danceMove.drop(1).split(("/")).map(String::toInt)
        return Group(swapCharactersAt(exchangePositions[0], exchangePositions[1]))
    }

    private fun swapCharactersAt(firstIndex: Int, secondIndex: Int): String {
        val first = startingState[firstIndex]
        val second = startingState[secondIndex]
        val chars = startingState.toCharArray()
        chars[firstIndex] = second
        chars[secondIndex] = first
        return chars.joinToString("")
    }

    private fun spin(danceMove: String): Group {
        val spinFrom = danceMove.drop(1)
        val zeroIndexedPositionsBeforeSpin = listOf(spinFrom.toInt(), 1).max()!!
        val reversed = startingState.toCharArray().reversed()
        val start = reversed.take(zeroIndexedPositionsBeforeSpin).reversed().joinToString("")
        val end = reversed.takeLast(startingState.length - zeroIndexedPositionsBeforeSpin).reversed().joinToString("")
        return Group("$start$end")
    }
}