╔═ select ═╗
SELECT
╔═ select (select field1 as a) as b ═╗
SELECT
  (
    SELECT field1 AS a
  ) AS b
╔═ select (select field1 as f1) as a, field2 as b ═╗
SELECT
  (
    SELECT field1 AS f1
  ) AS a,
  field2 AS b
╔═ select (select field1) ═╗
SELECT
  (
    SELECT field1
  )
╔═ select (select field1), field2 ═╗
SELECT
  (
    SELECT field1
  ),
  field2
╔═ select * ═╗
SELECT *
╔═ select \(field1 as a, field2 as b\) ═╗
SELECT
  field1 AS a,
  field2 AS b
╔═ select \(field1, field2\) ═╗
SELECT
  field1,
  field2
╔═ select all * ═╗
SELECT ALL *
╔═ select all field1 as a ═╗
SELECT ALL field1 AS a
╔═ select all field1 as a, field2 as b ═╗
SELECT ALL
  field1 AS a,
  field2 AS b
╔═ select all field1, field2 ═╗
SELECT ALL
  field1,
  field2
╔═ select distinct * ═╗
SELECT DISTINCT *
╔═ select distinct field1 as a ═╗
SELECT DISTINCT field1 AS a
╔═ select distinct field1 as a, field2 as b ═╗
SELECT DISTINCT
  field1 AS a,
  field2 AS b
╔═ select distinct field1, field2 ═╗
SELECT DISTINCT
  field1,
  field2
╔═ select distinct on(field1) * ═╗
SELECT DISTINCT ON (field1) *
╔═ select distinct on(field1) \(field2 as b, field3 as c\) ═╗
SELECT DISTINCT ON (field1)
  field2 AS b,
  field3 AS c
╔═ select distinct on(field1) \(field2, field3\) ═╗
SELECT DISTINCT ON (field1)
  field2,
  field3
╔═ select distinct on(field1) field2 as b ═╗
SELECT DISTINCT ON (field1) field2 AS b
╔═ select distinct on(field1) field2 as b, field3 as c ═╗
SELECT DISTINCT ON (field1)
  field2 AS b,
  field3 AS c
╔═ select distinct on(field1) field2, field3 ═╗
SELECT DISTINCT ON (field1)
  field2,
  field3
╔═ select field1 as a ═╗
SELECT field1 AS a
╔═ select field1 as a, field2 as b ═╗
SELECT
  field1 AS a,
  field2 AS b
╔═ select field1, field2 ═╗
SELECT
  field1,
  field2
╔═ [end of file] ═╗
