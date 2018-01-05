package com.pauldambra.adventofcode2017.day21

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.it

val startingGrid =
  ".#.\n" +
    "..#\n" +
    "###"

val line = ".#./..#/###"

object PixelArtTests : Spek(
  {
      it("can convert grids to lines for the pixel art") {
          expect(startingGrid.convertToLine()).to.equal(line)
      }

      it("can replace grids by matching against patterns") {
          val replacementPattern = "#..#/..../..../#..#"
          val patterns = mapOf(line to replacementPattern)
          expect(startingGrid.replaceFrom(patterns)).to.equal(replacementPattern)
      }

      it("can rotate grids") {
          val rotatedLine = "#../#.#/##."
          expect(line.rotate()).to.equal(rotatedLine)
      }

      it("can generate all the possible rotations of the starting grid") {
          val expected = listOf("#../#.#/##.",
                                "###/#../.#.",
                                ".##/#.#/..#",
                                ".#./..#/###",
                                ".#./#../###",
                                "###/..#/.#.",
                                "..#/#.#/.##",
                                "##./#.#/#..")

          val newLineDelimited = startingGrid.generateRotatedGrids()
          expected.forEach {
              expect(newLineDelimited.contains(it)).to.be.`true`
          }

          val slashDelimited = line.generateRotatedGrids()
          expected.forEach {
              expect(slashDelimited.contains(it)).to.be.`true`
          }
      }

      context("generate art") {
          it("can take the first step") {
              val rules = mapOf(
                "../.#" to "##./#../...",
                ".#./..#/###" to "#..#/..../..../#..#"
              )

              val final = startingGrid.generateArt(rules, 2)
              expect(final).to.equal("##.##./#..#../....../##.##./#..#../......")

              expect(final.pixelsOn()).to.equal(12)
          }

          it("can generate art for the puzzle input") {
              val rules = this::class.java
                .getResource("/day21.txt")
                .readText()
                .split("\n")
                .map { it.split(" => ") }
                .map { Pair(it[0], it[1]) }
                .toMap()

              val final = startingGrid.generateArt(rules, 5)
              expect(final.pixelsOn()).not.to.equal(10)
              println("day 21 part 1: there are ${final.pixelsOn()} pixels on after 5 iterations")
          }
      }

      context("can match flipped and rotated patterns") {
          val replacementPattern = "#..#/..../..../#..#"
          val patterns = mapOf(Pair(line, replacementPattern))
          it("can match original pattern") {
              val unchanged =
                ".#.\n" +
                  "..#\n" +
                  "###"


              expect(unchanged.replaceFrom(patterns)).to.equal(replacementPattern)
          }
          it("can match flipped pattern") {
              val flipped =
                ".#.\n" +
                  "#..\n" +
                  "###"

              expect(flipped.replaceFrom(patterns)).to.equal(replacementPattern)
          }
          it("can match rotated right pattern") {
              val rotatedRight =
                "#..\n" +
                  "#.#\n" +
                  "##."

              expect(rotatedRight.replaceFrom(patterns)).to.equal(replacementPattern)
          }
          it("can match upside down pattern") {
              val upsideDown =
                "###\n" +
                  "..#\n" +
                  ".#."

              expect(upsideDown.replaceFrom(patterns)).to.equal(replacementPattern)
          }
      }

      context("can split grids") {
          it("can split the starting grid") {
              val splat = startingGrid.splitGrid()
              expect(splat).to.have.elements(line)
          }

          it("can split a larger grid") {
              val splat = ("#..#\n" +
                "....\n" +
                "....\n" +
                "#..#").splitGrid()
              expect(splat).to.have.elements("#./..", ".#/..", "../#.", "../.#")
          }

          it("can split an even larger grid") {
              val splat = (
                "##.##.\n" +
                  "#..#..\n" +
                  "......\n" +
                  "##.##.\n" +
                  "#..#..\n" +
                  "......").splitGrid()
              expect(splat).to.have.elements(
                "##/#.",
                ".#/.#",
                "#./..",
                "../##",
                "../.#",
                "../#.",
                "#./..",
                ".#/..",
                "../.."
              )
          }

          it("can correctly split a 9x9 grid") {
              val start = "...#....#/.###.#.../..#..#..#/........./..##..#.#/...#.##.."
              val expected = listOf(
                ".../.##/..#",
                "#../#.#/..#",
                "..#/.../..#",
                ".../..#/...",
                ".../#../#.#",
                ".../#.#/#.."
              )

              val actual = start.splitGrid()

              expect(actual).to.equal(expected)
          }
      }

  })

