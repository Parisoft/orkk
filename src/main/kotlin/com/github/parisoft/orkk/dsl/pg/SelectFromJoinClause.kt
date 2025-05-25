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

    infix fun INNER(join: JOIN) = SelectInnerClause(this)

    // -- Outer Join

    infix fun LEFT(outer: OUTER) = SelectOuterClause(this, "LEFT")

    infix fun RIGHT(outer: OUTER) = SelectOuterClause(this, "RIGHT")

    infix fun FULL(outer: OUTER) = SelectOuterClause(this, "FULL")

    infix fun LEFT(join: JOIN) = SelectOuterClause2(this, "LEFT")

    infix fun RIGHT(join: JOIN) = SelectOuterClause2(this, "RIGHT")

    infix fun FULL(join: JOIN) = SelectOuterClause2(this, "FULL")

    // -- Cross Join

    infix fun CROSS(join: JoinWithTable) = SelectFromCrossJoinTableClause(this, join.table)

    infix fun CROSS(join: JoinWithSelect) = SelectFromCrossJoinSelectClause(this, join.select)

    infix fun CROSS(join: JoinWithFunction) = SelectFromCrossJoinFunctionClause(this, join.function)

    infix fun CROSS(join: JOIN) = SelectCrossJoinClause(this)

    // -- Natural Join

    infix fun NATURAL(inner: INNER) = SelectNaturalJoinClause(this, "INNER")

    infix fun NATURAL(left: LEFT) = SelectNaturalJoinClause(this, "LEFT OUTER")

    infix fun NATURAL(right: RIGHT) = SelectNaturalJoinClause(this, "RIGHT OUTER")

    infix fun NATURAL(full: FULL) = SelectNaturalJoinClause(this, "FULL OUTER")

    infix fun NATURAL(join: JOIN) = SelectNaturalJoinClause2(this, "INNER")
}

