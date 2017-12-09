package com.pauldambra.adventofcode2017.day9

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

object PuzzleInputTests : Spek({
    it("can read the puzzle input and report on it") {
        val stream = this::class.java
                .getResource("/day9.txt")
                .readText()
        val (countOfGroups, score, garbageCharacters) = StreamReader().read(stream)
        println("there are $countOfGroups groups in total with a score of $score")
        println("there were $garbageCharacters garbage characters")
    }
})