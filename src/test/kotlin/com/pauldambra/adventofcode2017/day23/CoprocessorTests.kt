package com.pauldambra.adventofcode2017.day23

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

val puzzleInput = "set b 67\n" +
        "set c b\n" +
        "jnz a 2\n" +
        "jnz 1 5\n" +
        "mul b 100\n" +
        "sub b -100000\n" +
        "set c b\n" +
        "sub c -17000\n" +
        "set f 1\n" +
        "set d 2\n" +
        "set e 2\n" +
        "set g d\n" +
        "mul g e\n" +
        "sub g b\n" +
        "jnz g 2\n" +
        "set f 0\n" +
        "sub e -1\n" +
        "set g e\n" +
        "sub g b\n" +
        "jnz g -8\n" +
        "sub d -1\n" +
        "set g d\n" +
        "sub g b\n" +
        "jnz g -13\n" +
        "jnz f 2\n" +
        "sub h -1\n" +
        "set g b\n" +
        "sub g c\n" +
        "jnz g 2\n" +
        "jnz 1 3\n" +
        "sub b -17\n" +
        "jnz 1 -23"

object CoprocessorTests : Spek({
    it("can set the value of a register to a number") {
        val c = Coprocessor()
        c.execute("set a 12")
        expect(c.registers()['a']).to.equal(12)
    }

    it("can set the value of a register to another register") {
        val c = Coprocessor()
        c.execute("set a 12")
        c.execute("set b a")
        expect(c.registers()['b']).to.equal(12)
    }

    it("can subtract by a number") {
        val c = Coprocessor()
        c.execute("set a 12")
        c.execute("sub a 2")
        expect(c.registers()['a']).to.equal(10)
    }

    it("can subtract by a register") {
        val c = Coprocessor()
        c.execute("set a 12")
        c.execute("sub b a")
        expect(c.registers()['b']).to.equal(-12)
    }

    it("can multiply by a number") {
        val c = Coprocessor()
        c.execute("set a 12")
        c.execute("mul a 2")
        expect(c.registers()['a']).to.equal(24)
    }

    it("can multiply by a register") {
        val c = Coprocessor()
        c.execute("set a 12\n"+
                "set b 3\n"+
                "mul b a")
        expect(c.registers()['b']).to.equal(36)
    }

    it("can jump when not zero") {
        val c = Coprocessor()
        c.execute("set a 12\n"+
                "jnz a 2\n"+
                "set b 2\n"+
                "mul b a")
        expect(c.registers()['b']).to.equal(0)
        expect(c.registers()['a']).to.equal(12)
    }

    it("can count muls") {
        val c = Coprocessor()
        c.execute("set a 12\n"+
                "set b 3\n"+
                "mul b a")
        expect(c.mulCount()).to.equal(1)
    }

    it("can count muls in the puzzle input") {
        val c = Coprocessor()
        c.execute(puzzleInput)
        println("day 23 part 1: mul count is ${c.mulCount()}")
    }

    it("can start with register a set to 1") {
        val c = Coprocessor(1L)
        expect(c.registers()['a']).to.equal(1)
    }

    it("can solve for h in the puzzle input") {
        val c = Coprocessor(1)
        c.execute(puzzleInput)
        println("day 23 part 2: h is ${c.registers()['h']}")
    }
})

class Coprocessor(registerA: Long = 0) {
    private val registers = ('a'..'h').map { Pair(it, 0L) }.toMap().toMutableMap()
    init {
        registers['a'] = registerA
    }
    private var mulCount = 0
    fun mulCount() = mulCount

    fun execute(instruction: String) {
        val instructions = instruction.split("\n")
        var nextIndex = 0

        controlLoop@ while (0 <= nextIndex && nextIndex < instructions.size) {
            val instructionParts = instructions[nextIndex].split(" ")
            when {
                instructionParts[0] == "set" -> {
                    val register = instructionParts[1].toCharArray().single()
                    val x = readValueFromInstruction(instructionParts[2])
//                println("setting $register to $x")
                    registers[register] = x
                }
                instructionParts[0] == "jnz" -> {
                    val gate = readValueFromInstruction(instructionParts[1])
                    if (gate != 0L) {
                        val jumpAmount = readValueFromInstruction(instructionParts[2])
//                    println("jumping by $jumpAmount")
                        nextIndex += jumpAmount.toInt()
                        continue@controlLoop
                    }
                }
                instructionParts[0] == "add" -> applyOperatorTo(instructionParts, Long::plus)
                instructionParts[0] == "mul" -> {
                    mulCount++
                    applyOperatorTo(instructionParts, Long::times)
                }
                instructionParts[0] == "mod" -> applyOperatorTo(instructionParts, Long::rem)
                instructionParts[0] == "sub" -> applyOperatorTo(instructionParts, Long::minus)
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

    fun registers() = registers.toMap()
}