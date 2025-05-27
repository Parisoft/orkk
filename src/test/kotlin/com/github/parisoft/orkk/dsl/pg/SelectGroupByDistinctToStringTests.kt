package com.github.parisoft.orkk.dsl.pg

import com.diffplug.selfie.coroutines.expectSelfie
import io.kotest.core.spec.style.StringSpec

class SelectGroupByDistinctToStringTests :
    StringSpec({

        val one = 1.literal()
        val two = 2.literal()

        // -- Group and Expressions
        "select 1 group by distinct ()" {
            val q = SELECT(one) GROUP BY DISTINCT emptyGroup()
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by distinct 1" {
            val q = SELECT(one) GROUP BY DISTINCT one
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by distinct (1)" {
            val q = SELECT(one) GROUP BY DISTINCT groupOf(one)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by distinct 1, 2" {
            val q = SELECT(one, two) GROUP BY DISTINCT listOf(one, two)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by distinct (1, 2)" {
            val q = SELECT(one, two) GROUP BY DISTINCT listOf(groupOf(one, two))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by distinct 1, (2)" {
            val q = SELECT(one, two) GROUP BY DISTINCT listOf(one, groupOf(two))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by distinct 1, (2), ()" {
            val q = SELECT(one, two) GROUP BY DISTINCT listOf(one, groupOf(two), emptyGroup())
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Rollup
        "select 1 group by distinct rollup (1)" {
            val q = SELECT(one) GROUP BY DISTINCT ROLLUP(one)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by distinct rollup (1, 2)" {
            val q = SELECT(one, two) GROUP BY DISTINCT ROLLUP(one, two)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by distinct rollup ((1))" {
            val q = SELECT(one) GROUP BY DISTINCT ROLLUP(groupOf(one))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by distinct rollup ((1, 2))" {
            val q = SELECT(one, two) GROUP BY DISTINCT ROLLUP(groupOf(one, two))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by distinct rollup ((1), 2)" {
            val q = SELECT(one, two) GROUP BY DISTINCT ROLLUP(groupOf(one), two)
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Cube
        "select 1 group by distinct cube (1)" {
            val q = SELECT(one) GROUP BY DISTINCT CUBE(one)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by distinct cube (1, 2)" {
            val q = SELECT(one, two) GROUP BY DISTINCT CUBE(one, two)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1 group by distinct cube ((1))" {
            val q = SELECT(one) GROUP BY DISTINCT CUBE(groupOf(one))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by distinct cube ((1, 2))" {
            val q = SELECT(one, two) GROUP BY DISTINCT CUBE(groupOf(one, two))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select 1, 2 group by distinct cube ((1), 2)" {
            val q = SELECT(one, two) GROUP BY DISTINCT CUBE(groupOf(one), two)
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Grouping Sets
        "select 1 group by distinct grouping sets (())" {
            val q1 = SELECT(one) GROUP BY DISTINCT SETS(emptyGroup())
            expectSelfie(q1.toString()).toMatchDisk()
            val q2 = SELECT(one) GROUP BY DISTINCT GROUPING SETS (emptyGroup())
            expectSelfie(q2.toString()).toMatchDisk()
        }
        "select 1 group by distinct grouping sets (1)" {
            val q1 = SELECT(one) GROUP BY DISTINCT SETS(one)
            expectSelfie(q1.toString()).toMatchDisk()
            val q2 = SELECT(one) GROUP BY DISTINCT GROUPING SETS (one)
            expectSelfie(q2.toString()).toMatchDisk()
        }
        "select 1 group by distinct grouping sets ((1))" {
            val q1 = SELECT(one) GROUP BY DISTINCT SETS(groupOf(one))
            expectSelfie(q1.toString()).toMatchDisk()
            val q2 = SELECT(one) GROUP BY DISTINCT GROUPING SETS (groupOf(one))
            expectSelfie(q2.toString()).toMatchDisk()
        }
        "select 1 group by distinct grouping sets (rollup (1))" {
            val q1 = SELECT(one) GROUP BY DISTINCT SETS(ROLLUP(one))
            expectSelfie(q1.toString()).toMatchDisk()
            val q2 = SELECT(one) GROUP BY DISTINCT GROUPING SETS (ROLLUP(one))
            expectSelfie(q2.toString()).toMatchDisk()
        }
        "select 1 group by distinct grouping sets (cube (1))" {
            val q1 = SELECT(one) GROUP BY DISTINCT SETS(CUBE(one))
            expectSelfie(q1.toString()).toMatchDisk()
            val q2 = SELECT(one) GROUP BY DISTINCT GROUPING SETS (CUBE(one))
            expectSelfie(q2.toString()).toMatchDisk()
        }
        "select 1 group by distinct grouping sets (grouping sets (1))" {
            val q1 = SELECT(one) GROUP BY DISTINCT SETS(SETS(one))
            expectSelfie(q1.toString()).toMatchDisk()
            val q2 = SELECT(one) GROUP BY DISTINCT GROUPING SETS (SETS(one))
            expectSelfie(q2.toString()).toMatchDisk()
            val q3 = SELECT(one) GROUP BY DISTINCT SETS(GROUPING SETS (one))
            expectSelfie(q3.toString()).toMatchDisk()
            val q4 = SELECT(one) GROUP BY DISTINCT GROUPING SETS (GROUPING SETS (one))
            expectSelfie(q4.toString()).toMatchDisk()
        }
        "select 1 group by distinct grouping sets ((), 1, (1), rollup (1), cube(1), grouping sets (1))" {
            val q1 = SELECT(one) GROUP BY DISTINCT SETS(emptyGroup(), one, groupOf(one), ROLLUP(one), CUBE(one), SETS(one))
            expectSelfie(q1.toString()).toMatchDisk()
            val q2 = SELECT(one) GROUP BY DISTINCT GROUPING SETS listOf(emptyGroup(), one, groupOf(one), ROLLUP(one), CUBE(one), SETS(one))
            expectSelfie(q2.toString()).toMatchDisk()
            val q3 = SELECT(one) GROUP BY DISTINCT SETS(emptyGroup(), one, groupOf(one), ROLLUP(one), CUBE(one), GROUPING SETS (one))
            expectSelfie(q3.toString()).toMatchDisk()
            val q4 =
                SELECT(one) GROUP BY DISTINCT GROUPING SETS
                    listOf(emptyGroup(), one, groupOf(one), ROLLUP(one), CUBE(one), GROUPING SETS (one))
            expectSelfie(q4.toString()).toMatchDisk()
        }
        // -- Mix
        "select 1 group by distinct (), 1, (1), rollup (1), cube(1), grouping sets (1)" {
            val q1 = SELECT(one) GROUP BY DISTINCT listOf(emptyGroup(), one, groupOf(one), ROLLUP(one), CUBE(one), SETS(one))
            expectSelfie(q1.toString()).toMatchDisk()
            val q2 = SELECT(one) GROUP BY DISTINCT listOf(emptyGroup(), one, groupOf(one), ROLLUP(one), CUBE(one), GROUPING SETS (one))
            expectSelfie(q2.toString()).toMatchDisk()
        }
    })
