package com.minetoblend.layout

/**
 * A <em> LayoutNode </em> represents all properties of a dom element that are required to determine it's layout.
 */
class LayoutNode {


    var left: LayoutUnit? = null
    var right: LayoutUnit? = null
    var top: LayoutUnit? = null
    var bottom: LayoutUnit? = null

    var width: LayoutUnit? = null
    var height: LayoutUnit? = null

    //Margin
    var marginTop: LayoutUnit? = null
    var marginLeft: LayoutUnit? = null
    var marginRight: LayoutUnit? = null
    var marginBottom: LayoutUnit? = null

    //Padding
    var paddingTop: LayoutUnit? = null
    var paddingLeft: LayoutUnit? = null
    var paddingRight: LayoutUnit? = null
    var paddingBottom: LayoutUnit? = null

    fun margin(margin: LayoutUnit) {
        marginTop = margin
        marginBottom = margin
        marginLeft = margin
        marginRight = margin
    }

    private val _children = mutableListOf<LayoutNode>()
    private var parent: LayoutNode? = null
    val children get() = _children.toList()
    val isLeaf get() = children.isEmpty()

    fun addChild(child: LayoutNode): Boolean {
        child.parent?.removeChild(child)
        child.parent = this
        return _children.add(child)
    }

    private fun removeChild(child: LayoutNode): Boolean {
        if (_children.remove(child)) {
            child.parent = null
            return true
        }
        return false
    }

    var needsRecalc: Boolean = false

    val childNeedsRecalc: Boolean
        get() {
            for (child in children) {
                if (child.needsRecalc) return true
                if (child.childNeedsRecalc) return true
            }
            return false
        }
}







