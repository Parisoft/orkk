@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl.pg

// -- Select

abstract class SelectSubClause<T>(
    upstream: Clause<T>? = null,
    val expressions: Array<out Expression<*>> = emptyArray(),
) : Clause<T>(upstream) {
    var inner = false

    init {
        expressions.forEach {
            if (it is SelectSubClause) {
                it.inner = true
            }
        }
    }

    abstract fun keyword(): String

    override fun toStringFrom(downstream: String?): String {
        val branchString =
            expressions
                .map {
                    val str = it.toString()
                    if (it is SelectSubClause && !str.trim().startsWith("(")) {
                        parenthesize(str)
                    } else {
                        str
                    }
                }.joinToString(",$LF")

        var applyAlias = false
        var thisString =
            (
                if (branchString.isEmpty()) {
                    keyword()
                } else if (branchString.lines().size > 1) {
                    "${keyword()}$LF${ident(branchString)}"
                } else {
                    "${keyword()} $branchString"
                }
            ).let {
                if (downstream != null) {
                    if (aliases.isEmpty()) {
                        "$it$LF$downstream"
                    } else {
                        "$it ${aliases.last()}$LF$downstream"
                    }
                } else if (inner) {
                    applyAlias = aliases.isNotEmpty()
                    it
                } else if (aliases.isNotEmpty()) {
                    "$it ${aliases.last()}"
                } else {
                    it
                }
            }

        val allString = upstream?.toStringFrom(thisString) ?: thisString
        return if (applyAlias) {
            "${parenthesize(allString)} ${aliases.last()}"
        } else {
            allString
        }
    }
}

abstract class SelectSubClause11<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectSubClause<T>(upstream, expressions)

abstract class SelectSubClause10<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectSubClause11<T>(upstream, expressions)

abstract class SelectSubClause09<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectSubClause10<T>(upstream, expressions)

abstract class SelectSubClause08<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectSubClause09<T>(upstream, expressions)

abstract class SelectSubClause07<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectSubClause08<T>(upstream, expressions)

abstract class SelectSubClause06<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectSubClause07<T>(upstream, expressions)

abstract class SelectSubClause05<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectSubClause06<T>(upstream, expressions)

abstract class SelectSubClause04<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectSubClause05<T>(upstream, expressions)

abstract class SelectSubClause03<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectSubClause04<T>(upstream, expressions)

abstract class SelectSubClause02<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectSubClause03<T>(upstream, expressions)

abstract class SelectSubClause01<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectSubClause02<T>(upstream, expressions) {
    // -- From Table

    infix fun FROM(table: Table) = SelectFromTableClause<T>(this, table)

    infix fun FROM(table: String) = this FROM Table(`@name` = table)

    // -- From Select

    infix fun FROM(select: SelectSubClause<*>) = SelectFromSelectClause<T>(this, select)

    // --From Functions

    infix fun FROM(function: FunctionCall<*>) = SelectFromFunctionClause<T>(this, function)

    infix fun FROM(rows: ROWS) = SelectFromRowsClause<T>(this)

    // -- From Lateral

    infix fun FROM(lateral: LateralSelect) = SelectFromLateralSelectClause<T>(this, lateral.select)

    infix fun FROM(lateral: LateralFunction) = SelectFromLateralFunctionClause<T>(this, lateral.function)

    infix fun FROM(lateral: LATERAL) = SelectFromLateralClause<T>(this)
}

object SELECT {
    override fun toString() = "SELECT"

    // -- Select no fields

    operator fun invoke() = SelectEmptyClause()

    // -- Select single field

    operator fun <T> invoke(expression: Expression<T>) = SelectClause<T>(expressions = arrayOf(expression))

    infix fun <T> ALL(expression: Expression<T>) = SelectAllClause<T>(expressions = arrayOf(expression))

    infix fun <T> DISTINCT(expression: Expression<T>) = SelectDistinctClause<T>(expressions = arrayOf(expression))

    // -- Select multiple fields

    operator fun invoke(vararg expressions: Expression<*>) = SelectClause<Any>(expressions = expressions)

    operator fun invoke(expressions: Collection<Expression<*>>) = SelectClause<Any>(expressions = expressions.toAnyArray())

    infix fun ALL(expressions: Collection<Expression<*>>) = SelectAllClause<Any>(expressions = expressions.toAnyArray())

    infix fun DISTINCT(expressions: Collection<Expression<*>>) = SelectDistinctClause<Any>(expressions = expressions.toAnyArray())

    infix fun <T> DISTINCT(on: OnFieldsAndExpressions<T>) = SelectDistinctOnClause<T>(on = on)
}

open class SelectClause<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>>,
) : SelectSubClause01<T>(upstream, expressions) {
    override fun keyword() = "SELECT"
}

open class SelectEmptyClause(
    upstream: Clause<Any>? = null,
) : SelectClause<Any>(upstream, emptyArray()) {
    override fun keyword() = "SELECT"
}

open class SelectAllClause<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>>,
) : SelectClause<T>(upstream, expressions) {
    override fun keyword() = "SELECT ALL"
}

open class SelectDistinctClause<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>>,
) : SelectClause<T>(upstream, expressions) {
    override fun keyword() = "SELECT DISTINCT"
}

open class SelectDistinctOnClause<T>(
    upstream: Clause<T>? = null,
    val on: OnFieldsAndExpressions<T>,
) : SelectClause<T>(upstream, on.expressions) {
    override fun keyword() = "SELECT DISTINCT ON (${on.fields.map { it.toString() }.joinToString(", ")})"
}

data class OnFieldsAndExpressions<T>(
    val fields: Array<out Expression<*>>,
    val expressions: Array<out Expression<T>>,
)

data class OnFields(
    val fields: Array<out Expression<*>>,
) {
    operator fun invoke(vararg expressions: Expression<*>) = OnFieldsAndExpressions(fields, expressions.toList().toAnyArray())

    operator fun invoke(expressions: Collection<Expression<*>>) = OnFieldsAndExpressions(fields, expressions.toAnyArray())

    operator fun <T> invoke(expression: Expression<T>) = OnFieldsAndExpressions(fields, arrayOf(expression))
}

object ON {
    operator fun invoke(vararg fields: Expression<*>) = OnFields(fields)
}

object `*` : Expression<Any>() {
    override fun toString() = "*"
}
