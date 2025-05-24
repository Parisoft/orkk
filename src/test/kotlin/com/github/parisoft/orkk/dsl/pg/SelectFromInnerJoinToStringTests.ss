╔═ select * from table inner join (select * from table2) on true ═╗
SELECT *
FROM "public".table
INNER JOIN
  (
    SELECT *
    FROM "public".table2
  )
ON true
╔═ select * from table inner join generate_series(1, 2) on true ═╗
SELECT *
FROM "public".table
INNER JOIN generate_series(1, 2)
ON true
╔═ select * from table inner join generate_series(1, 2) with ordinality on true ═╗
SELECT *
FROM "public".table
INNER JOIN generate_series(1, 2)
WITH ORDINALITY
ON true
╔═ select * from table inner join lateral (select * from table2) on true ═╗
SELECT *
FROM "public".table
INNER JOIN LATERAL
  (
    SELECT *
    FROM "public".table2
  )
ON true
╔═ select * from table inner join lateral generate_series(1, 2) on true ═╗
SELECT *
FROM "public".table
INNER JOIN LATERAL generate_series(1, 2)
ON true
╔═ select * from table inner join lateral generate_series(1, 2) with ordinality on true ═╗
SELECT *
FROM "public".table
INNER JOIN LATERAL generate_series(1, 2)
WITH ORDINALITY
ON true
╔═ select * from table inner join lateral rows from(generate_series(1, 2)) on true ═╗
SELECT *
FROM "public".table
INNER JOIN LATERAL ROWS FROM (generate_series(1, 2))
ON true
╔═ select * from table inner join lateral rows from(generate_series(1, 2)) with ordinality on true ═╗
SELECT *
FROM "public".table
INNER JOIN LATERAL ROWS FROM (generate_series(1, 2))
WITH ORDINALITY
ON true
╔═ select * from table inner join only table2 on true ═╗
SELECT *
FROM "public".table
INNER JOIN ONLY "public".table2
ON true
╔═ select * from table inner join rows from(generate_series(1, 2)) on true ═╗
SELECT *
FROM "public".table
INNER JOIN ROWS FROM (generate_series(1, 2))
ON true
╔═ select * from table inner join rows from(generate_series(1, 2)) with ordinality on true ═╗
SELECT *
FROM "public".table
INNER JOIN ROWS FROM (generate_series(1, 2))
WITH ORDINALITY
ON true
╔═ select * from table inner join table2 on true ═╗
SELECT *
FROM "public".table
INNER JOIN "public".table2
ON true
╔═ select * from table inner join table2 tablesample BERNOULLI(1) on true ═╗
SELECT *
FROM "public".table
INNER JOIN "public".table2
TABLESAMPLE BERNOULLI(1)
ON true
╔═ select * from table inner join table2 tablesample BERNOULLI(1) repeatable (1) on true ═╗
SELECT *
FROM "public".table
INNER JOIN "public".table2
TABLESAMPLE BERNOULLI(1)
REPEATABLE (1)
ON true
╔═ select f1, f2 from table inner join generate_series(1, 2) as (f, f2) on true ═╗
SELECT
  f1,
  f2
FROM "public".table
INNER JOIN generate_series(1, 2) AS (f1, f2)
ON true
╔═ select f1, f2 from table inner join lateral generate_series(1, 2) as (f, f2) on true ═╗
SELECT
  f1,
  f2
FROM "public".table
INNER JOIN LATERAL generate_series(1, 2) AS (f1, f2)
ON true
╔═ select t2.* from table as t inner join (select * from table2) as t2 on true ═╗
SELECT t2.*
FROM "public".table AS t
INNER JOIN
  (
    SELECT *
    FROM "public".table2
  ) AS t2
