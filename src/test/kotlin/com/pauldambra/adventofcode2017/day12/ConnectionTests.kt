package com.pauldambra.adventofcode2017.day12

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

object ConnectionTests : Spek({

    it("can connect 0 to 2") {
        val pipes = Pipes.from("0 <-> 2")
        expect(pipes.connectionsFrom(0)).to.equal(listOf(2))
    }
    it("can connect 1 to 1") {
        val pipes = Pipes.from("1 <-> 1")
        expect(pipes.connectionsFrom(1)).to.equal(listOf(1))
    }
    it("can connect one program to many") {
        val pipes = Pipes.from("4 <-> 1, 2, 3")
        expect(pipes.connectionsFrom(4)).to.equal(listOf(1, 2, 3))
    }
    it("can connect many programs") {
        val pipes = Pipes.from("0 <-> 2\n" +
                "1 <-> 1\n" +
                "2 <-> 0, 3, 4\n" +
                "3 <-> 2, 4\n" +
                "4 <-> 2, 3, 6\n" +
                "5 <-> 6\n" +
                "6 <-> 4, 5")
        expect(pipes.connectionsFrom(0)).to.equal(listOf(2))
        expect(pipes.connectionsFrom(1)).to.equal(listOf(1))
        expect(pipes.connectionsFrom(2)).to.equal(listOf(0, 3, 4))
        expect(pipes.connectionsFrom(3)).to.equal(listOf(2, 4))
        expect(pipes.connectionsFrom(4)).to.equal(listOf(2, 3, 6))
        expect(pipes.connectionsFrom(5)).to.equal(listOf(6))
        expect(pipes.connectionsFrom(6)).to.equal(listOf(4, 5))
    }
})

object ProgramGroupTests : Spek({

    it("can find a single group (which is programs that are connected)") {
        val pipes = Pipes.from("0 <-> 2")
        expect(pipes.programsInGroupWith(0)).to.equal(2)
    }
    it("can find a single group with two members") {
        val pipes = Pipes.from("0 <-> 2, 3")
        expect(pipes.programsInGroupWith(0)).to.equal(3)
    }
    it("can find group members with transitive connections e.g. 0 <-> 1 and 1 <-> 2 so 0 <-> 2") {
        val pipes = Pipes.from("0 <-> 1, 3\n" +
                "1 <-> 2")
        expect(pipes.programsInGroupWith(0)).to.equal(4)
    }
    it("can count 6 programs in the group in the example input") {
        val pipes = Pipes.from("0 <-> 2\n" +
                "1 <-> 1\n" +
                "2 <-> 0, 3, 4\n" +
                "3 <-> 2, 4\n" +
                "4 <-> 2, 3, 6\n" +
                "5 <-> 6\n" +
                "6 <-> 4, 5")
        println("there are ${pipes.programsInGroupWith(0)} programs in a group with program 0")
    }
    it("can count programs in the group of 0 in the puzzle input") {
        val connections = this::class.java
                .getResource("/day12.txt")
                .readText()
        val pipes = Pipes.from(connections)
        println("Day 12: there are ${pipes.programsInGroupWith(0)} programs in a group with 0")
    }

    it("can find all groups") {
        val pipes = Pipes.from("0 <-> 2\n" +
                "1 <-> 1\n" +
                "2 <-> 0, 3, 4\n" +
                "3 <-> 2, 4\n" +
                "4 <-> 2, 3, 6\n" +
                "5 <-> 6\n" +
                "6 <-> 4, 5")
        val groups = pipes.countAllGroups()
        println("found $groups as the groups in the input")
        expect(groups.count()).to.equal(2)
    }
    it("can count the groups in the puzzle input") {
        val connections = this::class.java
                .getResource("/day12.txt")
                .readText()
        val pipes = Pipes.from(connections)
        val groups = pipes.countAllGroups()
        println("Day 12: there are ${groups.count()} groups in the input")
    }
})


class Pipes(private val connections: MutableMap<Int, List<Int>>) {

    companion object {
        fun from(description: String) : Pipes {
            val connections = mutableMapOf<Int, List<Int>>()

            val descriptionLines = description.split("\n")

            descriptionLines.forEach {
                val split = it.split(" <-> ")

                val parent = split[0].toInt()
                val children = split[1].split(", ").map { it.toInt() }

                connections.put(parent, children)
            }

            return Pipes(connections)
        }
    }

    fun connectionsFrom(program: Int) : List<Int> {
        return connections[program] ?: emptyList()
    }

    fun programsInGroupWith(program: Int): Int = (countChildrenFrom(program)).distinct().count()


    private val visitedChildren = mutableListOf<Int>()
    private fun countChildrenFrom(program: Int): List<Int> {
        if (visitedChildren.contains(program)) {
            //println("not walking children of $program again")
            return listOf(program)
        }
        visitedChildren.add(program)
        val descendants = connectionsFrom(program).map { countChildrenFrom(it) }.flatten()
        return listOf(program) + descendants
    }

    fun countAllGroups(): MutableList<Int> {
        var unvisitedProgram: Int?
        var unvisitedPrograms = mutableListOf<Int>()
        do {
            unvisitedProgram = connections.keys.find { !visitedChildren.distinct().contains(it) }
            if (unvisitedProgram != null) {
                unvisitedPrograms.add(unvisitedProgram)
                countChildrenFrom(unvisitedProgram)
            }
        } while(unvisitedProgram != null)
        return unvisitedPrograms
    }


}