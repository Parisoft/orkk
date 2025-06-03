@file:Suppress("FunctionName", "ClassName", "PropertyName")

package com.github.parisoft.orkk.dsl

// -- Set Returning Functions

fun generate_series(
    start: Number,
    stop: Number,
): FunctionCall<Set<Int>> = FunctionCall(::generate_series.name, start, stop)

// -- Sampling methods

fun BERNOULLI(n: Number): FunctionCall<Float> = FunctionCall(::BERNOULLI.name, n)

// -- Window Functions

fun rank(): WindowFunctionCall<Long> = WindowFunctionCall(::rank.name)
