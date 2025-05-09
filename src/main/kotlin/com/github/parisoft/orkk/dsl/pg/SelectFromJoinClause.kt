@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl.pg

abstract class JoinableClause<T>(
    upstream: Clause<T>,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectSubClause02<T>(upstream, expressions) {
    // -- Inner Join

    infix fun JOIN(table: Table) = SelectInOutJoinTableClause(this, table, "INNER")

    infix fun JOIN(table: String) = this JOIN Table(`@name` = table)

    infix fun JOIN(select: SelectSubClause<*>) = SelectInOutJoinSelectClause(this, select, "INNER")

    infix fun JOIN(function: FunctionCall<*>) = SelectInOutJoinFunctionClause(this, function, "INNER")

    infix fun JOIN(rows: ROWS) = SelectInOutJoinRowsClause(this, "INNER")

    // -- Outer Join

    infix fun LEFT(outer: OUTER) = SelectOuterClause(this, "LEFT")

    infix fun RIGHT(outer: OUTER) = SelectOuterClause(this, "RIGHT")

    infix fun FULL(outer: OUTER) = SelectOuterClause(this, "FULL")

    // -- Cross Join

    infix fun CROSS(join: JoinWithTable) = SelectFromCrossJoinTableClause(this, join.table)

    infix fun CROSS(join: JoinWithSelect) = SelectFromCrossJoinSelectClause(this, join.select)

    infix fun CROSS(join: JoinWithFunction) = SelectFromCrossJoinFunctionClause(this, join.function)

    infix fun CROSS(join: JOIN) = SelectFromCrossJoinRowsClause(this)

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

    // -- Outer Join

    infix fun JOIN(table: Table) = SelectInOutJoinTableClause(upstream!!, table, keyword())

    infix fun JOIN(table: String) = this JOIN Table(`@name` = table)

    infix fun JOIN(select: SelectSubClause<*>) = SelectInOutJoinSelectClause(upstream!!, select, keyword())

    infix fun JOIN(function: FunctionCall<*>) = SelectInOutJoinFunctionClause(upstream!!, function, keyword())

    infix fun JOIN(rows: ROWS) = SelectInOutJoinRowsClause(upstream!!, keyword())
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

    // -- Natural Join

    infix fun JOIN(table: Table) = SelectFromNaturalJoinTableClause(upstream!!, table, side)

    infix fun JOIN(table: String) = this JOIN Table(`@name` = table)

    infix fun JOIN(select: SelectSubClause<*>) = SelectFromNaturalJoinSelectClause(upstream!!, select, side)

    infix fun JOIN(function: FunctionCall<*>) = SelectFromNaturalJoinFunctionClause(upstream!!, function, side)

    infix fun JOIN(rows: ROWS) = SelectFromNaturalJoinRowsClause(upstream!!, side)
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

    operator fun invoke(table: String) = JoinWithTable(Table(`@name` = table))

    operator fun invoke(select: SelectSubClause<*>) = JoinWithSelect(select)

    operator fun invoke(function: FunctionCall<*>) = JoinWithFunction(function)
}

data class JoinWithTable(
    val table: Table,
)

data class JoinWithSelect(
    val select: SelectSubClause<*>,
)

data class JoinWithFunction(
    val function: FunctionCall<*>,
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
) : JoinableClause<T>(upstream, arrayOf(method)) {
    override fun keyword() = "TABLESAMPLE"

    infix fun REPEATABLE(seed: Expression<Number>) = SelectFromCrossJoinTableSampleRepeatableClause(this, seed)

    infix fun REPEATABLE(seed: Number) = SelectFromCrossJoinTableSampleRepeatableClause(this, seed.literal())
}

open class SelectFromCrossJoinTableSampleRepeatableClause<T>(
    upstream: Clause<T>,
    val seed: Expression<Number>,
) : JoinableClause<T>(upstream) {
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
) : JoinableClause<T>(upstream, arrayOf(method)) {
    override fun keyword() = "TABLESAMPLE"

    infix fun REPEATABLE(seed: Expression<Number>) = SelectFromNaturalJoinTableSampleRepeatableClause(this, seed)

    infix fun REPEATABLE(seed: Number) = SelectFromNaturalJoinTableSampleRepeatableClause(this, seed.literal())
}

open class SelectFromNaturalJoinTableSampleRepeatableClause<T>(
    upstream: Clause<T>,
    val seed: Expression<Number>,
) : JoinableClause<T>(upstream) {
    override fun keyword() = "REPEATABLE ($seed)"
}

// -- Join Select

open class SelectInOutJoinSelectClause<T>(
    upstream: Clause<T>,
    select: SelectSubClause<*>,
    val side: String,
) : SelectInOutJoinClause<T>(upstream, arrayOf(select)) {
    override fun keyword() = "$side JOIN"
}

open class SelectFromCrossJoinSelectClause<T>(
    upstream: Clause<T>,
    select: SelectSubClause<*>,
) : SelectFromJoinClause<T>(upstream, arrayOf(select)) {
    override fun keyword() = "CROSS JOIN"
}

open class SelectFromNaturalJoinSelectClause<T>(
    upstream: Clause<T>,
    select: SelectSubClause<*>,
    val side: String,
) : SelectFromJoinClause<T>(upstream, arrayOf(select)) {
    override fun keyword() = "NATURAL $side JOIN"
}

// -- Join Function

open class SelectInOutJoinFunctionClause<T>(
    upstream: Clause<T>,
    function: FunctionCall<*>,
    val side: String,
) : SelectInOutJoinClause<T>(upstream, arrayOf(function)) {
    override fun keyword() = "$side JOIN"
}

open class SelectInOutJoinRowsClause<T>(
    upstream: Clause<T>,
    val side: String,
) : SelectInOutJoinClause<T>(upstream) {
    override fun keyword() = "ROWS"

    infix fun FROM(function: FunctionCall<*>) = SelectInOutJoinRowsFromClause(upstream!!, arrayOf(function), side)

    infix fun FROM(functions: Collection<FunctionCall<*>>) = SelectInOutJoinRowsFromClause(upstream!!, functions.toTypedArray(), side)
}

open class SelectInOutJoinRowsFromClause<T>(
    upstream: Clause<T>,
    functions: Array<out FunctionCall<*>>,
    val side: String,
) : SelectInOutJoinClause<T>(upstream, functions) {
    override fun keyword() = "$side JOIN ROWS FROM"

    // TODO: check if need toFullString
//    override fun toFullString(
//        branch: String,
//        downstream: String?,
//    ) = downstream.let { if (it == null) "" else "$LF$it" }.let { downstream ->
//        if (branch.lines().size > 1) {
//            "${keyword()}$LF${parenthesize(branch)}$downstream"
//        } else {
//            "${keyword()}($branch)$downstream"
//        }
//    }

    infix fun WITH(ordinality: ORDINALITY) = SelectInOutJoinWithOrdinalityClause(this)
}

open class SelectInOutJoinWithOrdinalityClause<T>(
    upstream: Clause<T>,
) : SelectInOutJoinClause<T>(upstream) {
    override fun keyword() = "WITH ORDINALITY"
}

open class SelectFromCrossJoinFunctionClause<T>(
    upstream: Clause<T>,
    function: FunctionCall<*>,
) : SelectFromJoinClause<T>(upstream, arrayOf(function)) {
    override fun keyword() = "CROSS JOIN"
}

open class SelectFromCrossJoinRowsClause<T>(
    upstream: Clause<T>,
) : SelectSubClause02<T>(upstream) {
    override fun keyword() = "CROSS JOIN ROWS"

    infix fun ROWS(functions: Collection<FunctionCall<*>>) = SelectFromCrossJoinRowsFromClause(upstream!!, functions.toTypedArray())
}

open class SelectFromCrossJoinRowsFromClause<T>(
    upstream: Clause<T>,
    functions: Array<out FunctionCall<*>>,
) : SelectFromJoinClause<T>(upstream, functions) {
    override fun keyword() = "CROSS JOIN ROWS FROM"

    override fun toFullString(
        branch: String,
        downstream: String?,
    ) = downstream.let { if (it == null) "" else "$LF$it" }.let { downstream ->
        if (branch.lines().size > 1) {
            "${keyword()}$LF${parenthesize(branch)}$downstream"
        } else {
            "${keyword()}($branch)$downstream"
        }
    }

    infix fun WITH(ordinality: ORDINALITY) = SelectFromCrossJoinWithOrdinalityClause(this)
}

open class SelectFromCrossJoinWithOrdinalityClause<T>(
    upstream: Clause<T>,
) : JoinableClause<T>(upstream) {
    override fun keyword() = "WITH ORDINALITY"
}

open class SelectFromNaturalJoinFunctionClause<T>(
    upstream: Clause<T>,
    function: FunctionCall<*>,
    val side: String,
) : SelectFromJoinClause<T>(upstream, arrayOf(function)) {
    override fun keyword() = "NATURAL $side JOIN"
}

open class SelectFromNaturalJoinRowsClause<T>(
    upstream: Clause<T>,
    val side: String,
) : SelectSubClause02<T>(upstream) {
    override fun keyword() = "NATURAL $side JOIN ROWS"

    infix fun FROM(function: FunctionCall<*>) = SelectFromNaturalJoinRowsFromClause(upstream!!, arrayOf(function), side)

    infix fun FROM(functions: Collection<FunctionCall<*>>) = SelectFromNaturalJoinRowsFromClause(upstream!!, functions.toTypedArray(), side)
}

open class SelectFromNaturalJoinRowsFromClause<T>(
    upstream: Clause<T>,
    functions: Array<out FunctionCall<*>>,
    val side: String,
) : SelectFromJoinClause<T>(upstream, functions) {
    override fun keyword() = "NATURAL $side JOIN ROWS FROM"

    override fun toFullString(
        branch: String,
        downstream: String?,
    ) = downstream.let { if (it == null) "" else "$LF$it" }.let { downstream ->
        if (branch.lines().size > 1) {
            "${keyword()}$LF${parenthesize(branch)}$downstream"
        } else {
            "${keyword()}($branch)$downstream"
        }
    }

    infix fun WITH(ordinality: ORDINALITY) = SelectFromNaturalJoinWithOrdinalityClause(this)
}

open class SelectFromNaturalJoinWithOrdinalityClause<T>(
    upstream: Clause<T>,
) : JoinableClause<T>(upstream) {
    override fun keyword() = "WITH ORDINALITY"
}
