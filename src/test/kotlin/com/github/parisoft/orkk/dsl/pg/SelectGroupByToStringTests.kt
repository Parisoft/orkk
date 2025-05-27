package com.github.parisoft.orkk.dsl.pg

import com.diffplug.selfie.coroutines.expectSelfie
import io.kotest.core.spec.style.StringSpec

class SelectGroupByToStringTests :
    StringSpec({

        val one = 1.literal()
        val two = 2.literal()

        // -- Group and Expressions
        "select 1 group by ()" {
            val q1 = SELECT(one) GROUP BY()
            expectSelfie(q1.toString()).toMatchDisk()
            val q2 = SELECT(one) GROUP BY(emptyGroup())
            expectSelfie(q2.toString()).toMatchDisk()
        }
        "select 1 group by 1" {
            val q = SELECT(one) GROUP BY(one)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by (1)" {
            val q = SELECT(one) GROUP BY(groupOf(one))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by 1, 2" {
            val q = SELECT(one, two) GROUP BY(one, two)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by (1, 2)" {
            val q = SELECT(one, two) GROUP BY(groupOf(one, two))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by 1, (2)" {
            val q = SELECT(one, two) GROUP BY(one, groupOf(two))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by 1, (2), ()" {
            val q = SELECT(one, two) GROUP BY(one, groupOf(two), emptyGroup())
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Rollup
        "select 1 group by rollup (1)" {
            val q = SELECT(one) GROUP BY ROLLUP (one)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by rollup (1, 2)" {
            val q1 = SELECT(one, two) GROUP BY ROLLUP listOf(one, two)
            expectSelfie(q1.toString()).toMatchDisk()
            val q2 = SELECT(one, two) GROUP BY(ROLLUP(one, two))
            expectSelfie(q2.toString()).toMatchDisk()
        }
        "select 1 group by rollup ((1))" {
            val q = SELECT(one) GROUP BY ROLLUP groupOf(one)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by rollup ((1, 2))" {
            val q = SELECT(one, two) GROUP BY ROLLUP groupOf(one, two)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by rollup ((1), 2)" {
            val q1 = SELECT(one, two) GROUP BY ROLLUP listOf(groupOf(one), two)
            expectSelfie(q1.toString()).toMatchDisk()
            val q2 = SELECT(one, two) GROUP BY(ROLLUP(groupOf(one), two))
            expectSelfie(q2.toString()).toMatchDisk()
        }
        // -- Cube
        "select 1 group by cube (1)" {
            val q = SELECT(one) GROUP BY CUBE (one)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by cube (1, 2)" {
            val q1 = SELECT(one, two) GROUP BY CUBE listOf(one, two)
            expectSelfie(q1.toString()).toMatchDisk()
            val q2 = SELECT(one, two) GROUP BY(CUBE(one, two))
            expectSelfie(q2.toString()).toMatchDisk()
        }
        "select 1 group by cube ((1))" {
            val q = SELECT(one) GROUP BY CUBE groupOf(one)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by cube ((1, 2))" {
            val q = SELECT(one, two) GROUP BY CUBE groupOf(one, two)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by cube ((1), 2)" {
            val q1 = SELECT(one, two) GROUP BY CUBE listOf(groupOf(one), two)
            expectSelfie(q1.toString()).toMatchDisk()
            val q2 = SELECT(one, two) GROUP BY(CUBE(groupOf(one), two))
            expectSelfie(q2.toString()).toMatchDisk()
        }
        // -- Grouping Sets
        "select 1 group by grouping sets (())" {
            val q = SELECT(one) GROUP BY GROUPING SETS(emptyGroup())
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by grouping sets (1)" {
            val q = SELECT(one) GROUP BY GROUPING SETS(one)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by grouping sets ((1))" {
            val q = SELECT(one) GROUP BY GROUPING SETS(groupOf(one))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by grouping sets (rollup (1))" {
            val q = SELECT(one) GROUP BY GROUPING SETS(ROLLUP(one))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by grouping sets (cube (1))" {
            val q = SELECT(one) GROUP BY GROUPING SETS(CUBE(one))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by grouping sets (grouping sets (1))" {
            val q = SELECT(one) GROUP BY GROUPING SETS(SETS(one))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by grouping sets ((), 1, (1), rollup (1), cube(1), grouping sets (1))" {
            val q = SELECT(one) GROUP BY GROUPING SETS(emptyGroup(), one, groupOf(one), ROLLUP(one), CUBE(one), SETS(one))
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Mix
        "select 1 group by (), 1, (1), rollup (1), cube(1), grouping sets (1)" {
            val q1 = SELECT(one) GROUP BY(emptyGroup(), one, groupOf(one), ROLLUP(one), CUBE(one), SETS(one))
            expectSelfie(q1.toString()).toMatchDisk()
            val q2 = SELECT(one) GROUP BY(emptyGroup(), one, groupOf(one), ROLLUP(one), CUBE(one), GROUPING SETS (one))
            expectSelfie(q2.toString()).toMatchDisk()
        }
    })
