╔═ select * from table natural inner join (select * from table2) ═╗
SELECT *
FROM "public".table
NATURAL INNER JOIN
  (
    SELECT *
    FROM "public".table2
  )
╔═ select * from table natural inner join generate_series(1, 2) ═╗
SELECT *
FROM "public".table
NATURAL INNER JOIN generate_series(1, 2)
╔═ select * from table natural inner join generate_series(1, 2) with ordinality ═╗
SELECT *
FROM "public".table
NATURAL INNER JOIN generate_series(1, 2)
  WITH ORDINALITY
╔═ select * from table natural inner join lateral (select * from table2) ═╗
SELECT *
FROM "public".table
NATURAL INNER JOIN LATERAL
  (
    SELECT *
    FROM "public".table2
  )
╔═ select * from table natural inner join lateral generate_series(1, 2) ═╗
SELECT *
FROM "public".table
NATURAL INNER JOIN LATERAL generate_series(1, 2)
╔═ select * from table natural inner join lateral generate_series(1, 2) with ordinality ═╗
SELECT *
FROM "public".table
NATURAL INNER JOIN LATERAL generate_series(1, 2)
  WITH ORDINALITY
╔═ select * from table natural inner join lateral rows from(generate_series(1, 2)) ═╗
SELECT *
FROM "public".table
NATURAL INNER JOIN LATERAL ROWS FROM(generate_series(1, 2))
╔═ select * from table natural inner join lateral rows from(generate_series(1, 2)) with ordinality ═╗
SELECT *
FROM "public".table
NATURAL INNER JOIN LATERAL ROWS FROM(generate_series(1, 2))
  WITH ORDINALITY
╔═ select * from table natural inner join only table2 ═╗
SELECT *
FROM "public".table
NATURAL INNER JOIN ONLY "public".table2
╔═ select * from table natural inner join rows from(generate_series(1, 2)) ═╗
SELECT *
FROM "public".table
NATURAL INNER JOIN ROWS FROM(generate_series(1, 2))
╔═ select * from table natural inner join rows from(generate_series(1, 2)) with ordinality ═╗
SELECT *
FROM "public".table
NATURAL INNER JOIN ROWS FROM(generate_series(1, 2))
  WITH ORDINALITY
╔═ select * from table natural inner join table2 ═╗
SELECT *
FROM "public".table
NATURAL INNER JOIN "public".table2
╔═ select * from table natural inner join table2 tablesample BERNOULLI(1) ═╗
SELECT *
FROM "public".table
NATURAL INNER JOIN "public".table2
  TABLESAMPLE BERNOULLI(1)
╔═ select * from table natural inner join table2 tablesample BERNOULLI(1) repeatable (1) ═╗
SELECT *
FROM "public".table
NATURAL INNER JOIN "public".table2
  TABLESAMPLE BERNOULLI(1)
    REPEATABLE (1)
╔═ select f1, f2 from table natural inner join generate_series(1, 2) as (f, f2) ═╗
SELECT
  f1,
  f2
FROM "public".table
NATURAL INNER JOIN generate_series(1, 2) AS (f1, f2)
╔═ select f1, f2 from table natural inner join lateral generate_series(1, 2) as (f, f2) ═╗
SELECT
  f1,
  f2
FROM "public".table
NATURAL INNER JOIN LATERAL generate_series(1, 2) AS (f1, f2)
╔═ select t2.* from table as t natural inner join (select * from table2) as t2 ═╗
SELECT t2.*
FROM "public".table AS t
NATURAL INNER JOIN
  (
    SELECT *
    FROM "public".table2
  ) AS t2
╔═ select t2.* from table as t natural inner join generate_series(1, 2) as t2 ═╗
SELECT t2.*
FROM "public".table AS t
NATURAL INNER JOIN generate_series(1, 2) AS t2
╔═ select t2.* from table as t natural inner join generate_series(1, 2) with ordinality as t2 ═╗
SELECT t2.*
FROM "public".table AS t
NATURAL INNER JOIN generate_series(1, 2)
  WITH ORDINALITY AS t2
