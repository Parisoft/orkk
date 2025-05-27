@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl.pg

// -- Having

open class SelectHavingClause<T>(
    upstream: Clause<T>,
    condition: Expression<Boolean>,
) : SelectSubClause05<T>(upstream, arrayOf(condition)) {
    override fun keyword() = "HAVING"
}
