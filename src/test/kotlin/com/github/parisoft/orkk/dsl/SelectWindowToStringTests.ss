╔═ select rank() filter (where true) over (partition by f1) from table ═╗
SELECT rank() FILTER (WHERE true) OVER (PARTITION BY f1)
FROM "public".table
╔═ select rank() over (groups between 0 preceding and 1 following exclude ties) from table ═╗
SELECT rank() OVER (GROUPS BETWEEN 0 PRECEDING AND 1 FOLLOWING TIES )
FROM "public".table
╔═ select rank() over (groups between 0 preceding and 1 following) from table ═╗
SELECT rank() OVER (GROUPS BETWEEN 0 PRECEDING AND 1 FOLLOWING )
FROM "public".table
╔═ select rank() over (groups unbounded preceding exclude current row) from table ═╗
SELECT rank() OVER (GROUPS UNBOUNDED PRECEDING CURRENT ROW )
FROM "public".table
╔═ select rank() over (groups unbounded preceding) from table ═╗
SELECT rank() OVER (GROUPS UNBOUNDED PRECEDING )
FROM "public".table
╔═ select rank() over (oder by f1 desc nulls first) from table ═╗
SELECT rank() OVER (ORDER BY f1 DESC NULLS FIRST)
FROM "public".table
╔═ select rank() over (oder by f1 desc) from table ═╗
SELECT rank() OVER (ORDER BY f1 DESC)
FROM "public".table
╔═ select rank() over (oder by f1 nulls first) from table ═╗
SELECT rank() OVER (ORDER BY f1 NULLS FIRST)
FROM "public".table
╔═ select rank() over (oder by f1 using > nulls first) from table ═╗
SELECT rank() OVER (ORDER BY f1 USING > NULLS FIRST)
FROM "public".table
╔═ select rank() over (oder by f1 using >) from table ═╗
SELECT rank() OVER (ORDER BY f1 USING >)
FROM "public".table
╔═ select rank() over (oder by f1) from table ═╗
SELECT rank() OVER (ORDER BY f1)
FROM "public".table
╔═ select rank() over (partition by f1 order by f2 range unbounded preceding) from table ═╗
SELECT
  rank() OVER (
    PARTITION BY f1
    ORDER BY f2
    RANGE UNBOUNDED PRECEDING 
  )
FROM "public".table
╔═ select rank() over (partition by f1 order by f2) from table ═╗
SELECT
  rank() OVER (
    PARTITION BY f1
    ORDER BY f2
  )
FROM "public".table
╔═ select rank() over (partition by f1) from table ═╗
SELECT rank() OVER (PARTITION BY f1)
FROM "public".table
╔═ select rank() over (range between 0 preceding and 1 following exclude ties) from table ═╗
SELECT rank() OVER (RANGE BETWEEN 0 PRECEDING AND 1 FOLLOWING TIES )
FROM "public".table
╔═ select rank() over (range between 0 preceding and 1 following) from table ═╗
SELECT rank() OVER (RANGE BETWEEN 0 PRECEDING AND 1 FOLLOWING )
FROM "public".table
╔═ select rank() over (range unbounded preceding exclude current row) from table ═╗
SELECT rank() OVER (RANGE UNBOUNDED PRECEDING CURRENT ROW )
FROM "public".table
╔═ select rank() over (range unbounded preceding) from table ═╗
SELECT rank() OVER (RANGE UNBOUNDED PRECEDING )
FROM "public".table
╔═ select rank() over (rows between 0 preceding and 1 following exclude ties) from table ═╗
SELECT rank() OVER (ROWS BETWEEN 0 PRECEDING AND 1 FOLLOWING TIES )
FROM "public".table
╔═ select rank() over (rows between 0 preceding and 1 following) from table ═╗
SELECT rank() OVER (ROWS BETWEEN 0 PRECEDING AND 1 FOLLOWING )
FROM "public".table
╔═ select rank() over (rows unbounded preceding exclude current row) from table ═╗
SELECT rank() OVER (ROWS UNBOUNDED PRECEDING CURRENT ROW )
FROM "public".table
╔═ select rank() over (rows unbounded preceding) from table ═╗
SELECT rank() OVER (ROWS UNBOUNDED PRECEDING )
FROM "public".table
╔═ select rank() over f1_order_by from table window f1_order_by as (order by f1) ═╗
SELECT rank() OVER f1_order_by 
FROM "public".table
WINDOW f1_order_by AS (ORDER BY f1)
╔═ select rank() over f1_partition from table window f1_partition as (partition by f1) ═╗
SELECT rank() OVER f1_partition 
FROM "public".table
WINDOW f1_partition AS (PARTITION BY f1)
╔═ select rank() over f1_range from table window f1_partition as (partition by f1), f2_order_by as (f1_partition order by f2), f1_range as (f2_order_by range unbounded preceding) ═╗
SELECT rank() OVER f1_range 
FROM "public".table
WINDOW
  f1_partition AS (PARTITION BY f1), 
  f2_order_by AS (
    f1_partition 
    ORDER BY f2
  ), 
  f1_range AS (
    f2_order_by 
    RANGE UNBOUNDED PRECEDING 
  )
╔═ select rank() over f1_range from table window f1_range as (range unbounded preceding) ═╗
SELECT rank() OVER f1_range 
FROM "public".table
WINDOW f1_range AS (RANGE UNBOUNDED PRECEDING )
╔═ select rank() over f2_order_by from table window f1_partition as (partition by f1), f2_order_by as (f1_partition order by f2) ═╗
SELECT rank() OVER f2_order_by 
FROM "public".table
WINDOW
  f1_partition AS (PARTITION BY f1), 
  f2_order_by AS (
    f1_partition 
    ORDER BY f2
  )
╔═ [end of file] ═╗
