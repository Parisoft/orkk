@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl.pg

// -- Where

open class SelectWhereClause<T>(
    upstream: Clause<T>,
    condition: Expression<Boolean>,
) : SelectSubClause03<T>(upstream, arrayOf(condition)) {
    override fun keyword() = "WHERE"
}
