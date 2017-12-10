package com.pauldambra.adventofcode2017.day7

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on


object DaySevenTests : Spek({
    describe("day seven") {
        on("part one") {
            it("can solve the example input") {
                val exampleInput = "pbga (66)\n" +
                        "xhth (57)\n" +
                        "ebii (61)\n" +
                        "havc (66)\n" +
                        "ktlj (57)\n" +
                        "fwft (72) -> ktlj, cntj, xhth\n" +
                        "qoyq (66)\n" +
                        "padx (45) -> pbga, havc, qoyq\n" +
                        "tknk (41) -> ugml, padx, fwft\n" +
                        "jptl (61)\n" +
                        "ugml (68) -> gyxo, ebii, jptl\n" +
                        "gyxo (61)\n" +
                        "cntj (57)"

                val rootProgram = Tower().parse(exampleInput).findRootProgram()
                expect(rootProgram.name).to.equal("tknk")
            }

            it("can solve the puzzle input") {
                val programInfo = this::class.java
                        .getResource("/day_7_input.txt")
                        .readText()
                val rootProgram = Tower().parse(programInfo).findRootProgram()
                println("root program is ${rootProgram.name}")
            }
        }

        on("part two") {
            it("can balance the example tree") {
                val exampleInput = "pbga (66)\n" +
                        "xhth (57)\n" +
                        "ebii (61)\n" +
                        "havc (66)\n" +
                        "ktlj (57)\n" +
                        "fwft (72) -> ktlj, cntj, xhth\n" +
                        "qoyq (66)\n" +
                        "padx (45) -> pbga, havc, qoyq\n" +
                        "tknk (41) -> ugml, padx, fwft\n" +
                        "jptl (61)\n" +
                        "ugml (68) -> gyxo, ebii, jptl\n" +
                        "gyxo (61)\n" +
                        "cntj (57)"

                val rootProgram = Tower().parse(exampleInput).findRootProgram()
                val unbalancedProgramWeight = rootProgram.balance()
                expect(unbalancedProgramWeight).to.equal(60)
            }

            it("can solve the puzzle input") {
                val programInfo = this::class.java
                        .getResource("/day_7_input.txt")
                        .readText()
                val rootProgram = Tower().parse(programInfo).findRootProgram()
                val unbalancedProgramWeight = rootProgram.balance()
                println("unbalanced program balancing weight is ${unbalancedProgramWeight}")
            }
        }
    }
})

