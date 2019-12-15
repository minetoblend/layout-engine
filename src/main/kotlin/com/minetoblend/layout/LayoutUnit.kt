package com.minetoblend.layout


/**
 * Represents an absolute or relative length unit
 */
interface LayoutUnit {

    /**
     * Determines the absolute value for a given node
     */
    fun getValue(node: LayoutNode): Float

}

/**
 * Represents an absolute length in pixels
 *
 * @constructor Creates a LayoutUnit representing the given amount in pixels
 */
class AbsoluteUnit(val value: Float) : LayoutUnit {
    override fun getValue(node: LayoutNode) = value
}

/**
 * Determines the absolute length based on the given node
 *
 * @property calc the calculation to use to obtain the absolute value
 */
class ComplexUnit(val calc: (node: LayoutNode) -> Float) : LayoutUnit {
    override fun getValue(node: LayoutNode) = calc(node)
}


/**
 * Determines the absolute length by combining the values of two LayoutUnits
 *
 * @see LayoutUnit.plus
 * @see LayoutUnit.minus
 * @see LayoutUnit.times
 * @see LayoutUnit.div
 */
class CompositeUnit(
    val a: LayoutUnit,
    val b: LayoutUnit,
    val calc: (node: LayoutNode, a: LayoutUnit, b: LayoutUnit) -> Float
) : LayoutUnit {
    override fun getValue(node: LayoutNode): Float = calc(node, a, b)
}

operator fun LayoutUnit.plus(other: LayoutUnit) =
    CompositeUnit(this, other) { node, a, b -> a.getValue(node) + b.getValue(node) }

operator fun LayoutUnit.minus(other: LayoutUnit) =
    CompositeUnit(this, other) { node, a, b -> a.getValue(node) - b.getValue(node) }

operator fun LayoutUnit.times(other: LayoutUnit) =
    CompositeUnit(this, other) { node, a, b -> a.getValue(node) * b.getValue(node) }

operator fun LayoutUnit.div(other: LayoutUnit) =
    CompositeUnit(this, other) { node, a, b -> a.getValue(node) / b.getValue(node) }


val Number.px get() = AbsoluteUnit(this.toFloat())