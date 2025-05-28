package com.github.parisoft.orkk.dsl

import com.diffplug.selfie.coroutines.expectSelfie
import io.kotest.core.spec.style.StringSpec

class SelectToStringTests :
    StringSpec({
        val field1 = fieldOf<Int>("field1")
        val field2 = fieldOf<String>("field2")
        val field3 = fieldOf<Boolean>("field3")

        "select" {
            val q = SELECT
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select *" {
            val q = SELECT(`*`)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select field1, field2" {
            val q = SELECT(field1, field2)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select [field1, field2]" {
            val q = SELECT(listOf(field1, field2))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select all *" {
            val q = SELECT ALL `*`
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select all field1, field2" {
            val q = SELECT ALL listOf(field1, field2)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select distinct *" {
            val q = SELECT DISTINCT `*`
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select distinct field1, field2" {
            val q = SELECT DISTINCT listOf(field1, field2)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select distinct on(field1) *" {
            val q = SELECT DISTINCT ON(field1) (`*`)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select distinct on(field1) field2, field3" {
            val q = SELECT DISTINCT ON(field1) (field2, field3)
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select distinct on(field1) [field2, field3]" {
            val q = SELECT DISTINCT ON(field1) (listOf(field2, field3))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select (select field1)" {
            val q = SELECT(SELECT(field1))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select (select field1), field2" {
            val q = SELECT(SELECT(field1), field2)
            expectSelfie(q.toString()).toMatchDisk()
        }
        // -- Alias
        "select field1 as a" {
            val q = SELECT(field1 AS "a")
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select field1 as a, field2 as b" {
            val q = SELECT(field1 AS "a", field2 AS "b")
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select [field1 as a, field2 as b]" {
            val q = SELECT(listOf(field1 AS "a", field2 AS "b"))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select all field1 as a" {
            val q = SELECT ALL field1 AS "a"
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select all field1 as a, field2 as b" {
            val q = SELECT ALL listOf(field1 AS "a", field2 AS "b")
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select distinct field1 as a" {
            val q = SELECT DISTINCT field1 AS "a"
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select distinct field1 as a, field2 as b" {
            val q = SELECT DISTINCT listOf(field1 AS "a", field2 AS "b")
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select distinct on(field1) field2 as b" {
            val q = SELECT DISTINCT ON(field1)(field2 AS "b")
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select distinct on(field1) field2 as b, field3 as c" {
            val q = SELECT DISTINCT ON(field1)(field2 AS "b", field3 AS "c")
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select distinct on(field1) [field2 as b, field3 as c]" {
            val q = SELECT DISTINCT ON(field1)(listOf(field2 AS "b", field3 AS "c"))
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select (select field1 as a) as b" {
            val q = SELECT(SELECT(field1 AS "a") AS "b")
            expectSelfie(q.toString()).toMatchDisk()
        }
        "select (select field1 as f1) as a, field2 as b" {
            val q = SELECT(SELECT(field1 AS "f1") AS "a", field2 AS "b")
            expectSelfie(q.toString()).toMatchDisk()
        }
    })
