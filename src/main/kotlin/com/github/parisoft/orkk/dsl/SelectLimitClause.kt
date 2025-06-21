@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl

class SelectLimitClause<T>(
    upstream: Clause<T>,
    count: Expression<out Number>,
) : SelectSubClause09<T>(upstream, arrayOf(count)) {
    override fun keyword(): String = "LIMIT"
}

class SelectLimitAllClause<T>(
    upstream: Clause<T>,
) : SelectSubClause09<T>(upstream) {
    override fun keyword(): String = "LIMIT ALL"
}
