@file:Suppress("FunctionName", "ClassName", "PropertyName")

package com.github.parisoft.orkk.dsl.pg

// -- Set Returning Functions

fun generate_series(
    start: Number,
    stop: Number,
): FunctionCall<Set<Int>> = FunctionCall<Set<Int>>(::generate_series.name, start, stop)

// -- Sampling methods

fun BERNOULLI(n: Number): FunctionCall<Float> = FunctionCall<Float>(::BERNOULLI.name, n)
