@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl

// -- Window Definitions

abstract class WindowDefinition<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>> = emptyArray(),
) : Clause<T>(upstream, expressions) {
    override fun toStringFrom(downstream: String?) =
        expressions
            .joinToString(",$LF") { branch ->
                branch.toString().let {
                    if (branch is SelectSubClause && !it.trim().startsWith("(")) {
                        parenthesize(it)
                    } else {
                        it
                    }
                }
            }.let { branchString ->
                if (branchString.lines().size > 1) {
                    "${keyword()}$LF${indent(branchString)}"
                } else {
                    "${keyword()} $branchString"
                }
            }.let { thisString ->
                if (downstream != null) {
                    "$thisString$LF$downstream"
                } else {
                    thisString
                }
            }.let { allString ->
                upstream?.toStringFrom(allString) ?: allString
            }
}

abstract class WindowDefinition03<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>> = emptyArray(),
) : WindowDefinition<T>(upstream, expressions) {
    infix fun RANGE(frameStart: FrameStart) =
        WindowFrameDefinition(
            upstream = this,
            name = "RANGE",
            start = frameStart,
        )

    infix fun RANGE(between: Between) =
        WindowFrameDefinition(
            upstream = this,
            name = "RANGE",
            start = between.start,
            end = between.end,
        )

    infix fun ROWS(frameStart: FrameStart) =
        WindowFrameDefinition(
            upstream = this,
            name = "ROWS",
            start = frameStart,
        )

    infix fun ROWS(between: Between) =
        WindowFrameDefinition(
            upstream = this,
            name = "ROWS",
            start = between.start,
            end = between.end,
        )

    infix fun GROUPS(frameStart: FrameStart) =
        WindowFrameDefinition(
            upstream = this,
            name = "GROUPS",
            start = frameStart,
        )

    infix fun GROUPS(between: Between) =
        WindowFrameDefinition(
            upstream = this,
            name = "GROUPS",
            start = between.start,
            end = between.end,
        )
}

abstract class WindowDefinition02<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>> = emptyArray(),
) : WindowDefinition03<T>(upstream, expressions) {
    infix fun ORDER(by: ByExpressions) = WindowOrderByDefinition(this, by.expressions)
}

abstract class WindowDefinition01<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>> = emptyArray(),
) : WindowDefinition02<T>(upstream, expressions) {
    infix fun PARTITION(by: ByExpressions) = WindowPartitionByDefinition(this, by.expressions)
}

class WindowReferenceDefinition<T>(
    val name: String,
) : WindowDefinition01<T>() {
    override fun keyword() = name
}

class WindowPartitionByDefinition<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>> = emptyArray(),
) : WindowDefinition02<T>(upstream, expressions) {
    override fun keyword() = "PARTITION BY"
}

class WindowOrderByDefinition<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>>,
) : WindowDefinition03<T>(upstream, expressions) {
    override fun keyword() = "ORDER BY"
}

class WindowFrameDefinition<T>(
    upstream: Clause<T>? = null,
    val name: String,
    val start: FrameStart,
    val end: FrameEnd? = null,
) : WindowDefinition<T>(upstream) {
    override fun keyword() =
        if (end != null) {
            "$name BETWEEN $start AND $end"
        } else {
            "$name $start"
        }

    infix fun EXCLUDE(frameExclusion: FrameExclusion) =
        WindowFrameWithExclusionDefinition(
            upstream = upstream,
            name = name,
            start = start,
            end = end,
            exclusion = frameExclusion,
        )
}

class WindowFrameWithExclusionDefinition<T>(
    upstream: Clause<T>? = null,
    val name: String,
    val start: FrameStart,
    val end: FrameEnd? = null,
    var exclusion: FrameExclusion,
) : WindowDefinition<T>(upstream) {
    override fun keyword() =
        if (end != null) {
            "$name BETWEEN $start AND $end $exclusion"
        } else {
            "$name $start $exclusion"
        }
}

