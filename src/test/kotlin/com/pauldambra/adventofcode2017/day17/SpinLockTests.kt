package com.pauldambra.adventofcode2017.day17

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it


object CircularArrayTests : Spek({
    it("can loop a circular array by index") {
        val list = listOf(0,1,2,3,4,5)
        expect(list.stepForward(0,1)).to.equal(1)
        expect(list.stepForward(0,2)).to.equal(2)
        expect(list.stepForward(0,3)).to.equal(3)
        expect(list.stepForward(0,4)).to.equal(4)
        expect(list.stepForward(0,5)).to.equal(5)
        expect(list.stepForward(0,6)).to.equal(0)
        expect(list.stepForward(0,7)).to.equal(1)

        expect(list.stepForward(3,0)).to.equal(3)
        expect(list.stepForward(3,1)).to.equal(4)
        expect(list.stepForward(3,2)).to.equal(5)
        expect(list.stepForward(3,3)).to.equal(0)
        expect(list.stepForward(3,4)).to.equal(1)
        expect(list.stepForward(3,5)).to.equal(2)
        expect(list.stepForward(3,6)).to.equal(3)
    }

    it("can insert into a circular array") {
        assertCanStepForwardAndInsert(
                mutableListOf(0, 1, 2, 3, 4, 5), 0,
                mutableListOf(0, 8, 1, 2, 3, 4, 5))

        assertCanStepForwardAndInsert(
                mutableListOf(0,1,2,3,4,5), 1,
                mutableListOf(0,1,8,2,3,4,5))

        assertCanStepForwardAndInsert(
                mutableListOf(0,1,2,3,4,5), 2,
                mutableListOf(0,1,2,8,3,4,5))

        assertCanStepForwardAndInsert(
                mutableListOf(0,1,2,3,4,5), 3,
                mutableListOf(0,1,2,3,8,4,5))

        assertCanStepForwardAndInsert(
                mutableListOf(0,1,2,3,4,5), 4,
                mutableListOf(0,1,2,3,4,8,5))

        assertCanStepForwardAndInsert(
                mutableListOf(0,1,2,3,4,5), 5,
                mutableListOf(0,1,2,3,4,5,8))

        assertCanStepForwardAndInsert(
                mutableListOf(0,1,2,3,4,5), 6,
                mutableListOf(0,8,1,2,3,4,5))

        assertCanStepForwardAndInsert(
                mutableListOf(0,1,2,3,4,5), 7,
                mutableListOf(0,1,8,2,3,4,5))
    }

})

private fun assertCanStepForwardAndInsert(start: MutableList<Int>, steps: Int, end: MutableList<Int>) {
    val next = start.stepForward(0, steps)
    start.insertAfter(next, 8)
    expect(start).to.equal(end)
}

object SpinLockTests : Spek({
    it("starts at 0") {
        val spinLock = SpinLock(0)
        expect(spinLock.current()).to.equal(listOf(0))
    }

    it("can take the example steps") {
        val expected = listOf(
        listOf(0),
        listOf(0, 1),
        listOf(0, 2, 1),
        listOf(0, 2, 3, 1),
        listOf(0, 2, 4, 3, 1),
        listOf(0, 5, 2, 4, 3, 1),
        listOf(0, 5, 2, 4, 3, 6, 1),
        listOf(0, 5, 7, 2, 4, 3, 6, 1),
        listOf(0, 5, 7, 2, 4, 3, 8, 6, 1),
        listOf(0, 9, 5, 7, 2, 4, 3, 8, 6, 1)
        )
        (0..9).forEach { n ->
            val sl = SpinLock(3)
            repeat(n) { _ -> sl.step() }
            expect(sl.current()).to.equal(expected[n])
        }
    }

    it("can find the value after the one at the end of the list") {
        val sl = SpinLock(3)
        repeat(3) { _ -> sl.step() }
        expect(sl.valueAfter(1)).to.equal(0)
    }

    it("can find the value after 2017") {
        val sl = SpinLock(3)
        repeat(2017) { _ -> sl.step() }
        expect(sl.valueAfter(2017)).to.equal(638)
    }

    it("can find the value after 2017 in a splinlock with the puzzle input for steps") {
        val sl = SpinLock(304)
        repeat(2017) { _ -> sl.step() }
        println("day 17 part 1: the value after 2017 is: ${sl.valueAfter(2017)}")
    }

    it("can find the value after 0 in a splinlock with the puzzle input after fifty million steps") {
        val sl = FirstIndexSpinLock(304)
        repeat(50_000_000) { _ -> sl.step() }
        println("day 17 part 2: the value after 0 is: ${sl.valueAfterZero()}")
    }
})

object FirstIndexTrackingSpinLock : Spek({
    it("can take the example steps") {
        val expected = listOf(
                listOf(0), // not this one!!
                listOf(0, 1), //or this one
                listOf(0, 2, 1),
                listOf(0, 2, 3, 1),
                listOf(0, 2, 4, 3, 1),
                listOf(0, 5, 2, 4, 3, 1),
                listOf(0, 5, 2, 4, 3, 6, 1),
                listOf(0, 5, 7, 2, 4, 3, 6, 1),
                listOf(0, 5, 7, 2, 4, 3, 8, 6, 1),
                listOf(0, 9, 5, 7, 2, 4, 3, 8, 6, 1)
        )
        (1..9).forEach { n ->
            val sl = FirstIndexSpinLock(3)
            repeat(n) { _ -> sl.step() }
            expect(sl.valueAfterZero()).to.equal(expected[n][1])
        }
    }
})

