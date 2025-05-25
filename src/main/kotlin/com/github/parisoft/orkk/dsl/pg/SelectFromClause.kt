@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl.pg

// -- From

open class SelectFromClause<T>(
    override val upstream: Clause<T>,
    expressions: Array<out Expression<*>>,
) : JoinableClause<T>(upstream, expressions) {
    override fun keyword() = "FROM"

    private fun isReferenced(alias: Alias): Boolean {
        var select: Clause<*>? = upstream
        while (select != null && select !is SelectClause) select = select.upstream
        return select?.expressions?.any { it is Field && it.table?.toString() == alias.name } == true
    }

    protected open fun toFullString(
        branch: String,
        downstream: String? = null,
    ) = downstream.let { if (it == null) "" else "$LF$it" }.let { downstream ->
        if (branch.lines().size > 1) {
            "${keyword()}$LF${ident(branch)}$downstream"
        } else {
            "${keyword()} $branch$downstream"
        }
    }

    override fun toStringFrom(downstream: String?): String {
        val branchString = branchToString()

        val thisStringToAlias =
            if (downstream == null) {
                when {
                    aliases.size == 1 ->
                        if (isReferenced(aliases.first()) || aliases.first().fields.isNotEmpty() || !inner) {
                            toFullString("$branchString ${aliases.first()}") to null
                        } else {
                            toFullString(branchString) to aliases.first()
                        }

                    aliases.size > 1 ->
                        if (inner) {
                            toFullString("$branchString ${aliases.first()}") to aliases.last()
                        } else {
                            toFullString("$branchString ${aliases.last()}") to null
                        }

                    else -> toFullString(branchString) to null
                }
            } else {
                when {
                    aliases.isNotEmpty() -> toFullString("$branchString ${aliases.last()}", downstream) to null
                    else -> toFullString(branchString, downstream) to null
                }
            }

        val thisString = thisStringToAlias.first
        val allString = upstream.toStringFrom(thisString)
        val alias = thisStringToAlias.second
        return if (alias != null) "${parenthesize(allString)} $alias" else allString
    }
}

// -- From table

open class SelectFromTableClause<T>(
    upstream: Clause<T>,
    table: Table,
) : SelectFromClause<T>(upstream, arrayOf(table)) {
    infix fun TABLESAMPLE(method: FunctionCall<*>) = SelectFromTableSampleClause(this, method)
}

open class SelectFromTableSampleClause<T>(
    upstream: SelectFromClause<T>,
    method: FunctionCall<*>,
) : JoinableClause<T>(upstream, arrayOf(method)) {
    override fun keyword() = "TABLESAMPLE"

    infix fun REPEATABLE(seed: Expression<Number>) = SelectFromTableSampleRepeatableClause(this, seed)

    infix fun REPEATABLE(seed: Number) = SelectFromTableSampleRepeatableClause(this, seed.literal())
}

open class SelectFromTableSampleRepeatableClause<T>(
    upstream: SelectFromTableSampleClause<T>,
    val seed: Expression<Number>,
) : JoinableClause<T>(upstream) {
    override fun keyword() = "REPEATABLE ($seed)"
}

class OnlyTable(
    table: Table,
) : Table(table.`@schema`, table.`@name`) {
    override fun toString() = "ONLY ${super.toString()}"
}

object ONLY {
    operator fun invoke(table: Table) = OnlyTable(table)
}

// -- From Select

open class SelectFromSelectClause<T>(
    upstream: Clause<T>,
    select: SelectSubClause<*>,
) : SelectFromClause<T>(upstream, arrayOf(select))

// -- From Function

open class SelectFromFunctionClause<T>(
    upstream: Clause<T>,
    function: FunctionCall<*>,
) : SelectFromClause<T>(upstream, arrayOf(function)) {
    infix fun WITH(ordinality: ORDINALITY) = SelectFromWithOrdinalityClause<T>(this)
}

open class SelectFromRowsClause<T>(
    upstream: Clause<T>,
) : SelectSubClause02<T>(upstream) {
    override fun keyword() = "ROWS"

    infix fun FROM(function: FunctionCall<*>) = SelectFromRowsFromClause<T>(upstream!!, arrayOf(function))

    infix fun FROM(functions: Collection<FunctionCall<*>>) = SelectFromRowsFromClause<T>(upstream!!, functions.toTypedArray())
}

open class SelectFromRowsFromClause<T>(
    upstream: Clause<T>,
    functions: Array<out FunctionCall<*>>,
) : SelectFromClause<T>(upstream, functions) {
    override fun keyword() = "FROM ROWS FROM"

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

    infix fun WITH(ordinality: ORDINALITY) = SelectFromWithOrdinalityClause<T>(this)
}

open class SelectFromWithOrdinalityClause<T>(
    upstream: Clause<T>,
) : JoinableClause<T>(upstream) {
    override fun keyword() = "WITH ORDINALITY"
}

object ROWS

object ORDINALITY

// -- From Lateral

object LATERAL {
    operator fun invoke(select: SelectSubClause<*>) = LateralSelect(select)

    operator fun invoke(function: FunctionCall<*>) = LateralFunction(function)
}

object FROM {
    operator fun invoke(vararg functions: FunctionCall<*>) = functions.toList()
}

open class SelectFromLateralClause<T>(
    upstream: Clause<T>,
) : SelectFromClause<T>(upstream, emptyArray()) {
    override fun keyword() = "FROM LATERAL"

    infix fun ROWS(functions: Collection<FunctionCall<*>>) = SelectFromLateralRowsFromClause<T>(upstream, functions.toTypedArray())
}

open class SelectFromLateralSelectClause<T>(
    upstream: Clause<T>,
    select: SelectSubClause<*>,
) : SelectFromSelectClause<T>(upstream, select) {
    override fun keyword() = "FROM LATERAL"
}

open class SelectFromLateralFunctionClause<T>(
    upstream: Clause<T>,
    function: FunctionCall<*>,
) : SelectFromFunctionClause<T>(upstream, function) {
    override fun keyword() = "FROM LATERAL"
}

open class SelectFromLateralRowsClause<T>(
    upstream: Clause<T>,
) : JoinableClause<T>(upstream) {
    override fun keyword() = "FROM LATERAL ROWS"

    infix fun FROM(function: FunctionCall<*>) = SelectFromLateralRowsFromClause<T>(upstream!!, arrayOf(function))

    infix fun FROM(functions: Collection<FunctionCall<*>>) = SelectFromLateralRowsFromClause<T>(upstream!!, functions.toTypedArray())
}

open class SelectFromLateralRowsFromClause<T>(
    upstream: Clause<T>,
    functions: Array<out FunctionCall<*>>,
) : SelectFromRowsFromClause<T>(upstream, functions) {
    override fun keyword() = "FROM LATERAL ROWS FROM"
}

data class LateralSelect(
    val select: SelectSubClause<*>,
)

data class LateralFunction(
    val function: FunctionCall<*>,
)
