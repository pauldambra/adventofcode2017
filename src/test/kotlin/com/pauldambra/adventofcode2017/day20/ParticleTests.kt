package com.pauldambra.adventofcode2017.day20

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.it
import kotlin.math.absoluteValue


object ParticleTests : Spek({
    it("can parse particles") {
        val input = "p=<2411,-29,-1776>, v=<345,-4,-258>, a=<-25,0,22>"
        val p: Particle = Particle.parse(IndexedValue(0, input))

        val position = GpuCoordinate(2411, -29, -1776)
        val velocity = GpuCoordinate(345, -4, -258)
        val acceleration = GpuCoordinate(-25, 0, 22)
        expect(p).to.equal(Particle(0, position, velocity, acceleration))
    }
    it("sums the position to get the particles manhattan distance") {
        val position = GpuCoordinate(1, 2, 3)
        val velocity = GpuCoordinate(2, 9, 3)
        val acceleration = GpuCoordinate(2, 2, -2)
        val p = Particle(0, position, velocity, acceleration)

        expect(p.manhattanDistance()).to.equal(6)
    }
    context("properties update on tick") {
        it("Increases the X velocity by the X acceleration first.") {
            val position = GpuCoordinate(0, 0, 0)
            val velocity = GpuCoordinate(2, 0, 0)
            val acceleration = GpuCoordinate(2, 0, 0)
            val p = Particle(0, position, velocity, acceleration)

            p.tick()

            expect(p.velocity.x).to.equal(4)
        }
        it("Increases the Y velocity by the Y acceleration second.") {
            val position = GpuCoordinate(0, 0, 0)
            val velocity = GpuCoordinate(2, 9, 0)
            val acceleration = GpuCoordinate(2, 2, 0)
            val p = Particle(0, position, velocity, acceleration)

            p.tick()

            expect(p.velocity.x).to.equal(4)
            expect(p.velocity.y).to.equal(11)

        }
        it("Increases the Z velocity by the Z acceleration third.") {
            val position = GpuCoordinate(0, 0, 0)
            val velocity = GpuCoordinate(2, 9, 3)
            val acceleration = GpuCoordinate(2, 2, -2)
            val p = Particle(0, position, velocity, acceleration)

            p.tick()

            expect(p.velocity.x).to.equal(4)
            expect(p.velocity.y).to.equal(11)
            expect(p.velocity.z).to.equal(1)
        }
        it("Increases the X position by the X velocity fourth.") {
            val position = GpuCoordinate(0, 0, 0)
            val velocity = GpuCoordinate(2, 9, 3)
            val acceleration = GpuCoordinate(2, 2, -2)
            val p = Particle(0, position, velocity, acceleration)

            p.tick()

            expect(p.position.x).to.equal(4)
        }
        it("Increases the Y position by the Y velocity fifth.") {
            val position = GpuCoordinate(0, 0, 0)
            val velocity = GpuCoordinate(2, 9, 3)
            val acceleration = GpuCoordinate(2, 2, -2)
            val p = Particle(0, position, velocity, acceleration)

            p.tick()

            expect(p.position.x).to.equal(4)
            expect(p.position.y).to.equal(11)
        }
        it("Increases the Z position by the Z velocity last.") {
            val position = GpuCoordinate(0, 0, 0)
            val velocity = GpuCoordinate(2, 9, 3)
            val acceleration = GpuCoordinate(2, 2, -2)
            val p = Particle(0, position, velocity, acceleration)

            p.tick()

            expect(p.position.x).to.equal(4)
            expect(p.position.y).to.equal(11)
            expect(p.position.z).to.equal(1)
        }
    }
})

