@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl

// -- Window Definitions

abstract class WindowDefinition<T>(
    upstream: Clause<T>? = null,
    val expressions: Array<out Expression<*>> = emptyArray(),
) : Clause<T>(upstream) {
    abstract fun keyword(): String

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
                    "${keyword()}$LF${ident(branchString)}"
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
) : WindowDefinition03<T>(upstream, expressions)

abstract class WindowDefinition01<T>(
    upstream: Clause<T>? = null,
    expressions: Array<out Expression<*>> = emptyArray(),
) : WindowDefinition02<T>(upstream, expressions)

class WindowPartitionByDefinition<T>(
    expressions: Array<out Expression<*>> = emptyArray(),
) : WindowDefinition01<T>(null, expressions) {
    override fun keyword() = "PARTITION BY"
}

class WindowFrameDefinition<T>(
    upstream: Clause<T>? = null,
    val name: String,
    val start: FrameStart,
    val end: FrameEnd? = null,
) : WindowDefinition03<T>(upstream) {
    override fun keyword() =
        if (end != null) {
            "FRAME $name BETWEEN $start AND $end"
        } else {
            "FRAME $name $start"
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
) : WindowDefinition03<T>(upstream) {
    override fun keyword() =
        if (end != null) {
            "FRAME $name BETWEEN $start AND $end $exclusion"
        } else {
            "FRAME $name $start $exclusion"
        }
}

class SelectWindowClause<T>(
    upstream: Clause<T>?,
    windows: Array<SelectWindowSubClause<*>>,
) : SelectSubClause05<T>(upstream, windows) {
    override fun keyword() = "WINDOW"
}

// -- Window Clauses

class SelectWindowSubClause<T>(
    upstream: Clause<T>?,
    definitions: Array<WindowDefinition<*>>,
    val name: String,
) : SelectSubClause05<T>(upstream, definitions) {
    override fun keyword() = name
}

class SelectWindowSingleClause<T>(
    upstream: Clause<T>? = null,
    val name: String,
) : Clause<T>(upstream) {
    infix fun AS(definition: WindowDefinition<*>) = SelectWindowSubClause(upstream, arrayOf(definition), name)
}

// -- Frame Clause

infix fun String.AS(definition: WindowDefinition<*>) = SelectWindowSubClause<Any>(null, arrayOf(definition), this)

object PARTITION {
    infix fun BY(expression: Expression<*>) = WindowPartitionByDefinition<Any>(arrayOf(expression))

    infix fun BY(expressions: Collection<Expression<*>>) = WindowPartitionByDefinition<Any>(expressions.toTypedArray())
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

data class Preceding(
    val offset: Expression<*>,
) : FrameStart,
    FrameEnd {
    override fun toString() = "$offset PRECEDING"
}

data class Following(
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
    operator fun invoke(offset: Expression<*>) = Preceding(offset)

    operator fun invoke(offset: Number) = Preceding(offset.literal())
}

object FOLLOWING {
    operator fun invoke(offset: Expression<*>) = Following(offset)

    operator fun invoke(offset: Number) = Following(offset.literal())
}

object GROUP : FrameExclusion

object TIES : FrameExclusion

object NO_OTHERS : FrameExclusion

object BETWEEN {
    operator fun invoke(startToEnd: Pair<FrameStart, FrameEnd>) =
        Between(
            start = startToEnd.first,
            end = startToEnd.second,
        )
}
