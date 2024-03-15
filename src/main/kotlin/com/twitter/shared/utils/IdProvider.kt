package com.twitter.shared.utils

import cn.hutool.core.lang.Snowflake
import org.springframework.stereotype.Component

@Component
class IdProvider{

    private val snowflake = Snowflake(1,1)

    fun provide() = snowflake.nextId()
}