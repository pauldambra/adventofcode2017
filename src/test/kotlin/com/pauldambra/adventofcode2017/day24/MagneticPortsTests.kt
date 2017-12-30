package com.pauldambra.adventofcode2017.day24

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import java.util.*

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

//object MagneticPortsTests : Spek(
//  {
//      it("can parse a component") {
//          val c = Component.parse("0/10").single()
//          expect(c.portOne).to.equal(0)
//          expect(c.portTwo).to.equal(10)
//      }
//
//      it("can parse many components from a list") {
//          val components = Component.parse("3/9\n" +
//                                             "0/6")
//          expect(components).to.equal(listOf(Component(3, 9), Component(0, 6)))
//      }
//
//      it("can check if it will connect to another component - i.e. has a matching port") {
//          val a = Component(1, 2)
//          val b = Component(9, 1)
//          expect(a.willConnectTo(b)).to.be.`true`
//          expect(b.willConnectTo(a)).to.be.`true`
//      }
//
//      it("can check it will not connect to another component") {
//          val a = Component(4, 2)
//          val b = Component(9, 1)
//          expect(a.willConnectTo(b)).to.be.`false`
//          expect(b.willConnectTo(a)).to.be.`false`
//      }
//
//      it("can connect two components") {
//          val a = Component(1, 2)
//          val b = Component(9, 1)
//          b.connectTo(a)
//          expect(b.children()).to.equal(listOf(a))
//      }
//
//      it("can safely ignore components that won't connect") {
//          val a = Component(4, 2)
//          val b = Component(9, 1)
//          b.connectTo(a)
//          expect(b.children()).to.equal(listOf())
//      }
//
//      it("can't use the same port twice") {
//          val a = Component(1, 2)
//          val b = Component(9, 1)
//          val extra = Component(1, 4)
//          b.connectTo(a)
//          b.connectTo(extra)
//          expect(b.children()).to.equal(listOf(a))
//      }
//
//      it("doesn't connect zeroes together") {
//          val a = Component(0, 2)
//          val b = Component(0, 1)
//          a.connectTo(b)
//          expect(a.children()).to.equal(emptyList())
//      }
//
//      it("can't use the child port twice") {
//          val a = Component(1, 2)
//          val b = Component(9, 1)
//          val c = Component(1, 4)
//          a.connectTo(b)
//          b.connectTo(c)
//          expect(b.children()).to.equal(listOf())
//          expect(a.children()).to.equal(listOf(b))
//      }
//
//      it("can find start components") {
//          val components = Component.parse("3/9\n" +
//                                             "0/6\n" + "3/0")
//          val starters = Component.findStartComponents(components)
//          expect(starters).to.equal(listOf(Component(0, 6), Component(3, 0)))
//      }
//
//      it("can find all the possible bridges one level deep") {
//          val components = Component.parse("3/9\n" +
//                                             "0/6\n" + "3/0")
//          // so 0/6
//          // and 0/3 -> 3/9
//
//          Component.findChildren(components)
//          val starters = Component.findStartComponents(components)
//
//          expect(starters[0]).to.equal(Component(0, 6))
//          expect(starters[0].children()).to.equal(emptyList())
//
//          expect(starters[1]).to.equal(Component(3, 0))
//          expect(starters[1].children()).to.equal(listOf(Component(3, 9)))
//      }
//
//      it("can find bridges two levels deep") {
//          val components = listOf(
//            //first bridge
//            Component(0,2),
//            Component(2,4),
//            Component(4,6),
//            //second bridge
//            Component(0,1),
//            Component(1,3)
//          )
//          Component.findChildren(components)
//          val starters = Component.findStartComponents(components)
//
//          expect(starters[0]).to.equal(Component(0, 2))
//          expect(starters[0].children()).to.equal(listOf(Component(2, 4)))
//          expect(starters[0].children().single().children()).to.equal(listOf(Component(4, 6)))
//
//          expect(starters[1]).to.equal(Component(0, 1))
//          expect(starters[1].children().single()).to.equal(Component(1, 3))
//      }
//  })

