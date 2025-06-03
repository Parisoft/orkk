@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl

data class OrderByDirectionExpression<T>(
    val expression: Expression<T>,
    val direction: String,
) : Expression<T>() {
    override fun toString() = "$expression $direction"

    infix fun USING(gt: GT) = OrderByUsingExpression(this, ">")

    infix fun USING(lt: LT) = OrderByUsingExpression(this, "<")

    infix fun NULLS(first: FIRST) = OrderByNullsExpression(this, "FIRST")

    infix fun NULLS(last: LAST) = OrderByNullsExpression(this, "LAST")
}

data class OrderByUsingExpression<T>(
    val expression: Expression<T>,
    val operator: String,
) : Expression<T>() {
    override fun toString() = "$expression USING $operator"

    infix fun NULLS(first: FIRST) = OrderByNullsExpression(this, "FIRST")

    infix fun NULLS(last: LAST) = OrderByNullsExpression(this, "LAST")
}

data class OrderByNullsExpression<T>(
    val expression: Expression<T>,
    val nulls: String,
) : Expression<T>() {
    override fun toString() = "$expression NULLS $nulls"
}

val <T> Expression<T>.ASC: OrderByDirectionExpression<T>
    get() = OrderByDirectionExpression(this, "ASC")

val <T> Expression<T>.DESC: OrderByDirectionExpression<T>
    get() = OrderByDirectionExpression(this, "DESC")

infix fun <T> Expression<T>.USING(gt: GT) = OrderByUsingExpression(this, ">")

infix fun <T> Expression<T>.USING(lt: LT) = OrderByUsingExpression(this, "<")

infix fun <T> Expression<T>.NULLS(first: FIRST) = OrderByNullsExpression(this, "FIRST")

infix fun <T> Expression<T>.NULLS(last: LAST) = OrderByNullsExpression(this, "LAST")

object GT

object LT

object NULLS

object FIRST

object LAST

fun ASC(expression: Expression<*>) = expression.ASC

fun DESC(expression: Expression<*>) = expression.DESC

class SelectOrderByClause<T>(
    override val upstream: Clause<T>,
    expressions: Array<out Expression<*>>,
) : SelectSubClause08<T>(upstream, expressions) {
    override fun keyword() = "ORDER BY"
}
