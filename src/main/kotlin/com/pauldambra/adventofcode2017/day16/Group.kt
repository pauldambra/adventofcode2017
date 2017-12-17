package com.pauldambra.adventofcode2017.day16

data class Group(var state: MutableList<Char>) {
    private val seenDances = mutableMapOf<List<Char>, List<Char>>()

    fun dance(moves: String, repeats: Int) {
//        var startTime = System.currentTimeMillis()
        (1..repeats).forEach { n ->

//            if (n % 1_000_000 == 0) {
//                val elapsed = System.currentTimeMillis() - startTime
//                val expectedCompletion = ((elapsed.toDouble() / 1_000_000) / 1000) * 1_000_000_000
//                println("$n: took $elapsed milliseconds for a million. so about $expectedCompletion seconds total")
//                startTime = System.currentTimeMillis()
//            }

            val start = state.toList()

            //if we've seen a start before then we don't need to do the dance again
            if (seenDances.containsKey(start)) {
                state = seenDances[start]!!.toMutableList()
            } else {
                moves.split(",").forEach { singleDanceStep(it) }
                val end = state.toList()
                seenDances.put(start, end)
            }
        }
    }

    fun singleDanceStep(danceMove: String) {
        val type = danceMove.first()
        when (type) {
            's' -> spin(danceMove)
            'x' -> exchange(danceMove)
            'p' -> partner(danceMove)
            else -> throw Exception("unknown dance move $type")
        }
    }

    private fun partner(danceMove: String) {
        val exchangePrograms = danceMove.drop(1).split(("/"))
        val firstIndex = state.indexOf(exchangePrograms[0].toCharArray()[0])
        val secondIndex = state.indexOf(exchangePrograms[1].toCharArray()[0])
        swapCharactersAt(firstIndex, secondIndex)

    }

    private fun exchange(danceMove: String) {
        val exchangePositions = danceMove.drop(1).split(("/")).map(String::toInt)
        swapCharactersAt(exchangePositions[0], exchangePositions[1])
    }

    private fun swapCharactersAt(firstIndex: Int, secondIndex: Int) {
        val first = state[firstIndex]
        val second = state[secondIndex]
        state[firstIndex] = second
        state[secondIndex] = first
    }

    private fun spin(danceMove: String) {
        val zeroIndexedPositionsBeforeSpin = listOf(danceMove.drop(1).toInt(), 1).max()!!
        val reversed = state.reversed()
        val start = reversed.take(zeroIndexedPositionsBeforeSpin).reversed()
        val end = reversed.takeLast(state.size - zeroIndexedPositionsBeforeSpin).reversed()
        state = (start + end).toMutableList()
    }
}