infix fun String.PARTITION(by: ByExpressions) = WindowReferenceDefinition<Any>(this) PARTITION by

infix fun String.ORDER(by: ByExpressions) = WindowReferenceDefinition<Any>(this) ORDER by

infix fun String.RANGE(frameStart: FrameStart) = WindowReferenceDefinition<Any>(this) RANGE frameStart

infix fun String.RANGE(between: Between) = WindowReferenceDefinition<Any>(this) RANGE between

infix fun String.ROWS(frameStart: FrameStart) = WindowReferenceDefinition<Any>(this) ROWS frameStart

infix fun String.ROWS(between: Between) = WindowReferenceDefinition<Any>(this) ROWS between

infix fun String.GROUPS(frameStart: FrameStart) = WindowReferenceDefinition<Any>(this) GROUPS frameStart

infix fun String.GROUPS(between: Between) = WindowReferenceDefinition<Any>(this) GROUPS between

// -- Window Clauses

class SelectWindowClause<T>(
    upstream: Clause<T>?,
    windows: Array<WindowClause<*>>,
) : SelectSubClause06<T>(upstream, windows) {
    override fun keyword() = "WINDOW"

    override fun branchToString() = expressions.joinToString(", $LF")

    override fun selfToString(
        downstream: String?,
        branchString: String,
    ) = if (branchString.lines().size > 1) {
        "${keyword()}$LF${indent(branchString)}"
    } else {
        "${keyword()} $branchString"
    }.let { thisString ->
        if (downstream != null) {
            "$thisString$LF$downstream" to null
        } else {
            thisString to null
        }
    }
}

class WindowClause<T>(
    upstream: Clause<T>?,
    definitions: Array<WindowDefinition<*>>,
    val name: String,
) : SelectSubClause06<T>(upstream, definitions) {
    override fun keyword() = "$name AS"

    override fun selfToString(
        downstream: String?,
        branchString: String,
    ) = if (branchString.lines().size > 1) {
        "${keyword()} ${parenthesize(branchString)}"
    } else {
        "${keyword()} ($branchString)"
    }.let { thisString ->
        if (downstream != null) {
            "$thisString$LF$downstream" to null
        } else {
            thisString to null
        }
    }
}

class SelectWindowBuilder<T>(
    val upstream: Clause<T>? = null,
    val name: String,
) {
    infix fun AS(definition: WindowDefinition<*>) =
        SelectWindowClause(upstream, arrayOf(WindowClause<Any>(null, arrayOf(definition), name)))
}

// -- Frame Clause

infix fun String.AS(definition: WindowDefinition<*>) = WindowClause<Any>(null, arrayOf(definition), this)

object PARTITION {
    infix fun BY(expression: Expression<*>) = WindowPartitionByDefinition<Any>(null, arrayOf(expression))

    infix fun BY(expressions: Collection<Expression<*>>) = WindowPartitionByDefinition<Any>(null, expressions.toTypedArray())
}

object ORDER {
    infix fun BY(expression: Expression<*>) = WindowOrderByDefinition<Any>(null, arrayOf(expression))

    infix fun BY(expressions: Collection<Expression<*>>) = WindowOrderByDefinition<Any>(null, expressions.toTypedArray())
}

object RANGE {
    operator fun invoke(frameStart: FrameStart) =
        WindowFrameDefinition<Any>(
            name = "RANGE",
            start = frameStart,
        )

    infix fun BETWEEN(startToEnd: Pair<FrameStart, FrameEnd>) =
        WindowFrameDefinition<Any>(
            name = "RANGE",
            start = startToEnd.first,
            end = startToEnd.second,
        )
}

object ROWS {
    operator fun invoke(frameStart: FrameStart) =
        WindowFrameDefinition<Any>(
            name = "ROWS",
            start = frameStart,
        )

    infix fun BETWEEN(startToEnd: Pair<FrameStart, FrameEnd>) =
        WindowFrameDefinition<Any>(
            name = "ROWS",
            start = startToEnd.first,
            end = startToEnd.second,
        )
}

