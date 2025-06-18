package com.github.parisoft.orkk.dsl

import com.diffplug.selfie.coroutines.expectSelfie
import io.kotest.core.spec.style.StringSpec

class SelectWindowToStringTests :
    StringSpec({

        val table = Table("public", "table")
        val f1 = fieldOf<Int>("f1")
        val f2 = fieldOf<Int>("f2")

        // -- Window Expression
        "select rank() over (partition by f1) from table" {
            val q = SELECT(rank() OVER (PARTITION BY f1)) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() filter (where true) over (partition by f1) from table" {
            val q = SELECT(rank() FILTER (WHERE(true)) OVER (PARTITION BY f1)) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (oder by f1) from table" {
            val q = SELECT(rank() OVER (ORDER BY f1)) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (oder by f1 desc) from table" {
            val q = SELECT(rank() OVER (ORDER BY f1.DESC)) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (oder by f1 using >) from table" {
            val q = SELECT(rank() OVER (ORDER BY (f1 USING GT))) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (oder by f1 nulls first) from table" {
            val q = SELECT(rank() OVER (ORDER BY (f1 NULLS FIRST))) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (oder by f1 desc nulls first) from table" {
            val q = SELECT(rank() OVER (ORDER BY (f1.DESC NULLS FIRST))) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (oder by f1 using > nulls first) from table" {
            val q = SELECT(rank() OVER (ORDER BY (f1 USING GT NULLS FIRST))) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (range unbounded preceding) from table" {
            val q = SELECT(rank() OVER (RANGE(UNBOUNDED_PRECEDING))) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (range unbounded preceding exclude current row) from table" {
            val q = SELECT(rank() OVER (RANGE(UNBOUNDED_PRECEDING) EXCLUDE CURRENT_ROW)) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (range between 0 preceding and 1 following) from table" {
            val q = SELECT(rank() OVER (RANGE BETWEEN (PRECEDING(0) AND FOLLOWING(1)))) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (range between 0 preceding and 1 following exclude ties) from table" {
            val q = SELECT(rank() OVER (RANGE BETWEEN (PRECEDING(0) AND FOLLOWING(1)) EXCLUDE TIES)) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (rows unbounded preceding) from table" {
            val q = SELECT(rank() OVER (ROWS(UNBOUNDED_PRECEDING))) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (rows unbounded preceding exclude current row) from table" {
            val q = SELECT(rank() OVER (ROWS(UNBOUNDED_PRECEDING) EXCLUDE CURRENT_ROW)) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (rows between 0 preceding and 1 following) from table" {
            val q = SELECT(rank() OVER (ROWS BETWEEN (PRECEDING(0) AND FOLLOWING(1)))) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (rows between 0 preceding and 1 following exclude ties) from table" {
            val q = SELECT(rank() OVER (ROWS BETWEEN (PRECEDING(0) AND FOLLOWING(1)) EXCLUDE TIES)) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (groups unbounded preceding) from table" {
            val q = SELECT(rank() OVER (GROUPS(UNBOUNDED_PRECEDING))) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (groups unbounded preceding exclude current row) from table" {
            val q = SELECT(rank() OVER (GROUPS(UNBOUNDED_PRECEDING) EXCLUDE CURRENT_ROW)) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (groups between 0 preceding and 1 following) from table" {
            val q = SELECT(rank() OVER (GROUPS BETWEEN (PRECEDING(0) AND FOLLOWING(1)))) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (groups between 0 preceding and 1 following exclude ties) from table" {
            val q = SELECT(rank() OVER (GROUPS BETWEEN (PRECEDING(0) AND FOLLOWING(1)) EXCLUDE TIES)) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (partition by f1 order by f2) from table" {
            val q = SELECT(rank() OVER (PARTITION BY f1 ORDER BY(f2))) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over (partition by f1 order by f2 range unbounded preceding) from table" {
            val q = SELECT(rank() OVER (PARTITION BY f1 ORDER BY(f2) RANGE UNBOUNDED_PRECEDING)) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Window Clause
        "select rank() over f1_partition from table window f1_partition as (partition by f1)" {
            val q = SELECT(rank() OVER "f1_partition") FROM table WINDOW "f1_partition" AS (PARTITION BY f1)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over f1_order_by from table window f1_order_by as (order by f1)" {
            val q = SELECT(rank() OVER "f1_order_by") FROM table WINDOW "f1_order_by" AS (ORDER BY f1)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over f1_range from table window f1_range as (range unbounded preceding)" {
            val q = SELECT(rank() OVER "f1_range") FROM table WINDOW "f1_range" AS (RANGE(UNBOUNDED_PRECEDING))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select rank() over f2_order_by from table window f1_partition as (partition by f1), f2_order_by as (f1_partition order by f2)" {
            val q =
                SELECT(rank() OVER "f2_order_by") FROM table WINDOW
                    listOf(
                        "f1_partition" AS (PARTITION BY f1),
                        "f2_order_by" AS ("f1_partition" ORDER BY(f2)),
                    )
            expectSelfie(q.toString()).toMatchDisk()
        }
        @Suppress("ktlint:standard:max-line-length")
        "select rank() over f1_range from table window f1_partition as (partition by f1), f2_order_by as (f1_partition order by f2), f1_range as (f2_order_by range unbounded preceding)" {
            val q =
                SELECT(rank() OVER "f1_range") FROM table WINDOW
                    listOf(
                        "f1_partition" AS (PARTITION BY f1),
                        "f2_order_by" AS ("f1_partition" ORDER BY(f2)),
                        "f1_range" AS ("f2_order_by" RANGE (UNBOUNDED_PRECEDING)),
                    )
            expectSelfie(q.toString()).toMatchDisk()
        }
    })
