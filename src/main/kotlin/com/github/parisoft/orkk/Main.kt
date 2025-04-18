package com.github.parisoft.orkk

import com.github.parisoft.orkk.dsl.pg.Field
import com.github.parisoft.orkk.dsl.pg.LATERAL
import com.github.parisoft.orkk.dsl.pg.ON
import com.github.parisoft.orkk.dsl.pg.SELECT
import com.github.parisoft.orkk.dsl.pg.Table

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        class MoviesTable(
            schema: String? = "public",
            name: String = "movies",
        ) : Table(schema, name) {
            val id = Field<Int>(table = this, name = "id")
            val title = Field<String>(table = this, name = "title")
            val year = Field<Int>(table = this, name = "year")
            val authorId = Field<Int>(table = this, name = "author_id")

            override fun alias(name: String) = MoviesTable(schema = null, name = name)
        }

        class AuthorsTable(
            schema: String = "public",
            name: String = "authors",
        ) : Table(schema, name) {
            val id = Field<Number>(table = this, name = "id")
            val name = Field<String>(table = this, name = "name")
        }

        val movies = MoviesTable()
        val authors = AuthorsTable()
        val m = movies.alias("m")
        val a = authors.alias("a")

        //        val BERNOULLI = function("BERNOULLI")
        //        val generate_series = function("generate_series")

        //        val s0 = SELECT ALL movies.title
        //        val s1 = SELECT DISTINCT ON(movies.title)(movies.year)
        //        val s2 = SELECT(movies.title, movies.year)
        //        val f0 = SELECT(`*`) FROM ONLY(movies) TABLESAMPLE BERNOULLI(40) REPEATABLE 2
        //        val f1 = SELECT(`*`) FROM LATERAL(SELECT(movies.title) FROM movies) AS "lm"
        //        val f2 = SELECT(`*`) FROM LATERAL(generate_series(1)) WITH ORDINALITY
        //        val f3 =
        //            SELECT(`*`) FROM LATERAL ROWS FROM(generate_series(1)) AS "rf" WITH ORDINALITY
        // AS "ord"
        //        val j0 = SELECT(`*`) FROM movies JOIN ONLY(movies) TABLESAMPLE BERNOULLI(40)
        // REPEATABLE 2
        //        val j1 = SELECT(`*`) FROM movies JOIN LATERAL(SELECT(movies.title) FROM movies) AS
        // "lm"
        //        val j2 = SELECT(`*`) FROM movies JOIN LATERAL(generate_series(1)) WITH ORDINALITY
        //        val j3 =
        //            SELECT(`*`) FROM
        //                movies JOIN
        //                LATERAL ROWS
        //                FROM(generate_series(1)) WITH
        //                ORDINALITY AS
        //                "ord"
        //        val j4 = SELECT(movies.`*`) FROM movies JOIN movies
        //        val lj0 = SELECT(movies.`*`) FROM movies LEFT OUTER JOIN movies
        //        val rj0 = SELECT(movies.`*`) FROM movies RIGHT OUTER JOIN movies
        //        val fj0 = SELECT(movies.`*`) FROM movies FULL OUTER JOIN movies
        //        val nj0 = SELECT(movies.`*`) FROM movies NATURAL INNER JOIN movies
        //        val nj1 = SELECT(movies.`*`) FROM movies NATURAL LEFT JOIN movies
        //        val nj2 = SELECT(movies.`*`) FROM movies NATURAL RIGHT JOIN movies
        //        val nj3 = SELECT(movies.`*`) FROM movies NATURAL FULL JOIN movies ON (1.literal()
        // AND 2.literal())
        //        val cj0 = SELECT(movies.`*`) FROM movies CROSS movies

        val s =
            SELECT DISTINCT
                ON(movies.id)(
                    movies.id,
                    movies.id,
                ) FROM LATERAL(SELECT())
        print(s)
    }
}
