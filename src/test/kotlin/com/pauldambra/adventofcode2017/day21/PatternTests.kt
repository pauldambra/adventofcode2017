package com.pauldambra.adventofcode2017.day21

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.it


object PatternTests : Spek(
  {
      it("can report its size") {
          val pattern = Pattern.parse(".#.\n" +
                                        "..#\n" +
                                        "###")
          expect(pattern.size).to.equal(3)
      }

      context("splitting patterns") {
          it("can split a 2x2 square into 1 split") {
              val p = Pattern.parse("12\n34")
              val s = p.split()
              expect(s.count()).to.equal(1)
          }

          it("can split a 4x4 square into 4 splits") {
              val p = Pattern.parse("1234\n" +
                                      "5678\n" +
                                      "9abc\n" +
                                      "defg")
              val s = p.split()
              val expected = listOf(
                Pattern.parse("12\n56"),
                Pattern.parse("34\n78"),
                Pattern.parse("9a\nde"),
                Pattern.parse("bc\nfg")
              )
              expect(s).to.equal(expected)
          }

          it("can split a 3x3 square into 1 split") {
              val threeByThree = "123\n" +
                "456\n" +
                "789"
              val p = Pattern.parse(threeByThree)
              val s = p.split()
              expect(s.count()).to.equal(1)
              expect(s).to.equal(listOf(Pattern.parse(threeByThree)))
          }

          it("can split a 6x6 square into 4 splits") {
              val p = Pattern.parse("123456\n" +
                                      "789abc\n" +
                                      "defghi\n" +
                                      "jklmno\n" +
                                      "pqrstu\n" +
                                      "vwxyz!"
              )
              val s = p.split()
              val expected = listOf(
                Pattern.parse("123\n789\ndef"),
                Pattern.parse("456\nabc\nghi"),
                Pattern.parse("jkl\npqr\nvwx"),
                Pattern.parse("mno\nstu\nyz!")
              )
              s.withIndex().forEach {
                  expect(it.value).to.equal(expected[it.index])
              }
          }

          /*
          2x2 splits to 1 size 2
          3x3 splits to 1 size 3

          4x4 splits to 4 size 2
          6x6 splits to 9 size 2
          8x8 splits to 12 size 2
          9x9 splits to 9 size 3
          10x10 splits to 25 size 2
          12x12 splits to 30 size 2

            */

          it("can combine a 4x4 split") {
              val split = listOf(
                Pattern.parse("12\n78"),
                Pattern.parse("34\n9a"),
                Pattern.parse("de\njk"),
                Pattern.parse("fg\nlm")
              )
              val combinedPattern = Pattern.combine(split)
              expect(combinedPattern).to.equal(Pattern.parse(
                "1234\n" +
                  "789a\n" +
                  "defg\n" +
                  "jklm"
              ))
          }

          it("can combine a 6x6 split") {
              val split = listOf(
                Pattern.parse("12\n78"),
                Pattern.parse("34\n9a"),
                Pattern.parse("56\nbc"),
                Pattern.parse("de\njk"),
                Pattern.parse("fg\nlm"),
                Pattern.parse("hi\nno"),
                Pattern.parse("pq\nvw"),
                Pattern.parse("rs\nxy"),
                Pattern.parse("tu\nz!")
              )
              val combinedPattern = Pattern.combine(split)
              expect(combinedPattern).to.equal(Pattern.parse(
                "123456\n" +
                  "789abc\n" +
                  "defghi\n" +
                  "jklmno\n" +
                  "pqrstu\n" +
                  "vwxyz!"
              ))
          }

          it("can combine a 8x8 split") {
              val split = listOf(
                Pattern.parse("12\n9a"),
                Pattern.parse("34\nbc"),
                Pattern.parse("56\nde"),
                Pattern.parse("78\nfg"),
                Pattern.parse("hi\npq"),
                Pattern.parse("jk\nrs"),
                Pattern.parse("lm\ntu"),
                Pattern.parse("no\nvw"),
                Pattern.parse("xy\n^&"),
                Pattern.parse("z!\n*("),
                Pattern.parse("@£\n)_"),
                Pattern.parse("$%\n+{")
              )
              val combinedPattern = Pattern.combine(split)
              expect(combinedPattern).to.equal(Pattern.parse(
                "12345678\n" +
                  "9abcdefg\n" +
                  "hijklmno\n" +
                  "pqrstuvw\n" +
                  "xyz!@£$%\n" +
                  "^&*()_+{"
              ))
          }
      }
  })


class Pattern constructor(private val patternGrid: List<List<String>>) {
    class GridMustBeDivisibleByTwoOrThree(size: Int) : Throwable("grid size is $size")

    companion object {
        fun parse(description: String): Pattern {
            return Pattern(description.split("\n").map { it.toCharArray().map { it.toString() } })

        }

        private fun splitSizeForGrid(gridSize: Int)
          = when {
            gridSize % 3 == 0 -> 3
            gridSize % 2 == 0 -> 2
            else              -> throw GridMustBeDivisibleByTwoOrThree(gridSize)
        }

        private fun fangleChunks(chunks: List<List<List<String>>>, splitSize: Int): MutableList<List<List<String>>> {
            val x = mutableListOf<List<List<String>>>()
            for (i in 0 until chunks.size step splitSize) {
                println("when i is $i")
                val a = chunks[i]
                val b = chunks[i + 1]
                println("a is $a")
                println("b is $b")

                var c: List<List<String>>? = null
                if (splitSize == 3) {
                    c = chunks[i + 2]
                    println("c is $c")
                }

                (0 until a.size).mapTo(x) {
                    val grid = listOf(a[it], b[it])
                    if (c == null) grid else grid + listOf(c[it])
                }
            }
            return x
        }

        fun combine(patterns: List<Pattern>): Pattern {
            if (patterns.size == 1) {
                return patterns.single()
            }

            val splitSize = splitSizeForGrid(patterns.size)
            println("combining a split with size $splitSize")

            val x = mutableListOf<List<String>>()
            for (i in 0 until patterns.size step splitSize) {
                println("patterns[i] size is ${patterns[i].size}")
                println("there are ${patterns.size} patterns")
                val a = patterns[i].patternGrid
                val b = patterns[i + 1].patternGrid

                var c: List<List<String>>? = null
                if (splitSize == 3) {
                    c = patterns[i + 2].patternGrid
                    println("c is $c")
                }

                (0 until a.size).mapTo(x) {
                    val grid = a[it] + b[it]
                    if (c == null) grid else grid + c[it]
                }
            }
            return Pattern(x.toList())
        }

    }

    //assumes it's a square
    val size = patternGrid.count()

    fun split(): List<Pattern> {

        val splitSize = splitSizeForGrid(patternGrid[0].size)

        val chunks = patternGrid.map { it.chunked(splitSize) }

        return fangleChunks(chunks, splitSize).map(::Pattern)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pattern

        if (patternGrid != other.patternGrid) return false

        return true
    }


    override fun hashCode(): Int {
        return patternGrid.hashCode()
    }

    override fun toString(): String {
        return "Pattern(patternGrid=${patternGrid.joinToString("/")})"
    }


}