object GpuBufferTests : Spek({
    it("can have a single particle") {
        val buffer = GpuBuffer.parse("p=<0,0,0>, v=<0,0,0>, a=<0,0,0>")
        expect(buffer.particles.count()).to.equal(1)
    }

    it("can have multiple particles") {
        val buffer = GpuBuffer.parse("p=<0,0,0>, v=<0,0,0>, a=<0,0,0>\np=<0,0,0>, v=<0,0,0>, a=<0,0,0>")
        expect(buffer.particles.count()).to.equal(2)
    }

    it("can find closest particle") {
        val uninterestingBit = ", v=< 2,0,0>, a=<-1,0,0>"
        val description = "p=< 3,0,0>$uninterestingBit\n" +
                "p=< 4,0,0>$uninterestingBit"
        val buffer = GpuBuffer.parse(description)
        expect(buffer.closestParticle()).to.equal(0)

        val description2 = "p=< 3,2,2>$uninterestingBit\n" +
                "p=< 2,1,1>$uninterestingBit"
        val buffer2 = GpuBuffer.parse(description2)
        expect(buffer2.closestParticle()).to.equal(1)
    }

    it("can tick all particles") {
        val description = "p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>\n" +
                "p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>"
        val buffer = GpuBuffer.parse(description)

        expect(buffer.closestParticle()).to.equal(0)
        buffer.tick()
        expect(buffer.closestParticle()).to.equal(1)
    }

    it("has particle 0 stay closest in the long term for the example input") {
        val description = "p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>\n" +
                "p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>"
        val buffer = GpuBuffer.parse(description)

        repeat(10_000) {buffer.tick()}

        expect(buffer.closestParticle()).to.equal(0)
    }

    it("can find out which particle stays closest in the puzzle input") {
        val description = this::class.java
            .getResource("/day20.txt")
            .readText()

        val buffer = GpuBuffer.parse(description)

        repeat(1000) {
            buffer.tick()
            buffer.closestParticle()
        }

//        buffer.printPositions()
        val closestParticle = buffer.closestParticle()
        expect(closestParticle).to.be.below(518)
        expect(closestParticle).to.be.above(366)
        expect(closestParticle).to.equal(457)
        println("day 20 part 1: particle that stays closest to 0 is $closestParticle")

    }

    it("can find out how many particles are removed in collisions") {
        val description = this::class.java
                .getResource("/day20.txt")
                .readText()

        val buffer = GpuBuffer.parse(description)

        repeat(1000) {
            buffer.tick(true)
            buffer.closestParticle()
        }

        val remainingParticles = buffer.particles.count()
        println("day 20 part 2: particles left after all collisions are resolved = $remainingParticles")
    }
})

class GpuBuffer(var particles: MutableList<Particle>) {
    companion object {
        fun parse(particleDescriptions: String) =
                GpuBuffer(particleDescriptions.split("\n").withIndex().map(Particle.Companion::parse).toMutableList())
    }

    fun closestParticle() = particles.minBy { it.manhattanDistance() }!!.index

    fun tick(removeCollidingParticles:Boolean = false) {
        particles.forEach { it.tick() }

        if (removeCollidingParticles) {
            val gatherParticles = mutableMapOf<GpuCoordinate, MutableList<Particle>>()
            particles.forEach {
                if(!gatherParticles.containsKey(it.position)) {
                    gatherParticles.put(it.position, mutableListOf())
                }
                gatherParticles[it.position]!!.add(it)
            }

            gatherParticles
                    .filter { it.value.count() > 1 }
                    .values
                    .flatten()
                    .forEach { particles.remove(it) }
        }

    }

    fun printPositions() {
        particles.forEach {
            println("${it.position} is ${it.manhattanDistance()}")
        }
    }
}

data class GpuCoordinate(var x: Long, var y: Long, var z: Long) {
    var manhattanDistance = x.absoluteValue + y.absoluteValue + z.absoluteValue
    var startedMovingAway = false

    companion object {
        fun parse(description: List<String>) = GpuCoordinate(description[0].toLong(), description[1].toLong(), description[2].toLong())
    }

    private var tickCount = 0
    fun tick() {
        tickCount++

        val md = x.absoluteValue + y.absoluteValue + z.absoluteValue
        if (manhattanDistance < md) {
            startedMovingAway = true
        }

        if (startedMovingAway && md < manhattanDistance && tickCount > 1000) {
            throw Exception("this tick has distance of $md against current of $manhattanDistance but should not be getting closer")
        }

        manhattanDistance = md
    }
}

data class Particle(val index: Int, val position: GpuCoordinate, val velocity: GpuCoordinate, private val acceleration: GpuCoordinate) {
    companion object {
        fun parse(description: IndexedValue<String>): Particle {
            val regex = Regex("(-?\\d+,-?\\d+,-?\\d+)")
            val s = regex.findAll(description.value).toList().map { it.groups.first()?.value }.map { it?.split(",") }
            val position = GpuCoordinate.parse(s[0]!!)
            val velocity = GpuCoordinate.parse(s[1]!!)
            val acceleration = GpuCoordinate.parse(s[2]!!)

            return Particle(description.index, position, velocity, acceleration)
        }
    }

    fun tick() {
        velocity.x += acceleration.x
        velocity.y += acceleration.y
        velocity.z += acceleration.z
        position.x += velocity.x
        position.y += velocity.y
        position.z += velocity.z

        position.tick()
    }

    fun manhattanDistance() = position.manhattanDistance
}