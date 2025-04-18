package com.github.parisoft.orkk.config

import com.diffplug.selfie.kotest.SelfieExtension
import io.kotest.core.config.AbstractProjectConfig

class ProjectConfig : AbstractProjectConfig() {
    override fun extensions() = listOf(SelfieExtension(this))
}