private fun String.pixelsOn() = this.count { it == '#' }

private fun String.generateArt(rules: Map<String, String>, iterations: Int): String {
    var next = this.convertToLine()
    repeat(iterations) {
        val splat = next.splitGrid()
//        println("splats to $splat")

        val enhancedSquares = splat.map { it.replaceFrom(rules) }.toList()

        next = combineEnhancedSquares(enhancedSquares)

//        println("after replacement we have $next")
    }


    return next
}

private fun combineEnhancedSquares(enhancedSquares: List<String>): String {
    return if (enhancedSquares.count() > 1) {
        val numberOfBlocks = enhancedSquares.count()
        val squareSide = if (numberOfBlocks % 2 == 0) 2 else 3

        val x = mutableListOf<String>()
        (0 until enhancedSquares.count() step squareSide)
          .map { i -> enhancedSquares.subList(i, i + squareSide).map { it.split("/") } }
          .forEach { thisBit ->
              (0 until thisBit.first().count()).mapTo(x) { j -> thisBit.joinToString("") { it[j] } }
          }
        x.joinToString("/")
    } else {
        enhancedSquares.single()
    }
}


private fun String.splitGrid(): List<String> {
    val separator = determineSeparator()
    val rows = this.split(separator)

    if (rows.count() == 2 || rows.count() == 3) return listOf(this.convertToLine())

//    println("rows $rows")

    val gridSize = gridSize(rows)

//    println("gridsize: $gridSize")

    val x = mutableListOf<String>()
    for (i in 0 until rows.count() step gridSize) {
        val a = rows[i]
        val b = rows[i + 1]

        val c: String? = if (gridSize == 3) {
            rows[i+2]
        } else {
            null
        }

        (0 until a.count() step gridSize).mapTo(x) {
            var part = a.substring(it, it + gridSize) + "/" + b.substring(it, it + gridSize)

            if (c != null) {
                part += "/" + c.substring(it, it + gridSize)
            }

            part
        }
    }
    return x.toList()
}

private fun gridSize(rows: List<String>): Int {
    val rowCount = rows[0].count()
    if (rowCount % 2 == 0) return 2
    if (rowCount % 3 == 0) return 3
    throw Exception("wait, what?! $rowCount")
}

private fun String.rotate(): String {
    val separator = determineSeparator()

    val rows = this.split(separator)

    val rotated = mutableListOf<List<String>>()
    rows.first().forEachIndexed { index, _ ->
        val cols = rows.map { it[index].toString() }
        rotated += cols.reversed()
    }
    return rotated.joinToString(separator) { it.joinToString("") }
}

private fun String.determineSeparator() = if (this.contains('/')) "/" else "\n"

private fun String.generateRotatedGrids(): List<String> {
    val s = this.convertFromLine()
    val unflippedOptions = s.allRotations()
    val flippedOptions = s.split("\n").joinToString("\n") { it.reversed() }.allRotations()
    return unflippedOptions + flippedOptions
}

private fun String.replaceFrom(patterns: Map<String, String>): String {
//    println("orig: $this")
    val rotatedGrids = this.generateRotatedGrids()
    val matched = rotatedGrids.find { patterns.containsKey(it) }
    return if (matched != null) {
        val match = patterns[matched]!!
//        println("enhanced: $match")
        match
    } else {
        this
    }
}

private fun String.allRotations(): List<String> {
    val results = mutableListOf<String>()
    var rotatedString = this

    repeat(4) {
        rotatedString = rotatedString.rotate()
        results.add(rotatedString)
    }
    return results.map(String::convertToLine)
}

private fun String.convertToLine(): String = this.replace('\n', '/')
private fun String.convertFromLine(): String = this.replace('/', '\n')


