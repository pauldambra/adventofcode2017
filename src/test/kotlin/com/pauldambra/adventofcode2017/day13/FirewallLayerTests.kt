package com.pauldambra.adventofcode2017.day13

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

object FirewallLayerTests : Spek({
    it("can create a firewall layer") {
        expect(Layer("0: 3")).to.equal(Layer(0, 3))
    }
    it("can create two consecutive layers from a description of the firewall") {
        val layers: List<FirewallLayer> = Layer.parse(
                "0: 3\n" +
                "1: 2")
        expect(layers).to.equal(listOf(Layer(0, 3), Layer(1, 2)))
    }
    it("fills in empty layers when description has gaps in the layers") {
        val layers: List<FirewallLayer> = Layer.parse(
                "0: 4\n" +
                "3: 3")
        expect(layers).to.equal(listOf(Layer(0, 4), EmptyLayer(1), EmptyLayer(2), Layer(3, 3)))
    }
})

interface FirewallLayer

data class EmptyLayer(val depth: Int) : FirewallLayer

data class Layer(val depth: Int, val range: Int) : FirewallLayer {
    companion object {
        fun parse(description: String): List<FirewallLayer> {
            val providedLayers = description
                    .split("\n")
                    .map { Layer(it) }
                    .associateBy( { it.depth }, {it} )

            val depthsRange = providedLayers.keys.min()!!.rangeTo(providedLayers.keys.max()!!)

            return depthsRange.fold(emptyList()) { layers, depth ->
                layers + providedLayers.getOrDefault(depth, EmptyLayer(depth))
            }
        }
    }

    constructor(description: String) : this(
            description.split(": ")[0].toInt(),
            description.split(": ")[1].toInt())

}
