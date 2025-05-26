package com.github.parisoft.orkk.dsl.pg

import com.diffplug.selfie.coroutines.expectSelfie
import io.kotest.core.spec.style.StringSpec

class SelectWhereToStringTests :
    StringSpec({
        "select 1 where true" {
            val q = SELECT(1.literal()) WHERE true
            expectSelfie(q.toString()).toMatchDisk()
        }
    })
