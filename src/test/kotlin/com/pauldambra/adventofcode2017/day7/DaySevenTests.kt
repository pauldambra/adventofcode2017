package com.pauldambra.adventofcode2017.day7

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.math.absoluteValue


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
                val unbalancedProgramWeight= rootProgram.balance()
                expect(unbalancedProgramWeight).to.equal(60)
            }

            it("can solve the puzzle input") {
                val programInfo = this::class.java
                        .getResource("/day_7_input.txt")
                        .readText()
                val rootProgram = Tower().parse(programInfo).findRootProgram()
                val unbalancedProgramWeight= rootProgram.balance()
                println("unbalanced program balancing weight is ${unbalancedProgramWeight}")
            }
        }
    }
})

class Tower {
    private val programs: HashMap<String, Program> = HashMap()

    fun parse(exampleInput: String): Tower {
        exampleInput
                .split("\n")
                .filter { !it.isEmpty() }
                .map { Program.parse(it) }
                .forEach{ programs.put(it.name, it) }

        programs.forEach { _, v -> v.childrenNames.filter { it.isNotBlank() }.forEach {
            c ->
            val child = programs[c]
            v.children.put(child!!.name, child)
            child.parent = v
        } }

        return this
    }

    fun findRootProgram(): Program {
        var nodes = programs.values.map {walkToParentFrom(it)}.distinct()
        return nodes.first()
    }

    private fun walkToParentFrom(program: Program): Program {
        var node = program
        while (node.hasParent()) {
            node = node.parent!!
        }
        return node
    }


}

class Program(internal val name: String, private val weight: Int, internal val childrenNames: List<String>) {

    internal var parent: Program? = null

    internal val children: HashMap<String, Program> = HashMap()

    companion object {

        fun parse(source: String) : Program {
            val parts = source.split(" -> ")

            try {
                val nw = parts[0].split(" ")
                val name = nw[0]
                val weight = nw[1].trim('(', ')').toInt()
                val children = if (parts.size == 2) parts[1].split(", ") else listOf()
                return Program(name, weight, children)
            } catch (e: Exception) {
                println("could not parse `$source` as a program")
                throw e
            }

        }

    }

    fun hasParent(): Boolean {
        return parent != null
    }

    fun balance() : Int {
        println("finding balance")

        return seekUnbalancedNode(this, 0, listOf())
    }

    private fun seekUnbalancedNode(program: Program, balancingNecessary: Int, unbalancedNodes: List<String>): Int {
        val sums = program.children.values.associateBy ({it.name}, {sumChildren(listOf(it))} )
        return if (sums.values.distinct().size == 1) {
            println("program ${program.name} has balanced children so returning the weight it would need")
            program.weight - balancingNecessary
        } else {
            println("program ${program.name} has unbalanced children")
            val balancedNodesValue = sums.entries.groupBy { it.value }.filter { it.value.size != 1 }.keys.first()
            println("balanced nodes have weight $balancedNodesValue")
            val unbalancedNode = sums.entries.groupBy { it.value }.filter { it.value.size == 1 }.values.first().first()
            println("unbalanced program is ${unbalancedNode.key} and has weight ${unbalancedNode.value}")
            val balancingNecessary = (balancedNodesValue - unbalancedNode.value).absoluteValue
            println("so balancing necessary is $balancingNecessary")
            seekUnbalancedNode(program.children[unbalancedNode.key]!!, balancingNecessary,unbalancedNodes + unbalancedNode.key)
        }
    }

    private fun sumChildren(programs: List<Program>) : Int {
//        println("summing children for ${programs.map { it.name }}")
        val thisLevelSum = programs.sumBy { p -> p.weight }
//        println("this level $thisLevelSum")

        val children = programs.flatMap { it.children.values }
        return if (children.isEmpty())
            thisLevelSum
        else
            thisLevelSum + sumChildren(children)
    }

}
