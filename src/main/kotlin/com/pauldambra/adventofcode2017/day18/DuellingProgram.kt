package com.pauldambra.adventofcode2017.day18

import java.util.*

class DuellingProgram(val programId: Long) {
    private val registers = mutableMapOf<Char, Long>()

    var sendCount = 0

    private lateinit var inboundQueue: ArrayDeque<Long>
    val outboundQueue: ArrayDeque<Long> = ArrayDeque()

    init {
        ('a'..'z').forEach { registers[it] = 0 }
        registers['p'] = programId
    }

    fun registers(): Map<Char, Long> {
        return registers.toMap()
    }

    fun execute(instruction: String, index: Int): Pair<Int, Boolean> {
        var isWaiting = false
        var nextIndex = index

        val instructionParts = instruction.split(" ")
        when {
            instructionParts[0] == "snd" -> {
                val sendValue = readValueFromInstruction(instructionParts[1])
                sendCount++
                println("have now sent $sendCount messages from program: $programId")
                outboundQueue.addLast(sendValue)
            }
            instructionParts[0] == "rcv" -> {
                val nextValue = if (inboundQueue.isNotEmpty()) inboundQueue.pop() else null
                if (nextValue == null) {
                    println("program: $programId waiting for next value on queue!")
                    isWaiting = true
                } else {
                    registers[instructionParts[1].toCharArray().first()] = nextValue
                }
            }
            instructionParts[0] == "jgz" -> {
                val gate = readValueFromInstruction(instructionParts[1])
                if (gate > 0) {
                    val jumpAmount = readValueFromInstruction(instructionParts[2])
//                    println("jumping by $jumpAmount")
                    nextIndex = jumpAmount.toInt() + index
                }
            }
            instructionParts[0] == "set" -> {
                val register = instructionParts[1].toCharArray().single()
                val x = readValueFromInstruction(instructionParts[2])
//                println("setting $register to $x")
                registers[register] = x
            }
            instructionParts[0] == "add" -> applyOperatorTo(instructionParts, Long::plus)
            instructionParts[0] == "mul" -> applyOperatorTo(instructionParts, Long::times)
            instructionParts[0] == "mod" -> applyOperatorTo(instructionParts, Long::rem)
        }

        val next = if (nextIndex == index && !isWaiting) nextIndex + 1 else nextIndex
        return Pair(
                next,
                isWaiting)
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

    fun setInboundQueue(otherOutboundQueue: ArrayDeque<Long>) {
        inboundQueue = otherOutboundQueue
    }
}

class DuellingPrograms {

    val programZero = DuellingProgram(0)
    val prorgramOne = DuellingProgram(1)

    init {
        programZero.setInboundQueue(prorgramOne.outboundQueue)
        prorgramOne.setInboundQueue(programZero.outboundQueue)
    }

    fun execute(instruction: String) {
        val instructions = instruction.split("\n")

        var programZeroIndex = 0
        var programOneIndex = 0

        var isWaiting0 = false
        var isWaiting1 = false

        while (atLeastOneProgramIsNotWaiting(isWaiting0, isWaiting1)) {
            val (i, w) = programZero.execute(instructions[programZeroIndex], programZeroIndex)
            programZeroIndex = i
            isWaiting0 = w

            val (i1, w1) = prorgramOne.execute(instructions[programOneIndex], programOneIndex)
            programOneIndex = i1
            isWaiting1 = w1

            println("end of run")
            println("prog zero: $programZeroIndex and waiting? $isWaiting0")
            println("prog one: $programOneIndex and waiting? $isWaiting1")
            println("-----------")


        }
    }

    private fun atLeastOneProgramIsNotWaiting(isWaiting0: Boolean, isWaiting1: Boolean) =
            !(isWaiting0 && isWaiting1)
}