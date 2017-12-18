package com.pauldambra.adventofcode2017.day18

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.it
import java.util.*
import kotlin.math.nextUp


object DuellingProgramsTests : Spek({
    context("duelling programs") {
        it("starts with its program id in register p") {
            val card = DuellingProgram(12)
            expect(card.registers()['p']).to.equal(12)
        }

//        it("has a queue for the other program") {
//            val card = DuellingProgram(1)
//            card.execute("set a 2\n" +
//            "snd a")
//
//            val expected = ArrayDeque<Long>()
//            expected.push(2)
//
//            expect(card.outboundQueue).to.have.elements(2)
//        }
//
//        it("can run a second program that reads from the queue of the first") {
//            val otherProgramsOutboundQueue = ArrayDeque<Long>()
//            otherProgramsOutboundQueue.push(2)
//
//            val cardTwo = DuellingProgram(1)
//            cardTwo.setInboundQueue(otherProgramsOutboundQueue)
//
//            cardTwo.execute("rcv a")
//
//            expect(cardTwo.registers()['a']).to.equal(2)
//        }
//
//        it("ends on deadlock if there's nothing to receive on the queue") {
//            val otherProgramsOutboundQueue = ArrayDeque<Long>()
//
//            val cardTwo = DuellingProgram(1)
//            cardTwo.setInboundQueue(otherProgramsOutboundQueue)
//
//            cardTwo.execute("rcv a\nset a 200")
//
//            expect(cardTwo.registers()['a']).to.equal(0)
//        }

        it("can process example input") {
            val exampleInput = "snd 1\n" +
                    "snd 2\n" +
                    "snd p\n" +
                    "rcv a\n" +
                    "rcv b\n" +
                    "rcv c\n" +
                    "rcv d"

            val p = DuellingPrograms()

            p.execute(exampleInput)

            expect(p.prorgramOne.sendCount).to.equal(3)
        }

        it("can process puzzle input") {
            val p = DuellingPrograms()

            p.execute(puzzleInput)

            val sentMessages = p.prorgramOne.sendCount
            println("day 18 part 2: program one sent $sentMessages messages")
        }
    }

    context("set") {

        it("can set the a register") {
            val card = DuellingProgram(0)
            expect(card.registers()['a']).to.equal(0)

            card.execute("set a 1", 0)
            expect(card.registers()['a']).to.equal(1)
        }

        it("can set the a register to a different number") {
            val card = DuellingProgram(0)
            card.execute("set a 3", 0)
            expect(card.registers()['a']).to.equal(3)
        }

        it("can set the b register to a number") {
            val card = DuellingProgram(0)
            card.execute("set b 9", 0)
            expect(card.registers()['b']).to.equal(9)
        }

        it("can starts with every register from a - z set to 0") {
            val card = DuellingProgram(0)


            ('a'..'z').forEach {
                expect(card.registers()[it]).to.equal(0)
            }
        }

        it("can set every register from a - z set to a number") {
            val card = DuellingProgram(0)


            ('a'..'z').forEach {
                val randomNumberOverZero = Math.random().nextUp().toInt() + 1
                card.execute("set $it $randomNumberOverZero", 0)
                expect(card.registers()[it]).to.be.above(0)
            }
        }
    }

    context("add") {
        it("can add a positive number") {
            val card = DuellingProgram(0)
            card.execute("set b 2", 0)
            card.execute("add b 3", 0)
            expect(card.registers()['b']).to.equal(5)
        }
        it("can add a negative number") {
            val card = DuellingProgram(0)
            card.execute("add c -13", 0)
            expect(card.registers()['c']).to.equal(-13)
        }
        it("can add the content of another register") {
            val card = DuellingProgram(0)
            card.execute("set a 9", 0)
            card.execute("set b 2", 0)
            card.execute("add b a", 0)
            expect(card.registers()['b']).to.equal(11)
        }
    }

    context("mul") {
        it("can multiply by a number") {
            val card = DuellingProgram(0)
            card.execute("set c 2", 0)
            card.execute("mul c 10", 0)
            expect(card.registers()['c']).to.equal(20)
        }

        it("can multiply a register by itself") {
            val card = DuellingProgram(0)
            card.execute("set c 2", 0)
            card.execute("mul c c", 0)
            expect(card.registers()['c']).to.equal(4)
        }

        it("can multiply a register by another register") {
            val card = DuellingProgram(0)
            card.execute("set a 9", 0)
            card.execute("set c 3", 0)
            card.execute("mul c a", 0)
            expect(card.registers()['c']).to.equal(27)
        }
    }

    context("mod") {
        it("can modulo by a number where there is no remainder") {
            val card = DuellingProgram(0)
            card.execute("set c 10", 0)
            card.execute("mod c 2", 0)
            expect(card.registers()['c']).to.equal(0)
        }

        it("can modulo by a number where there is a remainder") {
            val card = DuellingProgram(0)
            card.execute("set c 9", 0)
            card.execute("mod c 2", 0)
            expect(card.registers()['c']).to.equal(1)
        }

        it("can modulo by a register") {
            val card = DuellingProgram(0)
            card.execute("set c 10", 0)
            card.execute("set a 3", 0)
            card.execute("mod c a", 0)
            expect(card.registers()['c']).to.equal(1)
        }

    }
})