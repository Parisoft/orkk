@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl

class SelectFetchClause<T>(
    upstream: Clause<T>,
    val first: String,
    count: Expression<out Number>? = null,
    val row: String,
    val only: String,
) : SelectSubClause11<T>(upstream, if (count != null) arrayOf(count) else emptyArray()) {
    override fun keyword() = "FETCH $first"

    override fun branchToStatement() = Statement(branchToString(), emptyList())

    override fun branchToString() = super.branchToString() + " $row $only"
}

class SelectFetchBuilder<T>(
    val upstream: Clause<T>,
    val first: String,
    val count: Expression<out Number>? = null,
) {
    infix fun ROW(only: ONLY) = SelectFetchClause(upstream, first, count, "ROW", "ONLY")

    infix fun ROWS(only: ONLY) = SelectFetchClause(upstream, first, count, "ROWS", "ONLY")

    infix fun ROW(withTies: WITH_TIES) = SelectFetchClause(upstream, first, count, "ROW", "WITH TIES")

    infix fun ROWS(withTies: WITH_TIES) = SelectFetchClause(upstream, first, count, "ROWS", "WITH TIES")
}

data class FirstCount(
    val count: Expression<out Number>? = null,
)

data class NextCount(
    val count: Expression<out Number>? = null,
)

operator fun FIRST.invoke(count: Expression<out Number>? = null) = FirstCount(count)

operator fun <N : Number> FIRST.invoke(count: N) = FirstCount(count.literal())

object NEXT {
    operator fun invoke(count: Expression<out Number>? = null) = NextCount(count)

    operator fun <N : Number> invoke(count: N) = NextCount(count.literal())
}

object WITH_TIES
