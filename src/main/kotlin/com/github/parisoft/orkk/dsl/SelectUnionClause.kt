@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl

class SelectUnionClause<T>(
    upstream: Clause<T>,
    select: SelectClause<T>,
    val type: String = "UNION",
    val modifier: String? = null,
) : SelectSubClause07<T>(upstream, arrayOf(select)) {
    override fun keyword(): String = "$type${if (modifier != null) " $modifier" else ""}"
}

data class AllSelect<T>(
    val select: SelectClause<T>,
)

data class DistinctSelect<T>(
    val select: SelectClause<T>,
)

object ALL {
    operator fun <T> invoke(select: SelectClause<T>): AllSelect<T> = AllSelect(select)
}

object DISTINCT {
    operator fun <T> invoke(select: SelectClause<T>): DistinctSelect<T> = DistinctSelect(select)
}
