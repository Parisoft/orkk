@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl

open class BinaryExpression<T>(
    val left: Expression<*>,
    val right: Expression<*>,
    val op: String,
    var parenthesize: Boolean = false,
) : Expression<T>() {
    override fun toString() = if (parenthesize)"($left $op $right)" else "$left $op $right"
}

// -- Arithmetic
// FIXME replace with explicit type declaration
operator fun Expression<out Number>.plus(n: Expression<out Number>) = BinaryExpression<Number>(this, n, "+").also { wrap(this, n) }

operator fun <N : Number> Expression<out Number>.plus(n: N) = this + n.literal()

operator fun <N : Number> N.plus(n: Expression<Number>) = literal() + n

// -- Boolean

infix fun Expression<Boolean>.OR(b: Expression<Boolean>) = BinaryExpression<Boolean>(this, b, "OR").also { wrap(this, b) }

infix fun Expression<Boolean>.OR(b: Boolean) = this OR b.literal()

infix fun Boolean.OR(b: Expression<Boolean>) = literal() OR b

// -- Helpers

private fun wrap(
    x1: Expression<*>,
    x2: Expression<*>,
) {
    if (x1 is BinaryExpression<*>) {
        x1.parenthesize = true
    }
    if (x2 is BinaryExpression<*>) {
        x2.parenthesize = true
    }
}
