package com.pauldambra.adventofcode2017.day13

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it

object LayerParsingTests : Spek({

    it("can create a firewall layer") {
        expect(CalculatingLayer("0: 3")).to.equal(CalculatingLayer(0, 3))
    }
    it("can create two consecutive layers from a description of the firewall") {
        val layers: List<FirewallLayer> = CalculatingLayer.parse(
                "0: 3\n" +
                        "1: 2")
        expect(layers).to.equal(listOf(CalculatingLayer(0, 3), CalculatingLayer(1, 2)))
    }
    it("fills in empty layers when description has gaps in the layers") {
        val layers: List<FirewallLayer> = CalculatingLayer.parse(
                "0: 4\n" +
                        "3: 3")
        expect(layers).to.equal(listOf(CalculatingLayer(0, 4), EmptyLayer(1), EmptyLayer(2), CalculatingLayer(3, 3)))
    }

    it("can parse calculating layers") {
        val layers: List<FirewallLayer> = CalculatingLayer.parse(
                "0: 3\n" +
                        "1: 2")
        expect(layers).to.equal(listOf(CalculatingLayer(0, 3), CalculatingLayer(1, 2)))
    }

})