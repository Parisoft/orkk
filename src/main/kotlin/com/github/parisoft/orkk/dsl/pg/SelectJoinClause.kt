@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl.pg

abstract class JoinableClause<T>(
    upstream: Clause<T>,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectSubClause02<T>(upstream, expressions) {
    // -- Inner Join Table

    infix fun JOIN(table: Table) = SelectInnerJoinClause(this, arrayOf(table))

    infix fun JOIN(table: String) = this JOIN Table(`@name` = table)

    // -- Outer Join

    infix fun LEFT(outer: OUTER) = SelectOuterClause(this, "LEFT")

    infix fun RIGHT(outer: OUTER) = SelectOuterClause(this, "RIGHT")

    infix fun FULL(outer: OUTER) = SelectOuterClause(this, "FULL")

    // -- Cross Join

    infix fun CROSS(join: JoinExpression) = SelectFromCrossJoinClause(this, arrayOf(join.expression))

    // -- Natural Join

    infix fun NATURAL(inner: INNER) = SelectNaturalJoinClause(this, "INNER")

    infix fun NATURAL(left: LEFT) = SelectNaturalJoinClause(this, "LEFT OUTER")

    infix fun NATURAL(right: RIGHT) = SelectNaturalJoinClause(this, "RIGHT OUTER")

    infix fun NATURAL(full: FULL) = SelectNaturalJoinClause(this, "FULL OUTER")
}

open class SelectOuterClause<T>(
    upstream: Clause<T>,
    val side: String,
) : SelectSubClause02<T>(upstream) {
    override fun keyword() = "$side OUTER"

    // -- Outer Join Table

    infix fun JOIN(table: Table) = SelectOuterJoinClause(upstream!!, arrayOf(table), side)

    infix fun JOIN(table: String) = this JOIN Table(`@name` = table)
}

abstract class SelectJoinClause<T>(
    upstream: Clause<T>,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectSubClause02<T>(upstream, expressions) {
    // -- Join On (condition)

    infix fun ON(condition: Expression<Boolean>) = SelectFromJoinOnClause(this, condition)

    infix fun ON(condition: Boolean) = this ON condition.literal()
}

open class SelectInnerJoinClause<T>(
    upstream: Clause<T>,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectJoinClause<T>(upstream, expressions) {
    override fun keyword() = "INNER JOIN"
}

open class SelectOuterJoinClause<T>(
    upstream: Clause<T>,
    expressions: Array<out Expression<*>> = emptyArray(),
    val side: String,
) : SelectJoinClause<T>(upstream, expressions) {
    override fun keyword() = "$side OUTER JOIN"
}

open class SelectNaturalJoinClause<T>(
    upstream: Clause<T>,
    val side: String,
) : SelectSubClause02<T>(upstream) {
    override fun keyword() = "NATURAL $side"

    // -- Natural Join Table

    infix fun JOIN(table: Table) = SelectFromNaturalJoinClause(upstream!!, arrayOf(table), side)
}

abstract class SelectFromJoinClause<T>(
    upstream: Clause<T>,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectFromClause<T>(upstream, expressions)

open class SelectFromJoinOnClause<T>(
    upstream: Clause<T>,
    condition: Expression<Boolean>,
) : SelectFromJoinClause<T>(upstream, arrayOf(condition))

open class SelectFromCrossJoinClause<T>(
    upstream: Clause<T>,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectFromJoinClause<T>(upstream, expressions) {
    override fun keyword() = "CROSS JOIN"
}

open class SelectFromNaturalJoinClause<T>(
    upstream: Clause<T>,
    expressions: Array<out Expression<*>> = emptyArray(),
    val side: String,
) : SelectFromJoinClause<T>(upstream, expressions) {
    override fun keyword() = "NATURAL $side JOIN"
}

object INNER

object OUTER

object LEFT

object RIGHT

object FULL

object JOIN {
    operator fun invoke(table: Table) = JoinExpression(table)
}

data class JoinExpression(
    val expression: Expression<*>,
)
