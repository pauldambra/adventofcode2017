package com.pauldambra.adventofcode2017.day19

data class PathPosition(val coordinate: Coordinate, val pathCharacter: Char) {
    companion object {
        fun routeEnd() = PathPosition(Coordinate(-1, -1), ' ')
    }
}