╔═ select 1 group by distinct () ═╗
SELECT 1
GROUP BY DISTINCT ()
╔═ select 1 group by distinct (), 1, (1), rollup (1), cube(1), grouping sets (1) ═╗
SELECT 1
GROUP BY DISTINCT
  (),
  1,
  (1),
  ROLLUP (1),
  CUBE (1),
  GROUPING SETS (1)
╔═ select 1 group by distinct (1) ═╗
SELECT 1
GROUP BY DISTINCT (1)
╔═ select 1 group by distinct 1 ═╗
SELECT 1
GROUP BY DISTINCT 1
╔═ select 1 group by distinct cube ((1)) ═╗
SELECT 1
GROUP BY DISTINCT
  CUBE (
    (1)
  )
╔═ select 1 group by distinct cube (1) ═╗
SELECT 1
GROUP BY DISTINCT CUBE (1)
╔═ select 1 group by distinct grouping sets (()) ═╗
SELECT 1
GROUP BY DISTINCT
  GROUPING SETS (
    ()
  )
╔═ select 1 group by distinct grouping sets ((), 1, (1), rollup (1), cube(1), grouping sets (1)) ═╗
SELECT 1
GROUP BY DISTINCT
  GROUPING SETS (
    (),
    1,
    (1),
    ROLLUP (1),
    CUBE (1),
    GROUPING SETS (1)
  )
╔═ select 1 group by distinct grouping sets ((1)) ═╗
SELECT 1
GROUP BY DISTINCT
  GROUPING SETS (
    (1)
  )
╔═ select 1 group by distinct grouping sets (1) ═╗
SELECT 1
GROUP BY DISTINCT GROUPING SETS (1)
╔═ select 1 group by distinct grouping sets (cube (1)) ═╗
SELECT 1
GROUP BY DISTINCT
  GROUPING SETS (
    CUBE (1)
  )
╔═ select 1 group by distinct grouping sets (grouping sets (1)) ═╗
SELECT 1
GROUP BY DISTINCT
  GROUPING SETS (
    GROUPING SETS (1)
  )
╔═ select 1 group by distinct grouping sets (rollup (1)) ═╗
SELECT 1
GROUP BY DISTINCT
  GROUPING SETS (
    ROLLUP (1)
  )
╔═ select 1 group by distinct rollup ((1)) ═╗
SELECT 1
GROUP BY DISTINCT
  ROLLUP (
    (1)
  )
╔═ select 1 group by distinct rollup (1) ═╗
SELECT 1
GROUP BY DISTINCT ROLLUP (1)
╔═ select 1, 2 group by distinct (1, 2) ═╗
SELECT
  1,
  2
GROUP BY DISTINCT
  (
    1,
    2
  )
╔═ select 1, 2 group by distinct 1, (2) ═╗
SELECT
  1,
  2
GROUP BY DISTINCT
  1,
  (2)
╔═ select 1, 2 group by distinct 1, (2), () ═╗
SELECT
  1,
  2
GROUP BY DISTINCT
  1,
  (2),
  ()
╔═ select 1, 2 group by distinct 1, 2 ═╗
SELECT
  1,
  2
GROUP BY DISTINCT
  1,
  2
╔═ select 1, 2 group by distinct cube ((1), 2) ═╗
SELECT
  1,
  2
GROUP BY DISTINCT
  CUBE (
    (1),
    2
  )
╔═ select 1, 2 group by distinct cube ((1, 2)) ═╗
SELECT
  1,
  2
GROUP BY DISTINCT
  CUBE (
    (
      1,
      2
    )
  )
╔═ select 1, 2 group by distinct cube (1, 2) ═╗
SELECT
  1,
  2
GROUP BY DISTINCT
  CUBE (
    1,
    2
  )
╔═ select 1, 2 group by distinct rollup ((1), 2) ═╗
SELECT
  1,
  2
GROUP BY DISTINCT
  ROLLUP (
    (1),
    2
  )
╔═ select 1, 2 group by distinct rollup ((1, 2)) ═╗
SELECT
  1,
  2
GROUP BY DISTINCT
  ROLLUP (
    (
      1,
      2
    )
  )
╔═ select 1, 2 group by distinct rollup (1, 2) ═╗
SELECT
  1,
  2
GROUP BY DISTINCT
  ROLLUP (
    1,
    2
  )
╔═ [end of file] ═╗
