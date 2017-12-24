package com.pauldambra.adventofcode2017.day24

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

val puzzleInput = "14/42\n" +
  "2/3\n" +
  "6/44\n" +
  "4/10\n" +
  "23/49\n" +
  "35/39\n" +
  "46/46\n" +
  "5/29\n" +
  "13/20\n" +
  "33/9\n" +
  "24/50\n" +
  "0/30\n" +
  "9/10\n" +
  "41/44\n" +
  "35/50\n" +
  "44/50\n" +
  "5/11\n" +
  "21/24\n" +
  "7/39\n" +
  "46/31\n" +
  "38/38\n" +
  "22/26\n" +
  "8/9\n" +
  "16/4\n" +
  "23/39\n" +
  "26/5\n" +
  "40/40\n" +
  "29/29\n" +
  "5/20\n" +
  "3/32\n" +
  "42/11\n" +
  "16/14\n" +
  "27/49\n" +
  "36/20\n" +
  "18/39\n" +
  "49/41\n" +
  "16/6\n" +
  "24/46\n" +
  "44/48\n" +
  "36/4\n" +
  "6/6\n" +
  "13/6\n" +
  "42/12\n" +
  "29/41\n" +
  "39/39\n" +
  "9/3\n" +
  "30/2\n" +
  "25/20\n" +
  "15/6\n" +
  "15/23\n" +
  "28/40\n" +
  "8/7\n" +
  "26/23\n" +
  "48/10\n" +
  "28/28\n" +
  "2/13\n" +
  "48/14"

object MagneticPortsTests : Spek(
  {
      it("can parse a component") {
          val c = Component.parse("0/10").single()
          expect(c.portOne).to.equal(0)
          expect(c.portTwo).to.equal(10)
      }

      it("can parse many components from a list") {
          val components = Component.parse("3/9\n" +
                                             "0/6")
          expect(components).to.equal(listOf(Component(3, 9), Component(0, 6)))
      }

      it("can check if it will connect to another component - i.e. has a matching port") {
          val a = Component(1, 2)
          val b = Component(9, 1)
          expect(a.willConnectTo(b)).to.be.`true`
          expect(b.willConnectTo(a)).to.be.`true`
      }

      it("can check it will not connect to another component") {
          val a = Component(4, 2)
          val b = Component(9, 1)
          expect(a.willConnectTo(b)).to.be.`false`
          expect(b.willConnectTo(a)).to.be.`false`
      }

      it("can connect two components") {
          val a = Component(1, 2)
          val b = Component(9, 1)
          b.connectTo(a)
          expect(b.children()).to.equal(listOf(a))
      }

      it("can safely ignore components that won't connect") {
          val a = Component(4, 2)
          val b = Component(9, 1)
          b.connectTo(a)
          expect(b.children()).to.equal(listOf())
      }

      it("can't use the same port twice") {
          val a = Component(1, 2)
          val b = Component(9, 1)
          val extra = Component(1, 4)
          b.connectTo(a)
          b.connectTo(extra)
          expect(b.children()).to.equal(listOf(a))
      }

      it("can't use the child port twice") {
          val a = Component(1, 2)
          val b = Component(9, 1)
          val c = Component(1, 4)
          a.connectTo(b)
          b.connectTo(c)
          expect(b.children()).to.equal(listOf())
          expect(a.children()).to.equal(listOf(b))
      }

      it("can find start components") {
          val components = Component.parse("3/9\n" +
                                             "0/6\n" + "3/0")
          val starters = Component.findStartComponents(components)
          expect(starters).to.equal(listOf(Component(0, 6), Component(3, 0)))
      }
  })


data class Component(val portOne: Int, val portTwo: Int) {

    private var portOneIsOpen = true
    private var portTwoIsOpen = true

    companion object {
        fun parse(description: String): List<Component> {
            return parse(description.split("\n"))
        }

        private fun parse(descriptions: List<String>) =
          descriptions.map { it.split("/") }.map { Component(it[0].toInt(), it[1].toInt()) }

        fun findStartComponents(components: List<Component>) =
          components.filter { it.portOne == 0 || it.portTwo == 0 }
    }

    fun willConnectTo(other: Component): Boolean {
        val portOneCanConnect = portOneCanConnect(other)
        val portTwoCanConnect = portTwoCanConnect(other)
        return portOneCanConnect || portTwoCanConnect
    }

    private fun portTwoCanConnect(other: Component): Boolean {
        return portTwoIsOpen &&
          (portTwo == other.portOne
            || portTwo == other.portTwo)
    }

    private fun portOneCanConnect(other: Component) =
      portOneIsOpen && (portOne == other.portOne
        || portOne == other.portTwo)

    private val children = mutableListOf<Component>()
    fun children() = children.toList()

    fun connectTo(other: Component) {
        if (portOneCanConnect(other)) {
            portOneIsOpen = false
            children.add(other)
            other.connecting(portOne)
        } else if(portTwoCanConnect(other)) {
            portTwoIsOpen = false
            children.add(other)
            other.connecting(portTwo)
        }
    }

    private fun connecting(otherPort: Int) {
        when {
            portOne == otherPort -> portOneIsOpen = false
            portTwo == otherPort -> portTwoIsOpen = false
            else -> Exception("can't connect $otherPort when my ports are $portOne and $portTwo")
        }
    }
}