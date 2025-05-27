package com.github.parisoft.orkk.dsl.pg

import com.diffplug.selfie.coroutines.expectSelfie
import io.kotest.core.spec.style.StringSpec

class SelectFromInnerJoinToStringTests :
    StringSpec({

        val table = Table("public", "table")
        val table2 = Table("public", "table2")
        val t = table.alias("t")
        val t2 = table2.alias("t2")
        val f = t.get<Any>("f")
        val f1 = fieldOf<Any>("f1")
        val f2 = fieldOf<Any>("f2")

        "select * from table inner join table2 on true" {
            val q = SELECT(`*`) FROM table JOIN table2 ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table inner join only table2 on true" {
            val q = SELECT(`*`) FROM table JOIN ONLY(table2) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table inner join table2 tablesample BERNOULLI(1) on true" {
            val q = SELECT(`*`) FROM table JOIN table2 TABLESAMPLE BERNOULLI(1) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table inner join table2 tablesample BERNOULLI(1) repeatable (1) on true" {
            val q = SELECT(`*`) FROM table JOIN table2 TABLESAMPLE BERNOULLI(1) REPEATABLE (1) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table inner join (select * from table2) on true" {
            val q = SELECT(`*`) FROM table JOIN (SELECT(`*`) FROM table2) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table inner join lateral (select * from table2) on true" {
            val q = SELECT(`*`) FROM table INNER JOIN LATERAL (SELECT(`*`) FROM table2) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table inner join generate_series(1, 2) on true" {
            val q = SELECT(`*`) FROM table JOIN generate_series(1, 2) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table inner join generate_series(1, 2) with ordinality on true" {
            val q = SELECT(`*`) FROM table JOIN generate_series(1, 2) WITH ORDINALITY ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table inner join lateral generate_series(1, 2) on true" {
            val q = SELECT(`*`) FROM table INNER JOIN LATERAL generate_series(1, 2) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table inner join lateral generate_series(1, 2) with ordinality on true" {
            val q = SELECT(`*`) FROM table INNER JOIN LATERAL generate_series(1, 2) WITH ORDINALITY ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table inner join rows from(generate_series(1, 2)) on true" {
            val q = SELECT(`*`) FROM table JOIN ROWS FROM (generate_series(1, 2)) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table inner join rows from(generate_series(1, 2)) with ordinality on true" {
            val q = SELECT(`*`) FROM table JOIN ROWS FROM (generate_series(1, 2)) WITH ORDINALITY ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table inner join lateral rows from(generate_series(1, 2)) on true" {
            val q = SELECT(`*`) FROM table INNER JOIN LATERAL ROWS FROM (generate_series(1, 2)) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select * from table inner join lateral rows from(generate_series(1, 2)) with ordinality on true" {
            val q = SELECT(`*`) FROM table INNER JOIN LATERAL ROWS FROM (generate_series(1, 2)) WITH ORDINALITY ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Table Alias
        "select t2.* from table as t inner join table2 as t2 on true" {
            val q = SELECT(t2.`*`) FROM table AS t JOIN table2 AS t2 ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.* from table as t inner join only table as t2 on true" {
            val q = SELECT(t2.`*`) FROM table AS t JOIN ONLY(table2) AS t2 ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.* from table as t inner join table2 as t2 tablesample BERNOULLI(1) on true" {
            val q = SELECT(t2.`*`) FROM table AS t JOIN table2 AS t2 TABLESAMPLE BERNOULLI(1) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.* from table as t inner join (select * from table2) as t2 on true" {
            val q = SELECT(t2.`*`) FROM table AS t JOIN (SELECT(`*`) FROM table2) AS t2 ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.* from table as t inner join lateral (select * from table2) as t2 on true" {
            val q = SELECT(t2.`*`) FROM table AS t INNER JOIN LATERAL (SELECT(`*`) FROM table2) AS t2 ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.* from table as t inner join generate_series(1, 2) as t2 on true" {
            val q = SELECT(t2.`*`) FROM table AS t JOIN generate_series(1, 2) AS t2 ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.* from table as t inner join generate_series(1, 2) with ordinality as t2 on true" {
            val q = SELECT(t2.`*`) FROM table AS t JOIN generate_series(1, 2) WITH ORDINALITY AS t2 ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.* from table as t inner join lateral generate_series(1, 2) as t2 on true" {
            val q = SELECT(t2.`*`) FROM table AS t INNER JOIN LATERAL (generate_series(1, 2)) AS t2 ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.* from table as t inner join lateral generate_series(1, 2) with ordinality as t2 on true" {
            val q = SELECT(t2.`*`) FROM table AS t INNER JOIN LATERAL (generate_series(1, 2)) WITH ORDINALITY AS t2 ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Column Alias
        "select t2.f from table as t inner join table2 as t2(f) on true" {
            val q = SELECT(t2[f]) FROM table AS t JOIN table2 AS t2(f) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.f from table as t inner join only table2 as t2(f) on true" {
            val q = SELECT(t2[f]) FROM table AS t JOIN ONLY(table2) AS t2(f) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.f from table as t inner join table2 as t2(f) tablesample BERNOULLI(1) on true" {
            val q = SELECT(t2[f]) FROM table AS t JOIN table2 AS t2(f) TABLESAMPLE BERNOULLI(1) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.f from table as t inner join (select * from table2) as t2(f) on true" {
            val q = SELECT(t2[f]) FROM table AS t JOIN (SELECT(`*`) FROM table2) AS t2(f) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.f from table as t inner join lateral (select * from table2) as t2 (f) on true" {
            val q = SELECT(t2[f]) FROM table AS t INNER JOIN LATERAL (SELECT(`*`) FROM table2) AS t2(f) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.f from table as t inner join generate_series(1, 2) as t2(f) on true" {
            val q = SELECT(t2[f]) FROM table AS t JOIN generate_series(1, 2) AS t2(f) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.f from table as t inner join generate_series(1, 2) with ordinality as t2(f) on true" {
            val q = SELECT(t2[f]) FROM table AS t JOIN generate_series(1, 2) WITH ORDINALITY AS t2(f) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.f from table as t inner join lateral generate_series(1, 2) as t2(f) on true" {
            val q = SELECT(t2[f]) FROM table AS t INNER JOIN LATERAL (generate_series(1, 2)) AS t2(f) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select t2.f from table as t inner join lateral generate_series(1, 2) with ordinality as t2(f) on true" {
            val q = SELECT(t2[f]) FROM table AS t INNER JOIN LATERAL (generate_series(1, 2)) WITH ORDINALITY AS t2(f) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Only Column Alias
        "select f1, f2 from table inner join generate_series(1, 2) as (f, f2) on true" {
            val q = SELECT(f1, f2) FROM table JOIN generate_series(1, 2) AS listOf(f1, f2) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select f1, f2 from table inner join lateral generate_series(1, 2) as (f, f2) on true" {
            val q =
                SELECT(f1, f2) FROM table INNER JOIN LATERAL (generate_series(1, 2)) AS listOf(f1, f2) ON true
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Using
        "select * from table inner join table2 using (f2)" {
            val q1 = SELECT(`*`) FROM table JOIN table2 USING (f2)
            expectSelfie(q1.toString()).toMatchDisk()
            val q2 = SELECT(`*`) FROM table JOIN table2 USING ("f2")
            expectSelfie(q2.toString()).toMatchDisk()
        }
        "select * from table inner join table2 using (f1, f2)" {
            val q1 = SELECT(`*`) FROM table JOIN table2 USING listOf(f1, f2)
            expectSelfie(q1.toString()).toMatchDisk()
            val q2 = SELECT(`*`) FROM table JOIN table2 USING listOf("f1", "f2")
            expectSelfie(q2.toString()).toMatchDisk()
        }
        // -- Using Alias
        "select * from table inner join table2 using (f2) as t2" {
            val q1 = SELECT(`*`) FROM table JOIN table2 USING (f2) AS t2
            expectSelfie(q1.toString()).toMatchDisk()
            val q2 = SELECT(`*`) FROM table JOIN table2 USING ("f2") AS t2
            expectSelfie(q2.toString()).toMatchDisk()
        }
        "select * from table inner join table2 using (f1, f2) as t2" {
            val q1 = SELECT(`*`) FROM table JOIN table2 USING listOf(f1, f2) AS t2
            expectSelfie(q1.toString()).toMatchDisk()
            val q2 = SELECT(`*`) FROM table JOIN table2 USING listOf("f1", "f2") AS t2
            expectSelfie(q2.toString()).toMatchDisk()
        }
    })