╔═ select t2.* from table as t natural inner join lateral (select * from table2) as t2 ═╗
SELECT t2.*
FROM "public".table AS t
NATURAL INNER JOIN LATERAL
  (
    SELECT *
    FROM "public".table2
  ) AS t2
╔═ select t2.* from table as t natural inner join lateral generate_series(1, 2) as t2 ═╗
SELECT t2.*
FROM "public".table AS t
NATURAL INNER JOIN LATERAL generate_series(1, 2) AS t2
╔═ select t2.* from table as t natural inner join lateral generate_series(1, 2) with ordinality as t2 ═╗
SELECT t2.*
FROM "public".table AS t
NATURAL INNER JOIN LATERAL generate_series(1, 2)
  WITH ORDINALITY AS t2
╔═ select t2.* from table as t natural inner join only table as t2 ═╗
SELECT t2.*
FROM "public".table AS t
NATURAL INNER JOIN ONLY "public".table2 AS t2
╔═ select t2.* from table as t natural inner join table2 as t2 ═╗
SELECT t2.*
FROM "public".table AS t
NATURAL INNER JOIN "public".table2 AS t2
╔═ select t2.* from table as t natural inner join table2 as t2 tablesample BERNOULLI(1) ═╗
SELECT t2.*
FROM "public".table AS t
NATURAL INNER JOIN "public".table2 AS t2
  TABLESAMPLE BERNOULLI(1)
╔═ select t2.f from table as t natural inner join (select * from table2) as t2(f) ═╗
SELECT t2.f
FROM "public".table AS t
NATURAL INNER JOIN
  (
    SELECT *
    FROM "public".table2
  ) AS t2(f)
╔═ select t2.f from table as t natural inner join generate_series(1, 2) as t2(f) ═╗
SELECT t2.f
FROM "public".table AS t
NATURAL INNER JOIN generate_series(1, 2) AS t2(f)
╔═ select t2.f from table as t natural inner join generate_series(1, 2) with ordinality as t2(f) ═╗
SELECT t2.f
FROM "public".table AS t
NATURAL INNER JOIN generate_series(1, 2)
  WITH ORDINALITY AS t2(f)
╔═ select t2.f from table as t natural inner join lateral (select * from table2) as t2 (f) ═╗
SELECT t2.f
FROM "public".table AS t
NATURAL INNER JOIN LATERAL
  (
    SELECT *
    FROM "public".table2
  ) AS t2(f)
╔═ select t2.f from table as t natural inner join lateral generate_series(1, 2) as t2(f) ═╗
SELECT t2.f
FROM "public".table AS t
NATURAL INNER JOIN LATERAL generate_series(1, 2) AS t2(f)
╔═ select t2.f from table as t natural inner join lateral generate_series(1, 2) with ordinality as t2(f) ═╗
SELECT t2.f
FROM "public".table AS t
NATURAL INNER JOIN LATERAL generate_series(1, 2)
  WITH ORDINALITY AS t2(f)
╔═ select t2.f from table as t natural inner join only table2 as t2(f) ═╗
SELECT t2.f
FROM "public".table AS t
NATURAL INNER JOIN ONLY "public".table2 AS t2(f)
╔═ select t2.f from table as t natural inner join table2 as t2(f) ═╗
SELECT t2.f
FROM "public".table AS t
NATURAL INNER JOIN "public".table2 AS t2(f)
╔═ select t2.f from table as t natural inner join table2 as t2(f) tablesample BERNOULLI(1) ═╗
SELECT t2.f
FROM "public".table AS t
NATURAL INNER JOIN "public".table2 AS t2(f)
  TABLESAMPLE BERNOULLI(1)
╔═ [end of file] ═╗
