package com.pauldambra.adventofcode2017.day19

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

object MapTests : Spek({
    it("can instantiate a map with a single straight line path") {
        val map = Map(
                "  |  \n" +
                "  A  ")
        expect(map.lettersSeen()).to.equal(listOf("A"))
    }

    it("can instantiate a map with a single straight line path through two letters") {
        val map = Map(
                "  |  \n" +
                "  A  \n" +
                "  |  \n" +
                "  B  \n")
        expect(map.lettersSeen()).to.equal(listOf("A", "B"))
    }
})

data class PathPosition(val rowNumber: Int, val column: Int, val pathCharacter: Char)

val routeEnd = PathPosition(-1, -1, '!')

class Map {
    private val seenLetters = mutableListOf<Char>()

    constructor(drawing: String) {
        parse(drawing)
    }

    private fun parse(drawing: String) {
        val mapLines = drawing.split("\n").map { it.toCharArray() }
        mapLines.forEach { println(it) }

        val startingPoint = mapLines[0].withIndex().single { it.value == '|' }
        val startingPosition = PathPosition(0, startingPoint.index, startingPoint.value)

        var currentPosition = startingPosition
        while(currentPosition != routeEnd) {
            currentPosition = findNextPosition(mapLines, currentPosition)
            if (!currentPosition.pathCharacter.isLetter()) continue

            seenLetters.add(currentPosition.pathCharacter)
        }
    }

    private fun findNextPosition(mapLines: List<CharArray>, currentPosition: PathPosition): PathPosition {
        val nextRowNumber = currentPosition.rowNumber + 1
        val nextColumn = currentPosition.column

        if (nextRowNumber >= mapLines.size) return routeEnd

        val nextRow = mapLines[nextRowNumber]

        if (nextColumn >= nextRow.size) return routeEnd

        val nextCharacter = nextRow[nextColumn]
        return PathPosition(nextRowNumber, nextColumn, nextCharacter)
    }

    fun lettersSeen(): List<String> {
        return seenLetters.map { it.toString() }
    }
}