package com.pauldambra.adventofcode2017.day25

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.it


/**
 *
Begin in state A.
Perform a diagnostic checksum after 12302209 steps.

In state A:
If the current value is 0:
- Write the value 1.
- Move one slot to the right.
- Continue with state B.
If the current value is 1:
- Write the value 0.
- Move one slot to the left.
- Continue with state D.

In state B:
If the current value is 0:
- Write the value 1.
- Move one slot to the right.
- Continue with state C.
If the current value is 1:
- Write the value 0.
- Move one slot to the right.
- Continue with state F.

In state C:
If the current value is 0:
- Write the value 1.
- Move one slot to the left.
- Continue with state C.
If the current value is 1:
- Write the value 1.
- Move one slot to the left.
- C ontinue with state A.

In state D:
If the current value is 0:
- Write the value 0.
- Move one slot to the left.
- Continue with state E.
If the current value is 1:
- Write the value 1.
- Move one slot to the right.
- Continue with state A.

In state E:
If the current value is 0:
- Write the value 1.
- Move one slot to the left.
- Continue with state A.
If the current value is 1:
- Write the value 0.
- Move one slot to the right.
- Continue with state B.

In state F:
If the current value is 0:
- Write the value 0.
- Move one slot to the right.
- Continue with state C.
If the current value is 1:
- Write the value 0.
- Move one slot to the right.
- Continue with state E.
 */

val puzzleRules: Map<Char, Map<Int, Rule>> = mapOf(
  'A' to mapOf(
    0 to Rule(1, 1, 'B'),
    1 to Rule(0, -1, 'D')
  ),
  'B' to mapOf(
    0 to Rule(1, 1, 'C'),
    1 to Rule(0, 1, 'F')
  ),
  'C' to mapOf(
    0 to Rule(1, -1, 'C'),
    1 to Rule(1, -1, 'A')
  ),
  'D' to mapOf(
    0 to Rule(0, -1, 'E'),
    1 to Rule(1, 1, 'A')
  ),
  'E' to mapOf(
    0 to Rule(1, -1, 'A'),
    1 to Rule(0, 1, 'B')
  ),
  'F' to mapOf(
    0 to Rule(0, 1, 'C'),
    1 to Rule(0, 1, 'E')
  )
)

object TuringMachineTests : Spek(
  {

      context("with the puzzle input rules") {
          val machine = TuringMachine(puzzleRules)
          repeat(12302209) {machine.step()}
          val diagnostic = machine.diagnosticChecksum()
          print("Day 25 part 1: diagnostic context is $diagnostic")
      }

      context("with the example rules") {


          /*
          Begin in state A.
      Perform a diagnostic checksum after 6 steps.

      In state A:
        If the current value is 0:
          - Write the value 1.
          - Move one slot to the right.
          - Continue with state B.
        If the current value is 1:
          - Write the value 0.
          - Move one slot to the left.
          - Continue with state B.

      In state B:
        If the current value is 0:
          - Write the value 1.
          - Move one slot to the left.
          - Continue with state A.
        If the current value is 1:
          - Write the value 1.
          - Move one slot to the right.
          - Continue with state A.
           */

          val rules: Map<Char, Map<Int, Rule>> = mapOf(
            'A' to mapOf(
              0 to Rule(1, 1, 'B'),
              1 to Rule(0, -1, 'B')
            ),
            'B' to mapOf(
              0 to Rule(1, -1, 'A'),
              1 to Rule(1, 1, 'A')
            )
          )

          it("can have a tape with no actions") {
              val machine = TuringMachine(rules)

              expect(machine.state).to.equal('A')
              expect(machine.tape[0]).to.equal(0)
              expect(machine.cursor).to.equal(0)
          }

          it("can follow the first rule in state A") {
              val machine = TuringMachine(rules)

              machine.step()

              expect(machine.state).to.equal('B')
              expect(machine.tape[0]).to.equal(1)
              expect(machine.cursor).to.equal(1)
          }

          it("can take 2 steps") {
              val machine = TuringMachine(rules)

              machine.step()
              machine.step()

              expect(machine.tape[0]).to.equal(1)
              expect(machine.tape[1]).to.equal(1)
              expect(machine.cursor).to.equal(0)
              expect(machine.state).to.equal('A')
          }

          it("can take 3 steps") {
              val machine = TuringMachine(rules)

              machine.step()
              machine.step()
              machine.step()

              expect(machine.tape[0]).to.equal(0)
              expect(machine.tape[1]).to.equal(1)
              expect(machine.cursor).to.equal(-1)
              expect(machine.state).to.equal('B')
          }

          it("can take 4 steps") {
              val machine = TuringMachine(rules)

              repeat(4) { machine.step() }

              expect(machine.tape[-1]).to.equal(1)
              expect(machine.tape[0]).to.equal(0)
              expect(machine.tape[1]).to.equal(1)
              expect(machine.cursor).to.equal(-2)
              expect(machine.state).to.equal('A')
          }

          it("can take 5 steps") {
              val machine = TuringMachine(rules)

              repeat(5) { machine.step() }

              expect(machine.tape[-2]).to.equal(1)
              expect(machine.tape[-1]).to.equal(1)
              expect(machine.tape[0]).to.equal(0)
              expect(machine.tape[1]).to.equal(1)
              expect(machine.cursor).to.equal(-1)
              expect(machine.state).to.equal('B')
          }

          it("can take 6 steps") {
              val machine = TuringMachine(rules)

              repeat(6) { machine.step() }

              expect(machine.tape[-2]).to.equal(1)
              expect(machine.tape[-1]).to.equal(1)
              expect(machine.tape[0]).to.equal(0)
              expect(machine.tape[1]).to.equal(1)
              expect(machine.cursor).to.equal(0)
              expect(machine.state).to.equal('A')

              expect(machine.diagnosticChecksum()).to.equal(3)
          }
      }
  })

data class Rule(val newValue: Int, val changeCursorBy: Int, val nextState: Char)

class TuringMachine(private val rules: Map<Char, Map<Int, Rule>>) {
    var cursor = 0

    var state = 'A'
        private set

    val tape = mutableMapOf(0 to 0)

    fun step() {
        val current = tape.getOrElse(cursor) { 0 }
        val rule = rules[state]!![current]!!
        tape.put(cursor, rule.newValue)
        state = rule.nextState
        cursor += rule.changeCursorBy
    }

    fun diagnosticChecksum(): Int = tape.values.sum()

}