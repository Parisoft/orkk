package com.github.parisoft.orkk

import com.github.parisoft.orkk.dsl.BY
import com.github.parisoft.orkk.dsl.BY.invoke
import com.github.parisoft.orkk.dsl.SELECT
import com.github.parisoft.orkk.dsl.SELECT.invoke
import com.github.parisoft.orkk.dsl.literal

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val q1 = SELECT(1.literal()) GROUP BY()
        print(q1)
    }
}
