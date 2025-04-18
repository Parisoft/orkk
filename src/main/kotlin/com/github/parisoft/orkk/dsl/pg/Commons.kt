@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl.pg

open class Expression<T> {
    val aliases = mutableListOf<Alias>()

    protected fun String.withAlias() = if (aliases.isEmpty()) this else "$this ${aliases.last()}"
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
) : Expression<T>() {
    companion object {
        const val IDENT = "  "
        val LF = System.lineSeparator()
    }

    protected fun ident(code: String) = code.lines().joinToString(LF) { "${IDENT}$it" }

    protected fun parenthesize(code: String) = "(${LF}${ident(code)}$LF)"

    internal open fun toStringFrom(downstream: String?): String {
        val thisString = "${this::class.simpleName} $downstream"
        return upstream?.toStringFrom(thisString) ?: thisString
    }

    override fun toString() = toStringFrom(null)
}

fun <T> fieldOf(name: String) = Field<T>(name = name)

// -- Literal

abstract class Literal<T>(
    open val value: T,
) : Expression<T>() {
    override fun toString() = value.toString().withAlias()
}

class NumberLiteral(
    value: Number,
) : Literal<Number>(value)

class StringLiteral(
    value: String,
) : Literal<String>(value) {
    override fun toString() = "'$value'".withAlias()
}

class BoolLiteral(
    value: Boolean,
) : Literal<Boolean>(value)

fun Number.literal() = NumberLiteral(this)

fun String.literal() = StringLiteral(this)

fun Boolean.literal() = BoolLiteral(this)

// -- Functions

class FunctionCall<R>(
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
