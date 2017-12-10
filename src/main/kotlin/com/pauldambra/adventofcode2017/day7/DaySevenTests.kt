package com.pauldambra.adventofcode2017.day7

import kotlin.math.absoluteValue

class Tower {
    private val programs: HashMap<String, Program> = HashMap()

    fun parse(exampleInput: String): Tower {
        exampleInput
                .split("\n")
                .filter { !it.isEmpty() }
                .map { Program.parse(it) }
                .forEach { programs.put(it.name, it) }

        programs.forEach { _, v ->
            v.childrenNames.filter { it.isNotBlank() }.forEach { c ->
                val child = programs[c]
                v.children.put(child!!.name, child)
                child.parent = v
            }
        }

        return this
    }

    fun findRootProgram(): Program {
        var nodes = programs.values.map { walkToParentFrom(it) }.distinct()
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

        fun parse(source: String): Program {
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

    fun hasParent(): Boolean = parent != null

    fun balance(): Int = seekUnbalancedNode(this, 0, listOf())

    private fun seekUnbalancedNode(program: Program, balancingNecessary: Int, unbalancedNodes: List<String>): Int {
        val sums = program.children.values.associateBy({ it.name }, { sumChildren(listOf(it)) })
        return if (sums.values.distinct().size == 1) {
            program.weight - balancingNecessary
        } else {
            val balancedNodesValue = sums.entries.groupBy { it.value }.filter { it.value.size != 1 }.keys.first()
            val unbalancedNode = sums.entries.groupBy { it.value }.filter { it.value.size == 1 }.values.first().first()
            val balancingNecessary = (balancedNodesValue - unbalancedNode.value).absoluteValue
            seekUnbalancedNode(program.children[unbalancedNode.key]!!, balancingNecessary, unbalancedNodes + unbalancedNode.key)
        }
    }

    private fun sumChildren(programs: List<Program>): Int {
        val thisLevelSum = programs.sumBy { p -> p.weight }
        val children = programs.flatMap { it.children.values }

        return if (children.isEmpty())
            thisLevelSum
        else
            thisLevelSum + sumChildren(children)
    }

}