object DepthFirstBridges : Spek(
  {
      it("can do a single branch") {
          val cs = listOf(Component(0, 1), Component(1, 2))
          val b = BridgeBuilder(cs)
          b.build()
          expect(b.strongestBridge).to.equal(4)
      }

      it("can do a different valued single branch") {
          val cs = listOf(Component(0, 2), Component(1, 2))
          val b = BridgeBuilder(cs)
          b.build()
          expect(b.strongestBridge).to.equal(5)
      }

      it("can do two root branches") {
          val cs = listOf(Component(0, 2), Component(1, 4), Component(0, 1))

          /**
           *            0/0
           *           /   \
           *         0/2   0/1
           *                |
           *                1/4
           */

          val b = BridgeBuilder(cs)
          b.build()
          expect(b.strongestBridge).to.equal(6)
      }

      it("can do split branches within the tree") {
          val cs = listOf(Component(1,6), Component(0, 2), Component(1, 4), Component(0, 1))

          /**
           *            0/0
           *           /   \
           *         0/2   0/1
           *              /   \
           *            1/6    1/4
           */

          val b = BridgeBuilder(cs)
          b.build()
          expect(b.strongestBridge).to.equal(8)
      }

            it("can build the example tree") {
          val components = Component.parse("0/2\n" +
                                             "2/2\n" +
                                             "2/3\n" +
                                             "3/4\n" +
                                             "3/5\n" +
                                             "0/1\n" +
                                             "10/1\n" +
                                             "9/10")
                val b = BridgeBuilder(components)
                b.build()
          // ==

          /*
            0/0-\
          /      \
        0/1       0/2
        |         / \
        10/1     /   \--\
        |       2/3      \
        9/10   /   \      2/2
              3/4  3/5     |
                          2/3
                         /   \
                        3/4   3/5

           */

                expect(b.strongestBridge).to.equal(31)
      }

      it("can build the puzzle input tree") {
          val components = Component.parse(puzzleInput)
          val b = BridgeBuilder(components)
          b.build()

          println("day 24 part 1: strongest bridge is ${b.strongestBridge}")
          println("day 24 part 2: strength of the longest bridge is ${b.longestBridge}")
      }

      it("can not re-use parents in a chain") {
          val cs = listOf(
            Component(1,6),
            Component(6,5),
            Component(5,6),
            Component(0, 2),
            Component(1, 4),
            Component(0, 1))

          /**
           *            0/0
           *           /   \
           *         0/2   0/1
           *              /   \
           *            1/6    1/4
           *            |
           *            6/5
           *            |
           *            6/6 <- 1/6 and 6/5 can't connect here cos they're parents of this node
           */

          val b = BridgeBuilder(cs)
          b.build()
          expect(b.strongestBridge).to.equal(31)
      }
  })

data class Node(val strength: Int, val depth: Int, val component: Component)

class BridgeBuilder(private val components: List<Component>) {
    val root: Component = Component(Port.closed(0), Port.open(0))

    fun build() {

        val stack = ArrayDeque<Node>()
        stack.push(Node(root.strength, 0, root)) //always starts with a component with only one open port...

        while (!stack.isEmpty()) {
            val current = stack.pop()
//            println("current is $current")
            //can any component connect to this node
            val openPort = current.component.openPort()
            if (openPort != null) {
                val availableComponents = components
                  .filter { !it.portsAreEqual(current.component) }
                  .filter { it.hasPort(openPort) }

                if (availableComponents.any()) {
                    val depth = current.depth + 1

                    availableComponents
                      .forEach {
                          val c = it.copyWithClosed(openPort)
                          c.parent = current.component

//                          println("checking if we should add $c as a child")

                          if (connectingDoesNotLeaveANewZeroRootOpen(c)
                            && !c.isAncestorOf(current.component)) {

                              if(depth > components.size + 1) {
                                  throw Exception("ugh, there's some kind of loop. Depth of $depth but only ${components.size} components")
                              }
                              val node = Node(current.strength + c.strength, depth, c)
//                              println("adding child $node")
                              stack.push(node)
                          }
                      }
                } else {
//                    println("no connecting components available")
                    if (current.strength > strongestBridge) strongestBridge = current.strength
                    updateLongestBridge(current)
//                    println("terminating a branch with strongest bridge set as $strongestBridge")
                }
            }
//            println("-----------")
        }
    }

