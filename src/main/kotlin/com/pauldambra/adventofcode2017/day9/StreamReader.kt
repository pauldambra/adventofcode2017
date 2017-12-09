package com.pauldambra.adventofcode2017.day9

data class StreamReadResult(val countOfGroups: Int, val score: Int, val garbageCharactersCount: Int)

class StreamReader {
    fun read(stream: String) : StreamReadResult {
        var groups = 0
        val scores = mutableListOf<Int>()
        var currentScore = 0
        var insideGarbage = false
        var escapeNextCharacter = false
        var garbageCharacterCount = 0

        for (ch in stream) {
            if (escapeNextCharacter) {
                escapeNextCharacter = false
                continue
            }

            if (ch == '!') {
                escapeNextCharacter = true
                continue
            }

            if (!insideGarbage && ch == '<') {
                insideGarbage = true
                continue
            }

            if (ch == '>') {
                insideGarbage = false
                continue
            }

            if (insideGarbage) {
                garbageCharacterCount += 1
                continue
            }

            if (ch == '{') {
                currentScore += 1
                scores.add(currentScore)
                groups += 1
            }

            if (ch == '}') {
                currentScore -= 1
            }
        }
        return StreamReadResult(groups, scores.sum(), garbageCharacterCount)
    }
}