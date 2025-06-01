@file:Suppress("FunctionName", "ClassName", "PropertyName", "DANGEROUS_CHARACTERS")

package com.github.parisoft.orkk.dsl

interface OrderByExpression

interface UsingOperator

data class OrderByDirectionExpression<T>(
    val expression: Expression<T>,
    val direction: String,
) : Expression<T>(),
    OrderByExpression {
    override fun toString() = "$expression $direction"
}

data class OrderByUsingExpression<T>(
    val expression: Expression<T>,
    val operator: UsingOperator,
) : Expression<T>(),
    OrderByExpression {
    override fun toString() = "$expression USING $operator"
}

val <T> Expression<T>.ASC: OrderByDirectionExpression<T>
    get() = OrderByDirectionExpression(this, "ASC")

val <T> Expression<T>.DESC: OrderByDirectionExpression<T>
    get() = OrderByDirectionExpression(this, "DESC")

infix fun <T> Expression<T>.USING(operator: UsingOperator) = OrderByUsingExpression(this, operator)

// fun <T> Expression<T>.USING(operator: UsingOperator) = OrderByUsingExpression(this, operator)

fun ASC(expression: Expression<*>) = expression.ASC

fun DESC(expression: Expression<*>) = expression.DESC

class SelectOrderByClause<T>(
    override val upstream: Clause<T>,
    expressions: Array<out Expression<*>>,
) : SelectSubClause08<T>(upstream, expressions) {
    override fun keyword() = "ORDER BY"
}

// object `<` : UsingOperator {
//    override fun toString() = "<"
// }
//
// object `>` : UsingOperator {
//    override fun toString() = ">"
// }