    private fun updateLongestBridge(current: Node) {
        if (current.depth < longestBridge.depth) return

        if (current.depth == longestBridge.depth) {
            if (current.strength > longestBridge.strength) {
                longestBridge = current
            }
        }

        if (current.depth > longestBridge.depth) {
            longestBridge = current
        }
    }

    private fun connectingDoesNotLeaveANewZeroRootOpen(c: Component) =
      c.openPort()!!.pins != 0

    var strongestBridge: Int = 0
        private set

    var longestBridge: Node = Node(0, -1, Component(-1, -1))
        private set

}

data class Port(val pins: Int, val isOpen: Boolean = true) {
    companion object {
        fun closed(pins: Int) = Port(pins, false)

        fun open(pins: Int) = Port(pins, true)
    }
}

//this is just a treenode...
data class Component(val portOne: Port, val portTwo: Port, var parent: Component? = null) {
    constructor(portOne: Int, portTwo: Int) : this(Port(portOne), Port(portTwo))

    companion object {
        fun parse(description: String): List<Component> {
            return description
              .split("\n")
              .map { it.split("/") }
              .map { Component(it[0].toInt(), it[1].toInt()) }
        }
    }

    fun openPort(): Port? {
        return if (portOne.isOpen && portTwo.isOpen) {
            throw Exception("not expecting two open ports $this")
        } else if (portOne.isOpen) {
            portOne
        } else if (portTwo.isOpen) {
            portTwo
        } else {
            println("no open ports! $this")
            null
        }
    }

    fun hasPort(openPort: Port?): Boolean {
        if (openPort == null) return false
        return portOne.pins == openPort.pins || portTwo.pins == openPort.pins
    }

    fun copyWithClosed(port: Port): Component {
        assert(portOne.isOpen && portTwo.isOpen)
        val pins = port.pins
        assert(portOne.pins == pins || portTwo.pins == pins)

        return if (portOne.pins == pins && portTwo.pins == pins) {
            // it doesn't matter which we connect
            val a = portOne.copy(pins = pins, isOpen = false)
            this.copy(portOne = a, portTwo = portTwo)
        } else if (portOne.pins == pins && portTwo.pins != pins) {
            // we are connecting port one
            val a = portOne.copy(pins = pins, isOpen = false)
            this.copy(portOne = a, portTwo = portTwo)
        } else if (portOne.pins != pins && portTwo.pins == pins) {
            // we are connecting port two
            val a = portTwo.copy(pins = pins, isOpen = false)
            this.copy(portOne = portOne, portTwo = a)
        } else {
            throw Exception("can we even get here?!")
        }
    }

    val hasSamePortsPins = portOne.pins == portTwo.pins

    fun portsAreEqual(other: Component): Boolean {
        return other.portOne.pins == portOne.pins && other.portTwo.pins == portTwo.pins
    }

    val strength = portOne.pins + portTwo.pins

    //is _this_ node in the parents of the other one
    fun isAncestorOf(other: Component): Boolean {
        var currentParent = other.parent
        while (currentParent != null) {
            if (portsAreEqual(currentParent)) {
                return true
            }
            currentParent = currentParent.parent
        }
        return false
    }

//    fun printParents() {
//        println("------- printing parents ------")
//
//        var currentParent = parent
//        while (currentParent != null) {
//            println("      |       ")
//            println("$currentParent")
//            currentParent = currentParent.parent
//        }
//
//        println("-------------------------------")
//    }
}