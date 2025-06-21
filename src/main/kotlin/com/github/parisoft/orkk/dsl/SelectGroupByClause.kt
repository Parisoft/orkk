@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl

// -- Group By

class SelectByClause<T>(
    upstream: Clause<T>,
) : SelectSubClause04<T>(upstream) {
    override fun keyword() = "BY"

    infix fun ROLLUP(group: GroupList) = SelectGroupByClause(upstream!!, arrayOf(ROLLUP.invoke(group)))

    infix fun ROLLUP(expressions: Collection<Expression<*>>) =
        SelectGroupByClause(upstream!!, arrayOf(ROLLUP.invoke(*expressions.toTypedArray())))

    infix fun ROLLUP(expression: Expression<*>) = SelectGroupByClause(upstream!!, arrayOf(ROLLUP.invoke(expression)))

    infix fun CUBE(group: GroupList) = SelectGroupByClause(upstream!!, arrayOf(CUBE.invoke(group)))

    infix fun CUBE(expressions: Collection<Expression<*>>) =
        SelectGroupByClause(upstream!!, arrayOf(CUBE.invoke(*expressions.toTypedArray())))

    infix fun CUBE(expression: Expression<*>) = SelectGroupByClause(upstream!!, arrayOf(CUBE.invoke(expression)))

    infix fun GROUPING(sets: GroupingSets) = SelectGroupByClause(upstream!!, arrayOf(sets))

    infix fun ALL(grouping: GROUPING) = SelectGroupByAllGroupingClause(upstream!!)

    infix fun ALL(group: GroupList) = SelectGroupByAllClause(upstream!!, arrayOf(group))

    infix fun ALL(expressions: Collection<Expression<*>>) = SelectGroupByAllClause(upstream!!, expressions.toTypedArray())

    infix fun ALL(expression: Expression<*>) = SelectGroupByAllClause(upstream!!, arrayOf(expression))

    infix fun DISTINCT(grouping: GROUPING) = SelectGroupByDistinctGroupingClause(upstream!!)

    infix fun DISTINCT(group: GroupList) = SelectGroupByDistinctClause(upstream!!, arrayOf(group))

    infix fun DISTINCT(expressions: Collection<Expression<*>>) = SelectGroupByDistinctClause(upstream!!, expressions.toTypedArray())

    infix fun DISTINCT(expression: Expression<*>) = SelectGroupByDistinctClause(upstream!!, arrayOf(expression))
}

open class SelectGroupByClause<T>(
    upstream: Clause<T>,
    expressions: Array<out Expression<*>>,
) : SelectSubClause04<T>(upstream, if (expressions.isNotEmpty()) expressions else arrayOf(GroupList())) {
    override fun keyword() = "GROUP BY"
}

class SelectGroupByAllGroupingClause<T>(
    upstream: Clause<T>,
) : SelectSubClause04<T>(upstream) {
    override fun keyword() = "GROUP BY ALL GROUPING"

    infix fun SETS(expression: Expression<*>) = SelectGroupByAllClause(upstream!!, arrayOf(GROUPING.SETS(expression)))

    infix fun SETS(group: GroupList) = SETS(group as Expression<*>)

    infix fun SETS(expressions: Collection<Expression<*>>) =
        SelectGroupByAllClause(
            upstream!!,
            arrayOf(GROUPING.SETS(expressions)),
        )
}

class SelectGroupByDistinctGroupingClause<T>(
    upstream: Clause<T>,
) : SelectSubClause04<T>(upstream) {
    override fun keyword() = "GROUP BY DISTINCT GROUPING"

    infix fun SETS(expression: Expression<*>) = SelectGroupByDistinctClause(upstream!!, arrayOf(GROUPING.SETS(expression)))

    infix fun SETS(group: GroupList) = SETS(group as Expression<*>)

    infix fun SETS(expressions: Collection<Expression<*>>) =
        SelectGroupByDistinctClause(
            upstream!!,
            arrayOf(GROUPING.SETS(expressions)),
        )
}

class SelectGroupByAllClause<T>(
    upstream: Clause<T>,
    expressions: Array<out Expression<*>>,
) : SelectGroupByClause<T>(upstream, expressions) {
    override fun keyword() = "GROUP BY ALL"
}

class SelectGroupByDistinctClause<T>(
    upstream: Clause<T>,
    expressions: Array<out Expression<*>>,
) : SelectGroupByClause<T>(upstream, expressions) {
    override fun keyword() = "GROUP BY DISTINCT"
}

data class ByExpressions(
    val expressions: Array<out Expression<*>>,
)

object BY {
    operator fun invoke(vararg expressions: Expression<*>) = ByExpressions(expressions)

    operator fun invoke(expressions: Collection<Expression<*>>) = ByExpressions(expressions.toTypedArray())

    operator fun invoke(group: GroupList) = ByExpressions(arrayOf(group))
}

object ROLLUP {
    operator fun invoke(vararg expressions: Expression<*>) = RollupGrouping(expressions)
}

object CUBE {
    operator fun invoke(vararg expressions: Expression<*>) = CubeGrouping(expressions)
}

object GROUPING {
    infix fun SETS(expression: Expression<*>) = GroupingSets(arrayOf(expression))

    infix fun SETS(expressions: Collection<Expression<*>>) = GroupingSets(expressions.toTypedArray())
}

object SETS {
    operator fun invoke(vararg expressions: Expression<*>) = GroupingSets(expressions)
}

open class GroupList(
    vararg expressions: Expression<*>,
) : Clause<Any>(expressions = expressions) {
    override fun keyword() = ""

    override fun toStringFrom(downstream: String?) =
        if (expressions.isEmpty()) {
            "()"
        } else if (expressions.size == 1 && expressions.first() !is GroupList) {
            "(${expressions.first()})"
        } else {
            parenthesize(expressions.joinToString(",$LF"))
        }
}

class RollupGrouping(
    expressions: Array<out Expression<*>>,
) : GroupList(*expressions) {
    override fun toStringFrom(downstream: String?) = "ROLLUP ${super.toStringFrom(downstream)}"
}

class CubeGrouping(
    expressions: Array<out Expression<*>>,
) : GroupList(*expressions) {
    override fun toStringFrom(downstream: String?) = "CUBE ${super.toStringFrom(downstream)}"
}

class GroupingSets(
    expressions: Array<out Expression<*>>,
) : GroupList(*expressions) {
    override fun toStringFrom(downstream: String?) = "GROUPING SETS ${super.toStringFrom(downstream)}"
}

fun groupOf(vararg expressions: Expression<*>) = GroupList(*expressions)

fun emptyGroup() = GroupList()
