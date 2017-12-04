package com.pauldambra.adventofcode2017.day4

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on


object Day4Tests : Spek({
    describe("day 4") {
        on("part one") {
            it("aa bb cc dd ee is valid"){
                expect("aa bb cc dd ee".isValid()).to.be.`true`
            }
            it("aa bb cc dd aa is not valid - the word aa appears more than once"){
                expect("aa bb cc dd aa".isValid()).to.be.`false`
            }
            it("aa bb cc dd aaa is valid - aa and aaa count as different words."){
                expect("aa bb cc dd aaa".isValid()).to.be.`true`
            }
            it("can validate lines in the input") {
                val validPassPhrases = this::class.java
                        .getResource("/passphrases.txt")
                        .readText()
                        .split("\n")
                        .map { it.isValid() }
                        .filter { it }
                        .count()

                println("there are $validPassPhrases valid passphrases")
            }
        }
        on("part two") {
            it("abcde fghij is a valid passphrase.") {
                expect("abcde fghij".isAnagramValid()).to.be.`true`
            }
            it("abcde xyz ecdab is not valid - the letters from the third word can be rearranged to form the first word.") {
                expect("abcde xyz ecdab".isAnagramValid()).to.be.`false`
            }
            it("a ab abc abd abf abj is a valid passphrase, because all letters need to be used when forming another word.") {
                expect("a ab abc abd abf abj".isAnagramValid()).to.be.`true`
            }
            it("iiii oiii ooii oooi oooo is valid.") {
                expect("iiii oiii ooii oooi oooo".isAnagramValid()).to.be.`true`
            }
            it("oiii ioii iioi iiio is not valid - any of these words can be rearranged to form any other word.") {
                expect("oiii ioii iioi iiio".isAnagramValid()).to.be.`false`
            }
            it("can validate lines in the input") {
                val validPassPhrases = this::class.java
                        .getResource("/passphrases.txt")
                        .readText()
                        .split("\n")
                        .map { it.isAnagramValid() }
                        .filter { it }
                        .count()

                println("there are $validPassPhrases valid anagram passphrases")
            }
        }

    }
})

private fun String.isAnagramValid() : Boolean {
    val tokens = this.split(" ").map { it.toCharArray() }.map { it.sort(); it }.map { it.joinToString("") }
    return tokens.distinct().size == tokens.size
}

private fun String.isValid() : Boolean {
    val tokens = this.split(" ")
    return tokens.distinct().size == tokens.size
}
