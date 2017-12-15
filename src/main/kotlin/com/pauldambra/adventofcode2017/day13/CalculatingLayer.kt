package com.pauldambra.adventofcode2017.day13

data class CalculatingLayer(override val depth: Int, private val range: Int) : FirewallLayer {
    companion object {
        fun parse(description: String): List<FirewallLayer> {
            val providedLayers = description
                    .split("\n")
                    .map(::CalculatingLayer)
                    .associateBy({ it.depth }, { it })

            val depthsRange = providedLayers.keys.min()!!.rangeTo(providedLayers.keys.max()!!)

            return depthsRange.fold(emptyList()) { layers, depth ->
                layers + providedLayers.getOrDefault(depth, EmptyLayer(depth))
            }
        }
    }

    constructor(description: String) : this(
            description.split(": ")[0].toInt(),
            description.split(": ")[1].toInt())

    override fun scannerPosition(atPicosecond: Int): Int {
        return atPicosecond % ((range - 1) * 2)
    }

    override fun layerRange(): Int = range

}