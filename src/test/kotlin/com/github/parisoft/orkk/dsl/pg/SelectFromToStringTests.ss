╔═ select * from (select * from table) ═╗
SELECT *
FROM
  (
    SELECT *
    FROM "public".table
  )
╔═ select * from generate_series(1, 2) ═╗
SELECT *
FROM generate_series(1, 2)
╔═ select * from generate_series(1, 2) with ordinality ═╗
SELECT *
FROM generate_series(1, 2)
WITH ORDINALITY
╔═ select * from lateral (select * from table) ═╗
SELECT *
FROM LATERAL
  (
    SELECT *
    FROM "public".table
  )
╔═ select * from lateral generate_series(1, 2) ═╗
SELECT *
FROM LATERAL generate_series(1, 2)
╔═ select * from lateral generate_series(1, 2) with ordinality ═╗
SELECT *
FROM LATERAL generate_series(1, 2)
WITH ORDINALITY
╔═ select * from lateral rows from(generate_series(1, 2)) ═╗
SELECT *
FROM LATERAL ROWS FROM(generate_series(1, 2))
╔═ select * from lateral rows from(generate_series(1, 2)) with ordinality ═╗
SELECT *
FROM LATERAL ROWS FROM(generate_series(1, 2))
WITH ORDINALITY
╔═ select * from only table ═╗
SELECT *
FROM ONLY "public".table
╔═ select * from rows from(generate_series(1, 2)) ═╗
SELECT *
FROM ROWS FROM(generate_series(1, 2))
╔═ select * from rows from(generate_series(1, 2)) with ordinality ═╗
SELECT *
FROM ROWS FROM(generate_series(1, 2))
WITH ORDINALITY
╔═ select * from table ═╗
SELECT *
FROM "public".table
╔═ select * from table tablesample BERNOULLI(1) ═╗
SELECT *
FROM "public".table
TABLESAMPLE BERNOULLI(1)
╔═ select * from table tablesample BERNOULLI(1) repeatable (1) ═╗
SELECT *
FROM "public".table
TABLESAMPLE BERNOULLI(1)
REPEATABLE (1)
╔═ select f1, f2 from generate_series(1, 2) as (f1, f2) ═╗
SELECT
  f1,
  f2
FROM generate_series(1, 2) AS (f1, f2)
╔═ select f1, f2 from lateral generate_series(1, 2) as (f1, f2) ═╗
SELECT
  f1,
  f2
FROM LATERAL generate_series(1, 2) AS (f1, f2)
╔═ select t.* from (select * from table) as t ═╗
SELECT t.*
FROM
  (
    SELECT *
    FROM "public".table
  ) AS t
╔═ select t.* from generate_series(1, 2) as t ═╗
SELECT t.*
FROM generate_series(1, 2) AS t
╔═ select t.* from generate_series(1, 2) with ordinality as t ═╗
SELECT t.*
FROM generate_series(1, 2)
WITH ORDINALITY AS t
╔═ select t.* from lateral (select * from table) as t ═╗
SELECT t.*
FROM LATERAL
  (
    SELECT *
    FROM "public".table
  ) AS t
╔═ select t.* from lateral generate_series(1, 2) as t ═╗
SELECT t.*
FROM LATERAL generate_series(1, 2) AS t
╔═ select t.* from lateral generate_series(1, 2) with ordinality as t ═╗
SELECT t.*
FROM LATERAL generate_series(1, 2)
WITH ORDINALITY AS t
╔═ select t.* from only table as t ═╗
SELECT t.*
FROM ONLY "public".table AS t
╔═ select t.* from table as t ═╗
SELECT t.*
FROM "public".table AS t
╔═ select t.* from table as t tablesample BERNOULLI(1) ═╗
SELECT t.*
FROM "public".table AS t
TABLESAMPLE BERNOULLI(1)
╔═ select t.f from (select * from table) as t(f) ═╗
SELECT t.f
FROM
  (
    SELECT *
    FROM "public".table
  ) AS t(f)
╔═ select t.f from generate_series(1, 2) as t(f) ═╗
SELECT t.f
FROM generate_series(1, 2) AS t(f)
╔═ select t.f from generate_series(1, 2) with ordinality as t(f) ═╗
SELECT t.f
FROM generate_series(1, 2)
WITH ORDINALITY AS t(f)
╔═ select t.f from lateral (select * from table) as t(f) ═╗
SELECT t.f
FROM LATERAL
  (
    SELECT *
    FROM "public".table
  ) AS t(f)
╔═ select t.f from lateral generate_series(1, 2) as t(f) ═╗
SELECT t.f
FROM LATERAL generate_series(1, 2) AS t(f)
╔═ select t.f from lateral generate_series(1, 2) with ordinality as t(f) ═╗
SELECT t.f
FROM LATERAL generate_series(1, 2)
WITH ORDINALITY AS t(f)
╔═ select t.f from only table as t(f) ═╗
SELECT t.f
FROM ONLY "public".table AS t(f)
╔═ select t.f from table as t(f) ═╗
SELECT t.f
FROM "public".table AS t(f)
╔═ select t.f from table as t(f) tablesample BERNOULLI(1) ═╗
SELECT t.f
FROM "public".table AS t(f)
TABLESAMPLE BERNOULLI(1)
╔═ [end of file] ═╗