object GROUPS {
    operator fun invoke(frameStart: FrameStart) =
        WindowFrameDefinition<Any>(
            name = "GROUPS",
            start = frameStart,
        )

    infix fun BETWEEN(startToEnd: Pair<FrameStart, FrameEnd>) =
        WindowFrameDefinition<Any>(
            name = "GROUPS",
            start = startToEnd.first,
            end = startToEnd.second,
        )
}

interface FrameStart {
    infix fun AND(end: FrameEnd) = this to end
}

interface FrameEnd

interface FrameExclusion

data class PrecedingFrame(
    val offset: Expression<*>,
) : FrameStart,
    FrameEnd {
    override fun toString() = "$offset PRECEDING"
}

data class FollowingFrame(
    val offset: Expression<*>,
) : FrameStart,
    FrameEnd {
    override fun toString() = "$offset FOLLOWING"
}

data class Between(
    val start: FrameStart,
    val end: FrameEnd,
)

object UNBOUNDED_PRECEDING : FrameStart, FrameEnd {
    override fun toString() = "UNBOUNDED PRECEDING"
}

object UNBOUNDED_FOLLOWING : FrameStart, FrameEnd {
    override fun toString() = "UNBOUNDED FOLLOWING"
}

object CURRENT_ROW : FrameStart, FrameEnd, FrameExclusion {
    override fun toString() = "CURRENT ROW"
}

object PRECEDING {
    operator fun invoke(offset: Expression<*>) = PrecedingFrame(offset)

    operator fun <N : Number> invoke(offset: N) = PrecedingFrame(offset.literal())
}

object FOLLOWING {
    operator fun invoke(offset: Expression<*>) = FollowingFrame(offset)

    operator fun <N : Number> invoke(offset: N) = FollowingFrame(offset.literal())
}

object GROUP : FrameExclusion {
    override fun toString() = "GROUP"
}

object TIES : FrameExclusion {
    override fun toString() = "TIES"
}

object NO_OTHERS : FrameExclusion {
    override fun toString() = "NO OTHERS"
}

object BETWEEN {
    operator fun invoke(startToEnd: Pair<FrameStart, FrameEnd>) =
        Between(
            start = startToEnd.first,
            end = startToEnd.second,
        )
}

// -- Window Expressions

class WindowFunctionCall<T>(
    name: String,
    vararg args: Any,
) : FunctionCall<T>(name, *args) {
    infix fun FILTER(where: Expression<Boolean>) = WindowFilterExpression(this, where)

    infix fun OVER(definition: WindowDefinition<*>) = WindowOverExpression<T>(this, definition)

    infix fun OVER(definition: String) = WindowOverExpression<T>(this, WindowReferenceDefinition<Any>(definition))
}

class WindowFilterExpression<T>(
    val function: WindowFunctionCall<T>,
    val where: Expression<Boolean>,
) : Expression<T>() {
    infix fun OVER(definition: WindowDefinition<*>) = WindowOverExpression<T>(this, definition)

    infix fun OVER(definition: String) = WindowOverExpression<T>(this, WindowReferenceDefinition<Any>(definition))

    override fun toString() = "$function FILTER (WHERE $where)"
}

class WindowOverExpression<T>(
    val upstream: Expression<*>,
    val definition: WindowDefinition<*>,
) : Expression<T>() {
    override fun toString() =
        definition.toString().let { definitionString ->
            if (definitionString.lines().size > 1) {
                "$upstream OVER ${parenthesize(definitionString)}"
            } else if (definition is WindowReferenceDefinition) {
                "$upstream OVER $definitionString"
            } else {
                "$upstream OVER ($definitionString)"
            }
        }
}

object WHERE {
    operator fun invoke(condition: Expression<Boolean>) = condition

    operator fun invoke(condition: Boolean) = condition.literal()
}
