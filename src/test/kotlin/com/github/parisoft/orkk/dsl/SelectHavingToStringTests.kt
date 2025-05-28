package com.github.parisoft.orkk.dsl

import com.diffplug.selfie.coroutines.expectSelfie
import io.kotest.core.spec.style.StringSpec

class SelectHavingToStringTests :
    StringSpec({
        "select 1 having true" {
            val q = SELECT(1.literal()) HAVING true
            expectSelfie(q.toString()).toMatchDisk()
        }
    })