open class SelectInnerClause<T>(
    upstream: Clause<T>,
) : SelectSubClause02<T>(upstream) {
    override fun keyword() = "INNER"

    infix fun LATERAL(select: SelectSubClause<*>) = SelectInOutJoinSelectClause(upstream!!, select, "INNER", lateral = true)

    infix fun LATERAL(function: FunctionCall<*>) = SelectInOutJoinFunctionClause(upstream!!, function, "INNER", lateral = true)

    infix fun LATERAL(rows: ROWS) = SelectInOutJoinRowsClause(upstream!!, "INNER", lateral = true)
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

open class SelectOuterClause2<T>(
    upstream: Clause<T>,
    val side: String,
) : SelectSubClause02<T>(upstream) {
    override fun keyword() = "$side OUTER"

    infix fun LATERAL(select: SelectSubClause<*>) = SelectInOutJoinSelectClause(upstream!!, select, keyword(), lateral = true)

    infix fun LATERAL(function: FunctionCall<*>) = SelectInOutJoinFunctionClause(upstream!!, function, keyword(), lateral = true)

    infix fun LATERAL(rows: ROWS) = SelectInOutJoinRowsClause(upstream!!, keyword(), lateral = true)
}

abstract class SelectInOutJoinClause<T>(
    upstream: Clause<T>,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectSubClause02<T>(upstream, expressions) {
    // -- Join On (condition)

    infix fun ON(condition: Expression<Boolean>) = SelectFromJoinOnClause(this, condition)

    infix fun ON(condition: Boolean) = this ON condition.literal()

    // -- Join Using (columns)

    infix fun USING(columns: Collection<*>) =
        SelectFromJoinUsingClause(
            this,
            columns
                .map {
                    when (it) {
                        is Field<*> -> it
                        else -> fieldOf<Any>(it.toString())
                    }
                }.toTypedArray(),
        )

    infix fun USING(field: Field<*>) = SelectFromJoinUsingClause(this, arrayOf(field))

    infix fun USING(field: String) = this USING fieldOf<Any>(field)
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

    // -- Lateral Join

    infix fun OUTER(join: JOIN) = SelectNaturalJoinClause2(upstream!!, side)
}

open class SelectNaturalJoinClause2<T>(
    upstream: Clause<T>,
    val side: String,
) : SelectSubClause02<T>(upstream) {
    override fun keyword() = "NATURAL $side"

    infix fun LATERAL(select: SelectSubClause<*>) = SelectFromNaturalJoinSelectClause(upstream!!, select, side, lateral = true)

    infix fun LATERAL(function: FunctionCall<*>) = SelectFromNaturalJoinFunctionClause(upstream!!, function, side, lateral = true)

    infix fun LATERAL(rows: ROWS) = SelectFromNaturalJoinRowsClause(upstream!!, side, lateral = true)
}

open class SelectCrossJoinClause<T>(
    upstream: Clause<T>,
) : SelectSubClause02<T>(upstream) {
    override fun keyword() = "CROSS JOIN"

    infix fun ONLY(table: Table) = SelectFromCrossJoinTableClause(upstream!!, ONLY.invoke(table))

    infix fun ROWS(functions: Collection<FunctionCall<*>>) = SelectFromCrossJoinRowsFromClause(upstream!!, functions.toTypedArray())

    infix fun LATERAL(select: SelectSubClause<*>) = SelectFromCrossJoinSelectClause(upstream!!, select, lateral = true)

    infix fun LATERAL(function: FunctionCall<*>) = SelectFromCrossJoinFunctionClause(upstream!!, function, lateral = true)

    infix fun LATERAL(rows: ROWS) = SelectFromCrossJoinRowsClause(upstream!!)
}

abstract class SelectFromJoinClause<T>(
    upstream: Clause<T>,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectFromClause<T>(upstream, expressions)

open class SelectFromJoinOnClause<T>(
    upstream: Clause<T>,
    condition: Expression<Boolean>,
) : SelectFromJoinClause<T>(upstream, arrayOf(condition)) {
    override fun keyword() = "ON"
}

open class SelectFromJoinUsingClause<T>(
    upstream: Clause<T>,
    columns: Array<out Expression<*>>,
) : SelectFromJoinClause<T>(upstream, columns) {
    override fun keyword() = "USING"

    override fun branchToString() =
        super.branchToString().let {
            if (it.trim().startsWith("(")) {
                it
            } else if (it.lines().size > 1) {
                parenthesize(it)
            } else {
                "($it)"
            }
        }
}

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
    val lateral: Boolean = false,
) : SelectInOutJoinClause<T>(upstream, arrayOf(select)) {
    override fun keyword() = "$side JOIN${if (lateral) " LATERAL" else ""}"
}

open class SelectFromCrossJoinSelectClause<T>(
    upstream: Clause<T>,
    select: SelectSubClause<*>,
    val lateral: Boolean = false,
) : SelectFromJoinClause<T>(upstream, arrayOf(select)) {
    override fun keyword() = "CROSS JOIN${if (lateral) " LATERAL" else ""}"
}

open class SelectFromNaturalJoinSelectClause<T>(
    upstream: Clause<T>,
    select: SelectSubClause<*>,
    val side: String,
    val lateral: Boolean = false,
) : SelectFromJoinClause<T>(upstream, arrayOf(select)) {
    override fun keyword() = "NATURAL $side JOIN${if (lateral) " LATERAL" else ""}"
}

// -- Join Function

open class SelectInOutJoinFunctionClause<T>(
    upstream: Clause<T>,
    function: FunctionCall<*>,
    val side: String,
    val lateral: Boolean = false,
) : SelectInOutJoinClause<T>(upstream, arrayOf(function)) {
    override fun keyword() = "$side JOIN${if (lateral) " LATERAL" else ""}"

    infix fun WITH(ordinality: ORDINALITY) = SelectInOutJoinWithOrdinalityClause(this)
}

open class SelectInOutJoinRowsClause<T>(
    upstream: Clause<T>,
    val side: String,
    val lateral: Boolean = false,
) : SelectInOutJoinClause<T>(upstream) {
    override fun keyword() = "ROWS"

    infix fun FROM(function: FunctionCall<*>) = SelectInOutJoinRowsFromClause(upstream!!, arrayOf(function), side, lateral)

    infix fun FROM(functions: Collection<FunctionCall<*>>) =
        SelectInOutJoinRowsFromClause(upstream!!, functions.toTypedArray(), side, lateral)
}

open class SelectInOutJoinRowsFromClause<T>(
    upstream: Clause<T>,
    functions: Array<out FunctionCall<*>>,
    val side: String,
    val lateral: Boolean = false,
) : SelectInOutJoinClause<T>(upstream, functions) {
    override fun keyword() = "$side JOIN${if (lateral) " LATERAL" else ""} ROWS FROM"

    // TODO: check if need toFullString
    override fun branchToString() =
        super.branchToString().let {
            if (it.trim().startsWith("(")) {
                it
            } else if (it.lines().size > 1) {
                parenthesize(it)
            } else {
                "($it)"
            }
        }

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
    val lateral: Boolean = false,
) : SelectFromJoinClause<T>(upstream, arrayOf(function)) {
    override fun keyword() = "CROSS JOIN${if (lateral) " LATERAL" else ""}"

    infix fun WITH(ordinality: ORDINALITY) = SelectFromCrossJoinWithOrdinalityClause(this)
}

open class SelectFromCrossJoinRowsClause<T>(
    upstream: Clause<T>,
) : SelectSubClause02<T>(upstream) {
    override fun keyword() = "CROSS JOIN LATERAL ROWS"

    infix fun FROM(function: FunctionCall<*>) = SelectFromCrossJoinRowsFromClause(upstream!!, arrayOf(function), true)

    infix fun FROM(functions: Collection<FunctionCall<*>>) = SelectFromCrossJoinRowsFromClause(upstream!!, functions.toTypedArray(), true)
}

open class SelectFromCrossJoinRowsFromClause<T>(
    upstream: Clause<T>,
    functions: Array<out FunctionCall<*>>,
    val lateral: Boolean = false,
) : SelectFromJoinClause<T>(upstream, functions) {
    override fun keyword() = "CROSS JOIN${if (lateral) " LATERAL" else ""} ROWS FROM"

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
    val lateral: Boolean = false,
) : SelectFromJoinClause<T>(upstream, arrayOf(function)) {
    override fun keyword() = "NATURAL $side JOIN${if (lateral) " LATERAL" else ""}"
}

open class SelectFromNaturalJoinRowsClause<T>(
    upstream: Clause<T>,
    val side: String,
    val lateral: Boolean = false,
) : SelectSubClause02<T>(upstream) {
    override fun keyword() = "NATURAL $side JOIN ROWS"

    infix fun FROM(function: FunctionCall<*>) = SelectFromNaturalJoinRowsFromClause(upstream!!, arrayOf(function), side, lateral)

    infix fun FROM(functions: Collection<FunctionCall<*>>) =
        SelectFromNaturalJoinRowsFromClause(upstream!!, functions.toTypedArray(), side, lateral)
}

open class SelectFromNaturalJoinRowsFromClause<T>(
    upstream: Clause<T>,
    functions: Array<out FunctionCall<*>>,
    val side: String,
    val lateral: Boolean = false,
) : SelectFromJoinClause<T>(upstream, functions) {
    override fun keyword() = "NATURAL $side JOIN${if (lateral) "LATERAL" else ""} ROWS FROM"

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
