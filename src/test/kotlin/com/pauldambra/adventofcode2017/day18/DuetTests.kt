package com.pauldambra.adventofcode2017.day18

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.it
import kotlin.math.nextUp

val puzzleInput = "set i 31\n" +
        "set a 1\n" +
        "mul p 17\n" +
        "jgz p p\n" +
        "mul a 2\n" +
        "add i -1\n" +
        "jgz i -2\n" +
        "add a -1\n" +
        "set i 127\n" +
        "set p 680\n" +
        "mul p 8505\n" +
        "mod p a\n" +
        "mul p 129749\n" +
        "add p 12345\n" +
        "mod p a\n" +
        "set b p\n" +
        "mod b 10000\n" +
        "snd b\n" +
        "add i -1\n" +
        "jgz i -9\n" +
        "jgz a 3\n" +
        "rcv b\n" +
        "jgz b -1\n" +
        "set f 0\n" +
        "set i 126\n" +
        "rcv a\n" +
        "rcv b\n" +
        "set p a\n" +
        "mul p -1\n" +
        "add p b\n" +
        "jgz p 4\n" +
        "snd a\n" +
        "set a b\n" +
        "jgz 1 3\n" +
        "snd b\n" +
        "set f 1\n" +
        "add i -1\n" +
        "jgz i -11\n" +
        "snd a\n" +
        "jgz f -16\n" +
        "jgz a -19"

object DuetTests : Spek({
    context("the assembly is meant to operate on a set of registers that are each named with a single letter and that can each hold a single integer") {
        context("set") {

            it("can set the a register") {
                val card = SoundCard()
                expect(card.registers()['a']).to.equal(0)

                card.execute("set a 1")
                expect(card.registers()['a']).to.equal(1)
            }

            it("can set the a register to a different number") {
                val card = SoundCard()
                card.execute("set a 3")
                expect(card.registers()['a']).to.equal(3)
            }

            it("can set the b register to a number") {
                val card = SoundCard()
                card.execute("set b 9")
                expect(card.registers()['b']).to.equal(9)
            }

            it("can starts with every register from a - z set to 0") {
                val card = SoundCard()


                ('a'..'z').forEach {
                    expect(card.registers()[it]).to.equal(0)
                }
            }

            it("can set every register from a - z set to a number") {
                val card = SoundCard()


                ('a'..'z').forEach {
                    val randomNumberOverZero = Math.random().nextUp().toInt() + 1
                    card.execute("set $it $randomNumberOverZero")
                    expect(card.registers()[it]).to.be.above(0)
                }
            }
        }

        context("add") {
            it("can add a positive number") {
                val card = SoundCard()
                card.execute("set b 2")
                card.execute("add b 3")
                expect(card.registers()['b']).to.equal(5)
            }
            it("can add a negative number") {
                val card = SoundCard()
                card.execute("add c -13")
                expect(card.registers()['c']).to.equal(-13)
            }
            it("can add the content of another register") {
                val card = SoundCard()
                card.execute("set a 9")
                card.execute("set b 2")
                card.execute("add b a")
                expect(card.registers()['b']).to.equal(11)
            }
        }

        context("mul") {
            it("can multiply by a number") {
                val card = SoundCard()
                card.execute("set c 2")
                card.execute("mul c 10")
                expect(card.registers()['c']).to.equal(20)
            }

            it("can multiply a register by itself") {
                val card = SoundCard()
                card.execute("set c 2")
                card.execute("mul c c")
                expect(card.registers()['c']).to.equal(4)
            }

            it("can multiply a register by another register") {
                val card = SoundCard()
                card.execute("set a 9")
                card.execute("set c 3")
                card.execute("mul c a")
                expect(card.registers()['c']).to.equal(27)
            }
        }

        context("mod") {
            it("can modulo by a number where there is no remainder") {
                val card = SoundCard()
                card.execute("set c 10")
                card.execute("mod c 2")
                expect(card.registers()['c']).to.equal(0)
            }

            it("can modulo by a number where there is a remainder") {
                val card = SoundCard()
                card.execute("set c 9")
                card.execute("mod c 2")
                expect(card.registers()['c']).to.equal(1)
            }

            it("can modulo by a register") {
                val card = SoundCard()
                card.execute("set c 10")
                card.execute("set a 3")
                card.execute("mod c a")
                expect(card.registers()['c']).to.equal(1)
            }

        }

        context("making a sound") {
            it("plays a sound from a number") {
                val card = SoundCard()
                card.execute("snd 12")
                expect(card.playing()).to.equal(12)
            }

            it("plays a sound from a register") {
                val card = SoundCard()
                card.execute("set c 9")
                card.execute("snd c")
                expect(card.playing()).to.equal(9)
            }
        }

        context("jumping instructions: jgz X Y jumps with an offset of the value of Y, but only if the value of X is greater than zero. (An offset of 2 skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)") {
            it("implies there can be many instructions passed in at once") {
                val card = SoundCard()
                card.execute("set a 1\n" +
                        "add a 2\n" +
                        "mul a a\n" +
                        "mod a 5")
                expect(card.registers()['a']).to.equal(4)
            }

            it("can jump forwards within the instruction range") {
                val card = SoundCard()
                card.execute("set a 1\n" +
                        "add a 2\n" +
                        "mul a a\n" +
                        "jgz a 2\n" +
                        "mul a a\n" +
                        "mod a 3")
                //equiv of set 1, add 2, square, skip, mod 5
                expect(card.registers()['a']).to.equal(0)
            }

            it("stops if the jump goes before the instruction range") {
                val card = SoundCard()
                card.execute("set a 1\n" +
                        "jgz a -2\n" +
                        "set a 12")
                expect(card.registers()['a']).to.equal(1)
            }

            it("stops if the jump goes after the instruction range") {
                val card = SoundCard()
                card.execute("set a 1\n" +
                        "jgz a 2\n" +
                        "set a 12")
                expect(card.registers()['a']).to.equal(1)
            }

            it("doesn't jump if the listed register is 0 - i.e. jgz a 4 is ignored if a == 0") {
                val card = SoundCard()
                card.execute("set a 1\n" +
                        "jgz b 2\n" +
                        "set a 12")
                expect(card.registers()['a']).to.equal(12)
            }
        }

        context("rcv: rcv X recovers the frequency of the last sound played, but only when the value of X is not zero. (If it is zero, the command does nothing.)") {
            it("can recover sounds?!") {
                val card = SoundCard()
                card.execute("set a 2\n" +
                        "snd a\n" +
                        "rcv a")
                expect(card.recoveredSounds()).to.equal(listOf(2))
            }
            it("does not recover sounds if the listed register is 0?!") {
                val card = SoundCard()
                card.execute("set a 2\n" +
                        "snd a\n" +
                        "rcv b")
                expect(card.recoveredSounds()).to.equal(listOf())
            }
        }

        it("can solve the example input") {
            val card = SoundCard()
            card.execute("set a 1\n" +
                    "add a 2\n" +
                    "mul a a\n" +
                    "mod a 5\n" +
                    "snd a\n" +
                    "set a 0\n" +
                    "rcv a\n" +
                    "jgz a -1\n" +
                    "set a 1\n" +
                    "jgz a -2")
            expect(card.recoveredSounds().first()).to.equal(4)
        }

        it("can find the first recover value for the puzzle input") {
            val card = SoundCard()
            card.execute(puzzleInput)
            val firstRecoveredSound = card.recoveredSounds().first()
            println("day 18 part 1: the first recover issued in the puzzle input is: $firstRecoveredSound")
            expect(firstRecoveredSound).to.equal(3188)
        }
    }
})

