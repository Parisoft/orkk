@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl

class SelectOffsetClause<T>(
    upstream: Clause<T>? = null,
    start: Expression<*>,
) : SelectSubClause10<T>(upstream, arrayOf(start)) {
    override fun keyword(): String = "OFFSET"
}
