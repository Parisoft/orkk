@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl

open class Expression<T> {
    companion object {
        const val IDENT = "  "
        val LF = System.lineSeparator()
    }

    val aliases = mutableListOf<Alias>()

    internal fun indent(code: String) = code.lines().joinToString(LF) { "${IDENT}$it" }

    internal fun unindent(code: String) = code.replace(IDENT, "").replace(LF, " ")

    internal fun parenthesize(code: String) = "(${LF}${indent(code)}$LF)"

    internal fun String.withAlias() = if (aliases.isEmpty()) this else "$this ${aliases.last()}"

    internal open fun toStatement() = Statement(query = toString(), values = emptyList())
}

open class Table(
    val `@schema`: String? = null,
    val `@name`: String,
) : Expression<Any>() {
    val `*` = Field<Any>(this, "*")

    open fun alias(name: String): Table = Table(`@name` = name)

    internal fun copy(): Table = Table(`@schema`, `@name`)

    operator fun invoke(vararg fields: String) = Alias(toString(), fields.toList())

    operator fun invoke(vararg fields: Field<*>) = Alias(toString(), fields.map { it.name })

    operator fun <T> get(field: Field<T>) = Field<T>(this, field.name)

    operator fun <T> get(fieldName: String) = Field<T>(this, fieldName)

    override fun toString() = (if (`@schema` != null) "\"${`@schema`}\".${`@name`}" else `@name`).withAlias()
}

data class Field<T>(
    val table: Table? = null,
    val name: String,
) : Expression<T>() {
    override fun toString() = (if (table != null) "$table.$name" else name).withAlias()
}

data class Alias(
    val name: String,
    val fields: List<String> = emptyList(),
) {
    override fun toString() = if (fields.isEmpty()) "AS $name" else "AS $name(${fields.joinToString(", ")})"
}

abstract class Clause<T>(
    open val upstream: Clause<T>? = null,
    open val expressions: Array<out Expression<*>> = emptyArray(),
) : Expression<T>() {
    internal var inner = false

    internal abstract fun keyword(): String

    internal open fun branchToStatement() = expressions.map { it.toStatement() }.reduce { s1, s2 -> s1 + s2 }

    internal open fun selfToStatement(
        downstream: Statement?,
        branchStatement: Statement,
    ): Pair<Statement, Alias?> {
        val (thisString, alias) = selfToString(downstream?.query, branchStatement.query)
        return Statement(query = unindent(thisString), values = branchStatement.values + downstream?.values.orEmpty()) to alias
    }

    internal fun toStatementFrom(downstream: Statement?): Statement {
        val branchStatement = branchToStatement()
        val (thisStatement, alias) = selfToStatement(downstream, branchStatement)
        val allStatement = upstream?.toStatementFrom(thisStatement) ?: thisStatement
        return allStatement.let { if (alias != null) Statement("${parenthesize(it.query)} $alias", values = it.values) else it }
    }

    override fun toStatement() = toStatementFrom(null)

    internal open fun branchToString(): String =
        expressions.joinToString(",$LF") { branch ->
            branch.toString().let {
                if (branch is SelectSubClause && !it.trim().startsWith("(")) {
                    parenthesize(it)
                } else {
                    it
                }
            }
        }

    internal open fun selfToString(
        downstream: String?,
        branchString: String,
    ) = (
        if (branchString.isEmpty()) {
            keyword()
        } else if (branchString.lines().size > 1) {
            "${keyword()}$LF${indent(branchString)}"
        } else {
            "${keyword()} $branchString"
        }
    ).let {
        if (downstream != null) {
            if (aliases.isEmpty()) {
                "$it$LF$downstream" to null
            } else {
                "$it ${aliases.last()}$LF$downstream" to null
            }
        } else if (inner) {
            it to if (aliases.isNotEmpty()) aliases.last() else null
        } else if (aliases.isNotEmpty()) {
            "$it ${aliases.last()}" to null
        } else {
            it to null
        }
    }

    internal open fun toStringFrom(downstream: String?): String {
        val branchString = branchToString()
        val (thisString, alias) = selfToString(downstream, branchString)
        val allString = upstream?.toStringFrom(thisString) ?: thisString
        return if (alias != null) "${parenthesize(allString)} $alias" else allString
    }

    override fun toString() = toStringFrom(null)
}

