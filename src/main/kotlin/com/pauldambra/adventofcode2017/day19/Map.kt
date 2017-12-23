package com.pauldambra.adventofcode2017.day19

import com.pauldambra.adventofcode2017.Direction

class Map(private val mapGrid: List<CharArray>) {

    companion object {
        fun parse(drawing: String) = Map(drawing.split("\n").map { it.toCharArray() })
    }

    private val seenLetters = mutableListOf<Char>()
    private var direction = Direction.DOWN
    private val visited = mutableListOf<Coordinate>()
    private var stepsTaken = 0

    fun walkPath() {
//        mapGrid.forEach {
//            println("${it.map { x -> x.toString() }}")
//        }

        val startingPosition = findEntryToMap()

        var currentPosition = startingPosition
        while (currentPosition != PathPosition.routeEnd()) {
            visited.add(currentPosition.coordinate)
            stepsTaken++

            maybeChangeDirection(currentPosition)

            currentPosition = findNextPosition(mapGrid, currentPosition)

            recordVisitedLetters(currentPosition)
        }
    }

    private fun recordVisitedLetters(currentPosition: PathPosition) {
        if (currentPosition.pathCharacter.isLetter()) seenLetters.add(currentPosition.pathCharacter)
    }

    private fun maybeChangeDirection(currentPosition: PathPosition) {
        if (currentPosition.pathCharacter == '+') {
            direction = seekNewDirection(currentPosition.coordinate, mapGrid)
        }
    }

    private fun findEntryToMap(): PathPosition {
        val startingPoint = mapGrid[0].withIndex().single { it.value == '|' }
        return PathPosition(Coordinate(0, startingPoint.index), startingPoint.value)
    }

    private fun findNextPosition(mapGrid: List<CharArray>, position: PathPosition): PathPosition {
        val next = position.coordinate.nextWhenHeading(direction)

        if (isImpossibleRow(next.row, mapGrid.size - 1)) return PathPosition.routeEnd()
        val nextRow = mapGrid[next.row]

        if (isImpossibleColumn(next.column, nextRow.size - 1)) return PathPosition.routeEnd()
        val nextCharacter = nextRow[next.column]

        val nextCoordinate = Coordinate(next.row, next.column)

        return if (nextCharacter.isWhitespace()) {
            PathPosition.routeEnd()
        } else {
            PathPosition(nextCoordinate, nextCharacter)
        }
    }

    private fun isImpossibleColumn(columnIndex: Int, max: Int) =
            columnIndex < 0 || max < columnIndex

    private fun isImpossibleRow(rowIndex: Int, max: Int) =
            rowIndex < 0 || max < rowIndex

    private fun seekNewDirection(coordinate: Coordinate, mapGrid: List<CharArray>) =
            coordinate
                    .neighbours()
                    .filter { !visited.contains(it.value) }
                    .filter { 0 < it.value.row && it.value.row < mapGrid.size }
                    .filter { 0 < it.value.column && it.value.column < mapGrid[it.value.row].size }
                    .entries
                    .single {
                        val pathCandidate = mapGrid[it.value.row][it.value.column]
                        !pathCandidate.isWhitespace()
                    }.key

    fun lettersSeen(): List<String> = seenLetters.map { it.toString() }

    fun stepsTaken(): Int = stepsTaken
}