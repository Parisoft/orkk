╔═ select 1 group by all () ═╗
SELECT 1
GROUP BY ALL ()
╔═ select 1 group by all (), 1, (1), rollup (1), cube(1), grouping sets (1) ═╗
SELECT 1
GROUP BY ALL
  (),
  1,
  (1),
  ROLLUP (1),
  CUBE (1),
  GROUPING SETS (1)
╔═ select 1 group by all (1) ═╗
SELECT 1
GROUP BY ALL (1)
╔═ select 1 group by all 1 ═╗
SELECT 1
GROUP BY ALL 1
╔═ select 1 group by all cube ((1)) ═╗
SELECT 1
GROUP BY ALL
  CUBE (
    (1)
  )
╔═ select 1 group by all cube (1) ═╗
SELECT 1
GROUP BY ALL CUBE (1)
╔═ select 1 group by all grouping sets (()) ═╗
SELECT 1
GROUP BY ALL
  GROUPING SETS (
    ()
  )
╔═ select 1 group by all grouping sets ((), 1, (1), rollup (1), cube(1), grouping sets (1)) ═╗
SELECT 1
GROUP BY ALL
  GROUPING SETS (
    (),
    1,
    (1),
    ROLLUP (1),
    CUBE (1),
    GROUPING SETS (1)
  )
╔═ select 1 group by all grouping sets ((1)) ═╗
SELECT 1
GROUP BY ALL
  GROUPING SETS (
    (1)
  )
╔═ select 1 group by all grouping sets (1) ═╗
SELECT 1
GROUP BY ALL GROUPING SETS (1)
╔═ select 1 group by all grouping sets (cube (1)) ═╗
SELECT 1
GROUP BY ALL
  GROUPING SETS (
    CUBE (1)
  )
╔═ select 1 group by all grouping sets (grouping sets (1)) ═╗
SELECT 1
GROUP BY ALL
  GROUPING SETS (
    GROUPING SETS (1)
  )
╔═ select 1 group by all grouping sets (rollup (1)) ═╗
SELECT 1
GROUP BY ALL
  GROUPING SETS (
    ROLLUP (1)
  )
╔═ select 1 group by all rollup ((1)) ═╗
SELECT 1
GROUP BY ALL
  ROLLUP (
    (1)
  )
╔═ select 1 group by all rollup (1) ═╗
SELECT 1
GROUP BY ALL ROLLUP (1)
╔═ select 1, 2 group by all (1, 2) ═╗
SELECT
  1,
  2
GROUP BY ALL
  (
    1,
    2
  )
╔═ select 1, 2 group by all 1, (2) ═╗
SELECT
  1,
  2
GROUP BY ALL
  1,
  (2)
╔═ select 1, 2 group by all 1, (2), () ═╗
SELECT
  1,
  2
GROUP BY ALL
  1,
  (2),
  ()
╔═ select 1, 2 group by all 1, 2 ═╗
SELECT
  1,
  2
GROUP BY ALL
  1,
  2
╔═ select 1, 2 group by all cube ((1), 2) ═╗
SELECT
  1,
  2
GROUP BY ALL
  CUBE (
    (1),
    2
  )
╔═ select 1, 2 group by all cube ((1, 2)) ═╗
SELECT
  1,
  2
GROUP BY ALL
  CUBE (
    (
      1,
      2
    )
  )
╔═ select 1, 2 group by all cube (1, 2) ═╗
SELECT
  1,
  2
GROUP BY ALL
  CUBE (
    1,
    2
  )
╔═ select 1, 2 group by all rollup ((1), 2) ═╗
SELECT
  1,
  2
GROUP BY ALL
  ROLLUP (
    (1),
    2
  )
╔═ select 1, 2 group by all rollup ((1, 2)) ═╗
SELECT
  1,
  2
GROUP BY ALL
  ROLLUP (
    (
      1,
      2
    )
  )
╔═ select 1, 2 group by all rollup (1, 2) ═╗
SELECT
  1,
  2
GROUP BY ALL
  ROLLUP (
    1,
    2
  )
╔═ [end of file] ═╗