data class Statement(
    val query: String,
    val values: List<Literal<*>>,
) {
    operator fun plus(other: Statement?) =
        if (other == null) {
            this
        } else {
            Statement(
                query = this.query + if (other.query.isBlank()) "" else " ${other.query}",
                values = this.values + other.values,
            )
        }
}

fun <T> fieldOf(name: String) = Field<T>(name = name)

// -- Literal

abstract class Literal<T>(
    open val value: T,
) : Expression<T>() {
    override fun toString() = value.toString().withAlias()
}

class NumberLiteral<Number>(
    value: Number,
) : Literal<Number>(value)

class ByteLiteral(
    value: Byte,
) : Literal<Byte>(value)

class ShortLiteral(
    value: Short,
) : Literal<Short>(value)

class IntLiteral(
    value: Int,
) : Literal<Int>(value)

class LongLiteral(
    value: Long,
) : Literal<Long>(value)

class FloatLiteral(
    value: Float,
) : Literal<Float>(value)

class DoubleLiteral(
    value: Double,
) : Literal<Double>(value)

class StringLiteral(
    value: String,
) : Literal<String>(value) {
    override fun toString() = "'$value'".withAlias()
}

class BoolLiteral(
    value: Boolean,
) : Literal<Boolean>(value)

class AnyLiteral(
    value: Any,
) : Literal<Any>(value)

class NullLiteral : Literal<Unit>(value = Unit) {
    override fun toString() = "NULL".withAlias()
}

fun Number.literal() = NumberLiteral(this)

fun Byte.literal() = ByteLiteral(this)

fun Short.literal() = ShortLiteral(this)

fun Int.literal() = IntLiteral(this)

fun Long.literal() = LongLiteral(this)

fun Float.literal() = FloatLiteral(this)

fun Double.literal() = DoubleLiteral(this)

fun String.literal() = StringLiteral(this)

fun Boolean.literal() = BoolLiteral(this)

fun Any?.literal() = if (this == null) NullLiteral() else AnyLiteral(this)

// -- Functions

open class FunctionCall<R>(
    val name: String,
    vararg val args: Any,
) : Expression<R>() {
    override fun toString() = "$name(${args.joinToString(", ")})".withAlias()
}

// -- Alias

infix fun <T : Table> T.AS(table: Table) = copy().apply { aliases += Alias(table.`@name`) } as T

infix fun <T : Table> T.AS(name: String) = copy().apply { aliases += Alias(name) } as T

infix fun <T : Table> T.AS(alias: Alias) = copy().apply { aliases += alias } as T

infix fun <T : Field<*>> T.AS(field: Field<*>) = copy().apply { aliases += Alias(table.toString()) } as T

infix fun <T : Field<*>> T.AS(name: String) = copy().apply { aliases += Alias(name) } as T

infix fun <T : Field<*>> T.AS(alias: Alias) = copy().apply { aliases += alias } as T

infix fun <T : Expression<*>> T.AS(table: Table) = apply { aliases += Alias(table.`@name`) }

infix fun <T : Expression<*>> T.AS(field: Field<*>) = apply { aliases += Alias(field.name) }

infix fun <T : Expression<*>> T.AS(name: String) = apply { aliases += Alias(name) }

infix fun <T : Expression<*>> T.AS(alias: Alias) = apply { aliases += alias }

infix fun <T : Expression<*>> T.AS(fields: Collection<*>) =
    apply {
        aliases +=
            when (fields.firstOrNull()) {
                is Field<*> -> Alias(name = "", fields.map { (it as Field<*>).name })
                is Table -> Alias(name = "", fields.map { (it as Table).`@name` })
                is Alias -> Alias(name = "", fields.map { (it as Alias).name })
                else -> Alias(name = "", fields.map { it.toString() })
            }
    }

// -- Helpers
@Suppress("UNCHECKED_CAST")
fun Collection<Expression<*>>.toAnyArray() = toTypedArray() as Array<Expression<Any>>
