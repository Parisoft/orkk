package com.github.parisoft.orkk.dsl

import com.diffplug.selfie.coroutines.expectSelfie
import io.kotest.core.spec.style.StringSpec

class SelectFromNaturalInnerJoinToStringTests :
    StringSpec({

        val table = Table("public", "table")
        val table2 = Table("public", "table2")
        val t = table.alias("t")
        val t2 = table2.alias("t2")
        val f = t.get<Any>("f")
        val f1 = fieldOf<Any>("f1")
        val f2 = fieldOf<Any>("f2")

        "select * from table natural inner join table2" {
            val q = SELECT(`*`) FROM table NATURAL INNER JOIN table2
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table natural inner join only table2" {
            val q = SELECT(`*`) FROM table NATURAL INNER JOIN ONLY(table2)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table natural inner join table2 tablesample BERNOULLI(1)" {
            val q = SELECT(`*`) FROM table NATURAL INNER JOIN table2 TABLESAMPLE BERNOULLI(1)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table natural inner join table2 tablesample BERNOULLI(1) repeatable (1)" {
            val q = SELECT(`*`) FROM table NATURAL INNER JOIN table2 TABLESAMPLE BERNOULLI(1) REPEATABLE (1)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table natural inner join (select * from table2)" {
            val q = SELECT(`*`) FROM table NATURAL INNER JOIN (SELECT(`*`) FROM table2)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table natural inner join lateral (select * from table2)" {
            val q = SELECT(`*`) FROM table NATURAL JOIN LATERAL (SELECT(`*`) FROM table2)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table natural inner join generate_series(1, 2)" {
            val q = SELECT(`*`) FROM table NATURAL INNER JOIN generate_series(1, 2)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table natural inner join generate_series(1, 2) with ordinality" {
            val q = SELECT(`*`) FROM table NATURAL INNER JOIN generate_series(1, 2) WITH ORDINALITY
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table natural inner join lateral generate_series(1, 2)" {
            val q = SELECT(`*`) FROM table NATURAL JOIN LATERAL generate_series(1, 2)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table natural inner join lateral generate_series(1, 2) with ordinality" {
            val q = SELECT(`*`) FROM table NATURAL JOIN LATERAL generate_series(1, 2) WITH ORDINALITY
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table natural inner join rows from(generate_series(1, 2))" {
            val q = SELECT(`*`) FROM table NATURAL INNER JOIN ROWS FROM (generate_series(1, 2))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table natural inner join rows from(generate_series(1, 2)) with ordinality" {
            val q = SELECT(`*`) FROM table NATURAL INNER JOIN ROWS FROM (generate_series(1, 2)) WITH ORDINALITY
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table natural inner join lateral rows from(generate_series(1, 2))" {
            val q = SELECT(`*`) FROM table NATURAL JOIN LATERAL ROWS FROM (generate_series(1, 2))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table natural inner join lateral rows from(generate_series(1, 2)) with ordinality" {
            val q = SELECT(`*`) FROM table NATURAL JOIN LATERAL ROWS FROM (generate_series(1, 2)) WITH ORDINALITY
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Table Alias
        "select t2.* from table as t natural inner join table2 as t2" {
            val q = SELECT(t2.`*`) FROM table AS t NATURAL INNER JOIN (table2) AS t2
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.* from table as t natural inner join only table as t2" {
            val q = SELECT(t2.`*`) FROM table AS t NATURAL INNER JOIN ONLY(table2) AS t2
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.* from table as t natural inner join table2 as t2 tablesample BERNOULLI(1)" {
            val q = SELECT(t2.`*`) FROM table AS t NATURAL INNER JOIN table2 AS t2 TABLESAMPLE BERNOULLI(1)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.* from table as t natural inner join (select * from table2) as t2" {
            val q = SELECT(t2.`*`) FROM table AS t NATURAL INNER JOIN (SELECT(`*`) FROM table2) AS t2
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.* from table as t natural inner join lateral (select * from table2) as t2" {
            val q = SELECT(t2.`*`) FROM table AS t NATURAL JOIN LATERAL (SELECT(`*`) FROM table2) AS t2
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.* from table as t natural inner join generate_series(1, 2) as t2" {
            val q = SELECT(t2.`*`) FROM table AS t NATURAL INNER JOIN generate_series(1, 2) AS t2
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.* from table as t natural inner join generate_series(1, 2) with ordinality as t2" {
            val q = SELECT(t2.`*`) FROM table AS t NATURAL INNER JOIN (generate_series(1, 2)) WITH ORDINALITY AS t2
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.* from table as t natural inner join lateral generate_series(1, 2) as t2" {
            val q = SELECT(t2.`*`) FROM table AS t NATURAL JOIN LATERAL generate_series(1, 2) AS t2
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.* from table as t natural inner join lateral generate_series(1, 2) with ordinality as t2" {
            val q = SELECT(t2.`*`) FROM table AS t NATURAL JOIN LATERAL generate_series(1, 2) WITH ORDINALITY AS t2
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Column Alias
        "select t2.f from table as t natural inner join table2 as t2(f)" {
            val q = SELECT(t2[f]) FROM table AS t NATURAL INNER JOIN table2 AS t2(f)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.f from table as t natural inner join only table2 as t2(f)" {
            val q = SELECT(t2[f]) FROM table AS t NATURAL INNER JOIN ONLY(table2) AS t2(f)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.f from table as t natural inner join table2 as t2(f) tablesample BERNOULLI(1)" {
            val q = SELECT(t2[f]) FROM table AS t NATURAL INNER JOIN table2 AS t2(f) TABLESAMPLE BERNOULLI(1)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.f from table as t natural inner join (select * from table2) as t2(f)" {
            val q = SELECT(t2[f]) FROM table AS t NATURAL INNER JOIN (SELECT(`*`) FROM table2) AS t2(f)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.f from table as t natural inner join lateral (select * from table2) as t2 (f)" {
            val q = SELECT(t2[f]) FROM table AS t NATURAL JOIN LATERAL (SELECT(`*`) FROM table2) AS t2(f)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.f from table as t natural inner join generate_series(1, 2) as t2(f)" {
            val q = SELECT(t2[f]) FROM table AS t NATURAL INNER JOIN generate_series(1, 2) AS t2(f)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.f from table as t natural inner join generate_series(1, 2) with ordinality as t2(f)" {
            val q = SELECT(t2[f]) FROM table AS t NATURAL INNER JOIN generate_series(1, 2) WITH ORDINALITY AS t2(f)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.f from table as t natural inner join lateral generate_series(1, 2) as t2(f)" {
            val q = SELECT(t2[f]) FROM table AS t NATURAL JOIN LATERAL generate_series(1, 2) AS t2(f)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.f from table as t natural inner join lateral generate_series(1, 2) with ordinality as t2(f)" {
            val q = SELECT(t2[f]) FROM table AS t NATURAL JOIN LATERAL generate_series(1, 2) WITH ORDINALITY AS t2(f)
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Only Column Alias
        "select f1, f2 from table natural inner join generate_series(1, 2) as (f, f2)" {
            val q = SELECT(f1, f2) FROM table NATURAL INNER JOIN generate_series(1, 2) AS listOf(f1, f2)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select f1, f2 from table natural inner join lateral generate_series(1, 2) as (f, f2)" {
            val q =
                SELECT(f1, f2) FROM table NATURAL JOIN LATERAL generate_series(1, 2) AS listOf(f1, f2)
            expectSelfie(q.toString()).toMatchDisk()
        }
    })
