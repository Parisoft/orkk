╔═ select 1 group by () ═╗
SELECT 1
GROUP BY ()
╔═ select 1 group by (), 1, (1), rollup (1), cube(1), grouping sets (1) ═╗
SELECT 1
GROUP BY
  (),
  1,
  (1),
  ROLLUP (1),
  CUBE (1),
  GROUPING SETS (1)
╔═ select 1 group by (1) ═╗
SELECT 1
GROUP BY (1)
╔═ select 1 group by 1 ═╗
SELECT 1
GROUP BY 1
╔═ select 1 group by cube ((1)) ═╗
SELECT 1
GROUP BY
  CUBE (
    (1)
  )
╔═ select 1 group by cube (1) ═╗
SELECT 1
GROUP BY CUBE (1)
╔═ select 1 group by grouping sets (()) ═╗
SELECT 1
GROUP BY
  GROUPING SETS (
    ()
  )
╔═ select 1 group by grouping sets ((), 1, (1), rollup (1), cube(1), grouping sets (1)) ═╗
SELECT 1
GROUP BY
  GROUPING SETS (
    (),
    1,
    (1),
    ROLLUP (1),
    CUBE (1),
    GROUPING SETS (1)
  )
╔═ select 1 group by grouping sets ((1)) ═╗
SELECT 1
GROUP BY
  GROUPING SETS (
    (1)
  )
╔═ select 1 group by grouping sets (1) ═╗
SELECT 1
GROUP BY GROUPING SETS (1)
╔═ select 1 group by grouping sets (cube (1)) ═╗
SELECT 1
GROUP BY
  GROUPING SETS (
    CUBE (1)
  )
╔═ select 1 group by grouping sets (grouping sets (1)) ═╗
SELECT 1
GROUP BY
  GROUPING SETS (
    GROUPING SETS (1)
  )
╔═ select 1 group by grouping sets (rollup (1)) ═╗
SELECT 1
GROUP BY
  GROUPING SETS (
    ROLLUP (1)
  )
╔═ select 1 group by rollup ((1)) ═╗
SELECT 1
GROUP BY
  ROLLUP (
    (1)
  )
╔═ select 1 group by rollup (1) ═╗
SELECT 1
GROUP BY ROLLUP (1)
╔═ select 1, 2 group by (1, 2) ═╗
SELECT
  1,
  2
GROUP BY
  (
    1,
    2
  )
╔═ select 1, 2 group by 1, (2) ═╗
SELECT
  1,
  2
GROUP BY
  1,
  (2)
╔═ select 1, 2 group by 1, (2), () ═╗
SELECT
  1,
  2
GROUP BY
  1,
  (2),
  ()
╔═ select 1, 2 group by 1, 2 ═╗
SELECT
  1,
  2
GROUP BY
  1,
  2
╔═ select 1, 2 group by cube ((1), 2) ═╗
SELECT
  1,
  2
GROUP BY
  CUBE (
    (1),
    2
  )
╔═ select 1, 2 group by cube ((1, 2)) ═╗
SELECT
  1,
  2
GROUP BY
  CUBE (
    (
      1,
      2
    )
  )
╔═ select 1, 2 group by cube (1, 2) ═╗
SELECT
  1,
  2
GROUP BY
  CUBE (
    1,
    2
  )
╔═ select 1, 2 group by rollup ((1), 2) ═╗
SELECT
  1,
  2
GROUP BY
  ROLLUP (
    (1),
    2
  )
╔═ select 1, 2 group by rollup ((1, 2)) ═╗
SELECT
  1,
  2
GROUP BY
  ROLLUP (
    (
      1,
      2
    )
  )
╔═ select 1, 2 group by rollup (1, 2) ═╗
SELECT
  1,
  2
GROUP BY
  ROLLUP (
    1,
    2
  )
╔═ [end of file] ═╗
