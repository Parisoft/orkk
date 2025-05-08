@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl.pg

abstract class JoinableClause<T>(
    upstream: Clause<T>,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectSubClause02<T>(upstream, expressions) {
    // -- Inner Join Table

    infix fun JOIN(table: Table) = SelectInOutJoinTableClause(this, table, "INNER")

    infix fun JOIN(table: String) = this JOIN Table(`@name` = table)

    // -- Outer Join

    infix fun LEFT(outer: OUTER) = SelectOuterClause(this, "LEFT")

    infix fun RIGHT(outer: OUTER) = SelectOuterClause(this, "RIGHT")

    infix fun FULL(outer: OUTER) = SelectOuterClause(this, "FULL")

    // -- Cross Join

    infix fun CROSS(join: JoinWithTable) = SelectFromCrossJoinTableClause(this, join.table)

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

    infix fun JOIN(table: Table) = SelectInOutJoinTableClause(upstream!!, table, keyword())

    infix fun JOIN(table: String) = this JOIN Table(`@name` = table)
}

abstract class SelectInOutJoinClause<T>(
    upstream: Clause<T>,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectSubClause02<T>(upstream, expressions) {
    // -- Join On (condition)

    infix fun ON(condition: Expression<Boolean>) = SelectFromJoinOnClause(this, condition)

    infix fun ON(condition: Boolean) = this ON condition.literal()
}

open class SelectNaturalJoinClause<T>(
    upstream: Clause<T>,
    val side: String,
) : SelectSubClause02<T>(upstream) {
    override fun keyword() = "NATURAL $side"

    // -- Natural Join Table

    infix fun JOIN(table: Table) = SelectFromNaturalJoinTableClause(upstream!!, table, side)
}

abstract class SelectFromJoinClause<T>(
    upstream: Clause<T>,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectFromClause<T>(upstream, expressions)

open class SelectFromJoinOnClause<T>(
    upstream: Clause<T>,
    condition: Expression<Boolean>,
) : SelectFromJoinClause<T>(upstream, arrayOf(condition))

object INNER

object OUTER

object LEFT

object RIGHT

object FULL

object JOIN {
    operator fun invoke(table: Table) = JoinWithTable(table)
}

data class JoinWithTable(
    val table: Table,
)

// -- Join Table

open class SelectInOutJoinTableClause<T>(
    upstream: Clause<T>,
    table: Table,
    val side: String,
) : SelectInOutJoinClause<T>(upstream, arrayOf(table)) {
    override fun keyword() = "$side JOIN"

    infix fun TABLESAMPLE(method: FunctionCall<*>) = SelectInOutJoinTableSampleClause(this, method)
}

open class SelectInOutJoinTableSampleClause<T>(
    upstream: Clause<T>,
    method: FunctionCall<*>,
) : SelectInOutJoinClause<T>(upstream, arrayOf(method)) {
    override fun keyword() = "TABLESAMPLE"

    infix fun REPEATABLE(seed: Expression<Number>) = SelectInOutJoinTableSampleRepeatableClause(this, seed)

    infix fun REPEATABLE(seed: Number) = SelectInOutJoinTableSampleRepeatableClause(this, seed.literal())
}

open class SelectInOutJoinTableSampleRepeatableClause<T>(
    upstream: Clause<T>,
    val seed: Expression<Number>,
) : SelectInOutJoinClause<T>(upstream) {
    override fun keyword() = "REPEATABLE ($seed)"
}

open class SelectFromCrossJoinTableClause<T>(
    upstream: Clause<T>,
    table: Table,
) : SelectFromJoinClause<T>(upstream, arrayOf(table)) {
    override fun keyword() = "CROSS JOIN"

    infix fun TABLESAMPLE(method: FunctionCall<*>) = SelectFromCrossJoinTableSampleClause(this, method)
}

open class SelectFromCrossJoinTableSampleClause<T>(
    upstream: Clause<T>,
    method: FunctionCall<*>,
) : SelectFromJoinClause<T>(upstream, arrayOf(method)) {
    override fun keyword() = "TABLESAMPLE"

    infix fun REPEATABLE(seed: Expression<Number>) = SelectFromCrossJoinTableSampleRepeatableClause(this, seed)

    infix fun REPEATABLE(seed: Number) = SelectFromCrossJoinTableSampleRepeatableClause(this, seed.literal())
}

open class SelectFromCrossJoinTableSampleRepeatableClause<T>(
    upstream: Clause<T>,
    val seed: Expression<Number>,
) : SelectFromJoinClause<T>(upstream) {
    override fun keyword() = "REPEATABLE ($seed)"
}

open class SelectFromNaturalJoinTableClause<T>(
    upstream: Clause<T>,
    table: Table,
    val side: String,
) : SelectFromJoinClause<T>(upstream, arrayOf(table)) {
    override fun keyword() = "NATURAL $side JOIN"

    infix fun TABLESAMPLE(method: FunctionCall<*>) = SelectFromNaturalJoinTableSampleClause(this, method)
}

open class SelectFromNaturalJoinTableSampleClause<T>(
    upstream: Clause<T>,
    method: FunctionCall<*>,
) : SelectFromJoinClause<T>(upstream, arrayOf(method)) {
    override fun keyword() = "TABLESAMPLE"

    infix fun REPEATABLE(seed: Expression<Number>) = SelectFromNaturalJoinTableSampleRepeatableClause(this, seed)

    infix fun REPEATABLE(seed: Number) = SelectFromNaturalJoinTableSampleRepeatableClause(this, seed.literal())
}

open class SelectFromNaturalJoinTableSampleRepeatableClause<T>(
    upstream: Clause<T>,
    val seed: Expression<Number>,
) : SelectFromJoinClause<T>(upstream) {
    override fun keyword() = "REPEATABLE ($seed)"
}