ON true
╔═ select t2.* from table as t inner join generate_series(1, 2) as t2 on true ═╗
SELECT t2.*
FROM "public".table AS t
INNER JOIN generate_series(1, 2) AS t2
ON true
╔═ select t2.* from table as t inner join generate_series(1, 2) with ordinality as t2 on true ═╗
SELECT t2.*
FROM "public".table AS t
INNER JOIN generate_series(1, 2)
WITH ORDINALITY AS t2
ON true
╔═ select t2.* from table as t inner join lateral (select * from table2) as t2 on true ═╗
SELECT t2.*
FROM "public".table AS t
INNER JOIN LATERAL
  (
    SELECT *
    FROM "public".table2
  ) AS t2
ON true
╔═ select t2.* from table as t inner join lateral generate_series(1, 2) as t2 on true ═╗
SELECT t2.*
FROM "public".table AS t
INNER JOIN LATERAL generate_series(1, 2) AS t2
ON true
╔═ select t2.* from table as t inner join lateral generate_series(1, 2) with ordinality as t2 on true ═╗
SELECT t2.*
FROM "public".table AS t
INNER JOIN LATERAL generate_series(1, 2)
WITH ORDINALITY AS t2
ON true
╔═ select t2.* from table as t inner join only table as t2 on true ═╗
SELECT t2.*
FROM "public".table AS t
INNER JOIN ONLY "public".table2 AS t2
ON true
╔═ select t2.* from table as t inner join table2 as t2 on true ═╗
SELECT t2.*
FROM "public".table AS t
INNER JOIN "public".table2 AS t2
ON true
╔═ select t2.* from table as t inner join table2 as t2 tablesample BERNOULLI(1) on true ═╗
SELECT t2.*
FROM "public".table AS t
INNER JOIN "public".table2 AS t2
TABLESAMPLE BERNOULLI(1)
ON true
╔═ select t2.f from table as t inner join (select * from table2) as t2(f) on true ═╗
SELECT t2.f
FROM "public".table AS t
INNER JOIN
  (
    SELECT *
    FROM "public".table2
  ) AS t2(f)
ON true
╔═ select t2.f from table as t inner join generate_series(1, 2) as t2(f) on true ═╗
SELECT t2.f
FROM "public".table AS t
INNER JOIN generate_series(1, 2) AS t2(f)
ON true
╔═ select t2.f from table as t inner join generate_series(1, 2) with ordinality as t2(f) on true ═╗
SELECT t2.f
FROM "public".table AS t
INNER JOIN generate_series(1, 2)
WITH ORDINALITY AS t2(f)
ON true
╔═ select t2.f from table as t inner join lateral (select * from table2) as t2 (f) on true ═╗
SELECT t2.f
FROM "public".table AS t
INNER JOIN LATERAL
  (
    SELECT *
    FROM "public".table2
  ) AS t2(f)
ON true
╔═ select t2.f from table as t inner join lateral generate_series(1, 2) as t2(f) on true ═╗
SELECT t2.f
FROM "public".table AS t
INNER JOIN LATERAL generate_series(1, 2) AS t2(f)
ON true
╔═ select t2.f from table as t inner join lateral generate_series(1, 2) with ordinality as t2(f) on true ═╗
SELECT t2.f
FROM "public".table AS t
INNER JOIN LATERAL generate_series(1, 2)
WITH ORDINALITY AS t2(f)
ON true
╔═ select t2.f from table as t inner join only table2 as t2(f) on true ═╗
SELECT t2.f
FROM "public".table AS t
INNER JOIN ONLY "public".table2 AS t2(f)
ON true
╔═ select t2.f from table as t inner join table2 as t2(f) on true ═╗
SELECT t2.f
FROM "public".table AS t
INNER JOIN "public".table2 AS t2(f)
ON true
╔═ select t2.f from table as t inner join table2 as t2(f) tablesample BERNOULLI(1) on true ═╗
SELECT t2.f
FROM "public".table AS t
INNER JOIN "public".table2 AS t2(f)
TABLESAMPLE BERNOULLI(1)
ON true
╔═ [end of file] ═╗
