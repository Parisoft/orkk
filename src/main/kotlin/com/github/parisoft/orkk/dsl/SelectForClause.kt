@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl

class SelectForClause<T>(
    upstream: Clause<T>,
    val lockStrength: String,
) : SelectSubClause<T>(upstream) {
    override fun keyword() = "FOR $lockStrength"

    infix fun OF(references: Collection<Expression<*>>) = SelectForOfClause(this, references.toTypedArray())

    infix fun OF(reference: Expression<*>) = SelectForOfClause(this, arrayOf(reference))

    infix fun NO(wait: WAIT) = SelectForNoWaitClause(this)

    infix fun SKIP(locked: LOCKED) = SelectForSkipLockedClause(this)
}

abstract class SelectForSubClause<T>(
    upstream: Clause<T>,
    expressions: Array<out Expression<*>> = emptyArray(),
) : SelectSubClause<T>(upstream, expressions) {
    override fun selfToString(
        downstream: String?,
        branchString: String,
    ) = super.selfToString(downstream, branchString).let { (string, alias) ->
        ident(string) to alias
    }
}

class SelectForOfClause<T>(
    upstream: Clause<T>,
    references: Array<out Expression<*>>,
) : SelectForSubClause<T>(upstream, references) {
    override fun keyword() = "OF"

    infix fun NO(wait: WAIT) = SelectForNoWaitClause(this)

    infix fun SKIP(locked: LOCKED) = SelectForSkipLockedClause(this)
}

class SelectForNoWaitClause<T>(
    upstream: Clause<T>,
) : SelectForSubClause<T>(upstream) {
    override fun keyword() = "NOWAIT"
}

class SelectForSkipLockedClause<T>(
    upstream: Clause<T>,
) : SelectForSubClause<T>(upstream) {
    override fun keyword() = "SKIP LOCKED"
}

class SelectForNoKeyUpdateBuilder<T>(
    val upstream: Clause<T>,
) {
    infix fun KEY(update: UPDATE): SelectForClause<T> = SelectForClause(upstream, "NO KEY UPDATE")
}

object NO

object UPDATE

object SHARE

object KEY_SHARE

object WAIT

object LOCKED
