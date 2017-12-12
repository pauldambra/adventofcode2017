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
})


class Pipes(val connections: MutableMap<Int, List<Int>>) {

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

    fun programsInGroupWith(program: Int): Int {
        return connectionsFrom(program).count() + 1
    }


}