package com.github.parisoft.orkk.dsl.pg

import com.diffplug.selfie.coroutines.expectSelfie
import io.kotest.core.spec.style.StringSpec

class SelectFromToStringTests :
    StringSpec({
        val table = Table("public", "table")
        val t = table.alias("t")
        val f = t.get<Any>("f")
        val f2 = t.get<Any>("f2")

        "select * from table" {
            val q = SELECT(`*`) FROM table
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from only table" {
            val q = SELECT(`*`) FROM ONLY(table)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table tablesample BERNOULLI(1)" {
            val q = SELECT(`*`) FROM table TABLESAMPLE BERNOULLI(1)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table tablesample BERNOULLI(1) repeatable (1)" {
            val q = SELECT(`*`) FROM table TABLESAMPLE BERNOULLI(1) REPEATABLE (1)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from (select * from table)" {
            val q = SELECT(`*`) FROM (SELECT(`*`) FROM table)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from lateral (select * from table)" {
            val q = SELECT(`*`) FROM LATERAL(SELECT(`*`) FROM table)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from generate_series(1, 2)" {
            val q = SELECT(`*`) FROM generate_series(1, 2)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from generate_series(1, 2) with ordinality" {
            val q = SELECT(`*`) FROM generate_series(1, 2) WITH ORDINALITY
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from lateral generate_series(1, 2)" {
            val q = SELECT(`*`) FROM LATERAL(generate_series(1, 2))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from lateral generate_series(1, 2) with ordinality" {
            val q = SELECT(`*`) FROM LATERAL(generate_series(1, 2)) WITH ORDINALITY
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from rows from(generate_series(1, 2))" {
            val q = SELECT(`*`) FROM ROWS FROM generate_series(1, 2)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from rows from(generate_series(1, 2)) with ordinality" {
            val q = SELECT(`*`) FROM ROWS FROM generate_series(1, 2) WITH ORDINALITY
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from lateral rows from(generate_series(1, 2))" {
            val q = SELECT(`*`) FROM LATERAL ROWS FROM(generate_series(1, 2))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from lateral rows from(generate_series(1, 2)) with ordinality" {
            val q = SELECT(`*`) FROM LATERAL ROWS FROM(generate_series(1, 2)) WITH ORDINALITY
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Table Alias
        "select t.* from table as t" {
            val q = SELECT(t.`*`) FROM table AS t
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t.* from only table as t" {
            val q = SELECT(t.`*`) FROM ONLY(table) AS t
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t.* from table as t tablesample BERNOULLI(1)" {
            val q = SELECT(t.`*`) FROM table AS t TABLESAMPLE BERNOULLI(1)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t.* from (select * from table) as t" {
            val q = SELECT(t.`*`) FROM (SELECT(`*`) FROM table) AS t
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t.* from lateral (select * from table) as t" {
            val q = SELECT(t.`*`) FROM LATERAL(SELECT(`*`) FROM table) AS t
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t.* from generate_series(1, 2) as t" {
            val q = SELECT(t.`*`) FROM generate_series(1, 2) AS t
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t.* from generate_series(1, 2) with ordinality as t" {
            val q = SELECT(t.`*`) FROM generate_series(1, 2) WITH ORDINALITY AS t
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t.* from lateral generate_series(1, 2) as t" {
            val q = SELECT(t.`*`) FROM LATERAL(generate_series(1, 2)) AS t
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t.* from lateral generate_series(1, 2) with ordinality as t" {
            val q = SELECT(t.`*`) FROM LATERAL(generate_series(1, 2)) WITH ORDINALITY AS t
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Column Alias
        "select t.f from table as t(f)" {
            val q = SELECT(t[f]) FROM table AS t(f)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t.f from only table as t(f)" {
            val q = SELECT(t[f]) FROM ONLY(table) AS t(f)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t.f from table as t(f) tablesample BERNOULLI(1)" {
            val q = SELECT(t[f]) FROM table AS t(f) TABLESAMPLE BERNOULLI(1)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t.f from (select * from table) as t(f)" {
            val q = SELECT(t[f]) FROM (SELECT(`*`) FROM table) AS t(f)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t.f from lateral (select * from table) as t(f)" {
            val q = SELECT(t[f]) FROM LATERAL(SELECT(`*`) FROM table) AS t(f)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t.f from generate_series(1, 2) as t(f)" {
            val q = SELECT(t[f]) FROM generate_series(1, 2) AS t(f)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t.f from generate_series(1, 2) with ordinality as t(f)" {
            val q = SELECT(t[f]) FROM generate_series(1, 2) WITH ORDINALITY AS t(f)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t.f from lateral generate_series(1, 2) as t(f)" {
            val q = SELECT(t[f]) FROM LATERAL(generate_series(1, 2)) AS t(f)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t.f from lateral generate_series(1, 2) with ordinality as t(f)" {
            val q = SELECT(t[f]) FROM LATERAL(generate_series(1, 2)) WITH ORDINALITY AS t(f)
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Only Column Alias
        "select f, f2 from generate_series(1, 2) as (f, f2)" {
            val q = SELECT(f, f2) FROM generate_series(1, 2) AS listOf(f, f2)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select f, f2 from lateral generate_series(1, 2) as (f, f2)" {
            val q = SELECT(f, f2) FROM LATERAL(generate_series(1, 2)) AS listOf(f, f2)
            expectSelfie(q.toString()).toMatchDisk()
        }
    })
