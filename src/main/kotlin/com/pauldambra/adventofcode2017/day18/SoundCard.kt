package com.pauldambra.adventofcode2017.day18

class SoundCard {
    private val registers = mutableMapOf<Char, Long>()
    private var playing = 0L
    private val recoveredSounds = mutableListOf<Long>()

    init {
        ('a'..'z').forEach { registers[it] = 0 }
    }

    fun registers(): Map<Char, Long> {
        return registers.toMap()
    }

    fun execute(instruction: String) {
        val instructions = instruction.split("\n")

        var nextIndex = 0
        loop@ while (nextIndex >= 0 && nextIndex < instructions.size) {
            val thisInstruction = instructions[nextIndex]
            val instructionParts = thisInstruction.split(" ")
            when {
                instructionParts[0] == "jgz" -> {
                    val gate = readValueFromInstruction(instructionParts[1])
                    if (gate > 0) {
                        val jumpAmount = readValueFromInstruction(instructionParts[2])
//                        println("jumping by $jumpAmount")
                        nextIndex += jumpAmount.toInt()
                        continue@loop

                    }
                }
                instructionParts[0] == "set" -> {
                    val register = instructionParts[1].toCharArray().single()
                    val x = readValueFromInstruction(instructionParts[2])
//                    println("setting $register to $x")
                    registers[register] = x
                }
                instructionParts[0] == "snd" -> playing = readValueFromInstruction(instructionParts[1])
                instructionParts[0] == "rcv" -> {
                    if (readValueFromInstruction(instructionParts[1])> 0) {
                        recoveredSounds.add(playing)
                        break@loop //ugh infinite loops otherwise in part one
                    }

                }
                instructionParts[0] == "add" -> applyOperatorTo(instructionParts, Long::plus)
                instructionParts[0] == "mul" -> applyOperatorTo(instructionParts, Long::times)
                instructionParts[0] == "mod" -> applyOperatorTo(instructionParts, Long::rem)
            }
            nextIndex++
        }
    }

    private fun applyOperatorTo(instructionParts: List<String>, op: (Long, Long) -> Long) {
        val register = instructionParts[1].toCharArray().single()
        val current = registers[register]!!
        val x = readValueFromInstruction(instructionParts[2])
        val result = op(current, x)
//        println("$current ${instructionParts[0]} x = $result for register $register")
        registers[register] = result
    }

    private fun readValueFromInstruction(s: String): Long {
        return if (s.matches(Regex("-?\\d+"))) {
            s.toLong()
        } else {
            registers[s.toCharArray().single()]!!
        }
    }


    fun playing() = playing
    fun recoveredSounds(): List<Long> = recoveredSounds.toList()
}