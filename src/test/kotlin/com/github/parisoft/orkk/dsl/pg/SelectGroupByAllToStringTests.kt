package com.github.parisoft.orkk.dsl.pg

import com.diffplug.selfie.coroutines.expectSelfie
import io.kotest.core.spec.style.StringSpec

class SelectGroupByAllToStringTests :
    StringSpec({

        val one = 1.literal()
        val two = 2.literal()

        // -- Group and Expressions
        "select 1 group by all ()" {
            val q = SELECT(one) GROUP BY ALL emptyGroup()
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by all 1" {
            val q = SELECT(one) GROUP BY ALL one
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by all (1)" {
            val q = SELECT(one) GROUP BY ALL groupOf(one)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by all 1, 2" {
            val q = SELECT(one, two) GROUP BY ALL listOf(one, two)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by all (1, 2)" {
            val q = SELECT(one, two) GROUP BY ALL listOf(groupOf(one, two))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by all 1, (2)" {
            val q = SELECT(one, two) GROUP BY ALL listOf(one, groupOf(two))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by all 1, (2), ()" {
            val q = SELECT(one, two) GROUP BY ALL listOf(one, groupOf(two), emptyGroup())
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Rollup
        "select 1 group by all rollup (1)" {
            val q = SELECT(one) GROUP BY ALL ROLLUP(one)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by all rollup (1, 2)" {
            val q = SELECT(one, two) GROUP BY ALL ROLLUP(one, two)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by all rollup ((1))" {
            val q = SELECT(one) GROUP BY ALL ROLLUP(groupOf(one))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by all rollup ((1, 2))" {
            val q = SELECT(one, two) GROUP BY ALL ROLLUP(groupOf(one, two))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by all rollup ((1), 2)" {
            val q = SELECT(one, two) GROUP BY ALL ROLLUP(groupOf(one), two)
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Cube
        "select 1 group by all cube (1)" {
            val q = SELECT(one) GROUP BY ALL CUBE(one)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by all cube (1, 2)" {
            val q = SELECT(one, two) GROUP BY ALL CUBE(one, two)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by all cube ((1))" {
            val q = SELECT(one) GROUP BY ALL CUBE(groupOf(one))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by all cube ((1, 2))" {
            val q = SELECT(one, two) GROUP BY ALL CUBE(groupOf(one, two))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by all cube ((1), 2)" {
            val q = SELECT(one, two) GROUP BY ALL CUBE(groupOf(one), two)
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Grouping Sets
        "select 1 group by all grouping sets (())" {
            val q = SELECT(one) GROUP BY ALL SETS(emptyGroup())
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by all grouping sets (1)" {
            val q = SELECT(one) GROUP BY ALL SETS(one)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by all grouping sets ((1))" {
            val q = SELECT(one) GROUP BY ALL SETS(groupOf(one))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by all grouping sets (rollup (1))" {
            val q = SELECT(one) GROUP BY ALL SETS(ROLLUP(one))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by all grouping sets (cube (1))" {
            val q = SELECT(one) GROUP BY ALL SETS(CUBE(one))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by all grouping sets (grouping sets (1))" {
            val q = SELECT(one) GROUP BY ALL SETS(SETS(one))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by all grouping sets ((), 1, (1), rollup (1), cube(1), grouping sets (1))" {
            val q = SELECT(one) GROUP BY ALL SETS(emptyGroup(), one, groupOf(one), ROLLUP(one), CUBE(one), SETS(one))
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Mix
        "select 1 group by all (), 1, (1), rollup (1), cube(1), grouping sets (1)" {
            val q = SELECT(one) GROUP BY ALL listOf(emptyGroup(), one, groupOf(one), ROLLUP(one), CUBE(one), SETS(one))
            expectSelfie(q.toString()).toMatchDisk()
        }
    })
