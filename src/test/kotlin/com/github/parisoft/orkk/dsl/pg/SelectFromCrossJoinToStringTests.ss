╔═ select * from table cross join (select * from table2) ═╗
SELECT *
FROM "public".table
CROSS JOIN
  (
    SELECT *
    FROM "public".table2
  )
╔═ select * from table cross join generate_series(1, 2) ═╗
SELECT *
FROM "public".table
CROSS JOIN generate_series(1, 2)
╔═ select * from table cross join generate_series(1, 2) with ordinality ═╗
SELECT *
FROM "public".table
CROSS JOIN generate_series(1, 2)
  WITH ORDINALITY
╔═ select * from table cross join lateral (select * from table2) ═╗
SELECT *
FROM "public".table
CROSS JOIN LATERAL
  (
    SELECT *
    FROM "public".table2
  )
╔═ select * from table cross join lateral generate_series(1, 2) ═╗
SELECT *
FROM "public".table
CROSS JOIN LATERAL generate_series(1, 2)
╔═ select * from table cross join lateral generate_series(1, 2) with ordinality ═╗
SELECT *
FROM "public".table
CROSS JOIN LATERAL generate_series(1, 2)
  WITH ORDINALITY
╔═ select * from table cross join lateral rows from(generate_series(1, 2)) ═╗
SELECT *
FROM "public".table
CROSS JOIN LATERAL ROWS FROM(generate_series(1, 2))
╔═ select * from table cross join lateral rows from(generate_series(1, 2)) with ordinality ═╗
SELECT *
FROM "public".table
CROSS JOIN LATERAL ROWS FROM(generate_series(1, 2))
  WITH ORDINALITY
╔═ select * from table cross join only table2 ═╗
SELECT *
FROM "public".table
CROSS JOIN ONLY "public".table2
╔═ select * from table cross join rows from(generate_series(1, 2)) ═╗
SELECT *
FROM "public".table
CROSS JOIN ROWS FROM(generate_series(1, 2))
╔═ select * from table cross join rows from(generate_series(1, 2)) with ordinality ═╗
SELECT *
FROM "public".table
CROSS JOIN ROWS FROM(generate_series(1, 2))
  WITH ORDINALITY
╔═ select * from table cross join table2 ═╗
SELECT *
FROM "public".table
CROSS JOIN "public".table2
╔═ select * from table cross join table2 tablesample BERNOULLI(1) ═╗
SELECT *
FROM "public".table
CROSS JOIN "public".table2
  TABLESAMPLE BERNOULLI(1)
╔═ select * from table cross join table2 tablesample BERNOULLI(1) repeatable (1) ═╗
SELECT *
FROM "public".table
CROSS JOIN "public".table2
  TABLESAMPLE BERNOULLI(1)
    REPEATABLE (1)
╔═ select f1, f2 from table cross join generate_series(1, 2) as (f, f2) ═╗
SELECT
  f1,
  f2
FROM "public".table
CROSS JOIN generate_series(1, 2) AS (f1, f2)
╔═ select f1, f2 from table cross join lateral generate_series(1, 2) as (f, f2) ═╗
SELECT
  f1,
  f2
FROM "public".table
CROSS JOIN LATERAL generate_series(1, 2) AS (f1, f2)
╔═ select t2.* from table as t cross join (select * from table2) as t2 ═╗
SELECT t2.*
FROM "public".table AS t
CROSS JOIN
  (
    SELECT *
    FROM "public".table2
  ) AS t2
╔═ select t2.* from table as t cross join generate_series(1, 2) as t2 ═╗
SELECT t2.*
FROM "public".table AS t
CROSS JOIN generate_series(1, 2) AS t2
╔═ select t2.* from table as t cross join generate_series(1, 2) with ordinality as t2 ═╗
SELECT t2.*
FROM "public".table AS t
CROSS JOIN generate_series(1, 2)
  WITH ORDINALITY AS t2
╔═ select t2.* from table as t cross join lateral (select * from table2) as t2 ═╗
SELECT t2.*
FROM "public".table AS t
CROSS JOIN LATERAL
  (
    SELECT *
    FROM "public".table2
  ) AS t2
╔═ select t2.* from table as t cross join lateral generate_series(1, 2) as t2 ═╗
SELECT t2.*
FROM "public".table AS t
CROSS JOIN LATERAL generate_series(1, 2) AS t2
╔═ select t2.* from table as t cross join lateral generate_series(1, 2) with ordinality as t2 ═╗
SELECT t2.*
FROM "public".table AS t
CROSS JOIN LATERAL generate_series(1, 2)
  WITH ORDINALITY AS t2
╔═ select t2.* from table as t cross join only table as t2 ═╗
SELECT t2.*
FROM "public".table AS t
CROSS JOIN ONLY "public".table2 AS t2
╔═ select t2.* from table as t cross join table2 as t2 ═╗
SELECT t2.*
FROM "public".table AS t
CROSS JOIN "public".table2 AS t2
╔═ select t2.* from table as t cross join table2 as t2 tablesample BERNOULLI(1) ═╗
SELECT t2.*
FROM "public".table AS t
CROSS JOIN "public".table2 AS t2
  TABLESAMPLE BERNOULLI(1)
╔═ select t2.f from table as t cross join (select * from table2) as t2(f) ═╗
SELECT t2.f
FROM "public".table AS t
CROSS JOIN
  (
    SELECT *
    FROM "public".table2
  ) AS t2(f)
╔═ select t2.f from table as t cross join generate_series(1, 2) as t2(f) ═╗
SELECT t2.f
FROM "public".table AS t
CROSS JOIN generate_series(1, 2) AS t2(f)
╔═ select t2.f from table as t cross join generate_series(1, 2) with ordinality as t2(f) ═╗
SELECT t2.f
FROM "public".table AS t
CROSS JOIN generate_series(1, 2)
  WITH ORDINALITY AS t2(f)
╔═ select t2.f from table as t cross join lateral (select * from table2) as t2 (f) ═╗
SELECT t2.f
FROM "public".table AS t
CROSS JOIN LATERAL
  (
    SELECT *
    FROM "public".table2
  ) AS t2(f)
╔═ select t2.f from table as t cross join lateral generate_series(1, 2) as t2(f) ═╗
SELECT t2.f
FROM "public".table AS t
CROSS JOIN LATERAL generate_series(1, 2) AS t2(f)
╔═ select t2.f from table as t cross join lateral generate_series(1, 2) with ordinality as t2(f) ═╗
SELECT t2.f
FROM "public".table AS t
CROSS JOIN LATERAL generate_series(1, 2)
  WITH ORDINALITY AS t2(f)
╔═ select t2.f from table as t cross join only table2 as t2(f) ═╗
SELECT t2.f
FROM "public".table AS t
CROSS JOIN ONLY "public".table2 AS t2(f)
╔═ select t2.f from table as t cross join table2 as t2(f) ═╗
SELECT t2.f
FROM "public".table AS t
CROSS JOIN "public".table2 AS t2(f)
╔═ select t2.f from table as t cross join table2 as t2(f) tablesample BERNOULLI(1) ═╗
SELECT t2.f
FROM "public".table AS t
CROSS JOIN "public".table2 AS t2(f)
  TABLESAMPLE BERNOULLI(1)
╔═ [end of file] ═╗
