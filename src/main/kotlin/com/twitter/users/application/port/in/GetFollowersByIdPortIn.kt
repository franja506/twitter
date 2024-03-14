package com.twitter.users.application.port.`in`

import reactor.core.publisher.Flux

interface GetFollowersByIdPortIn {

    suspend fun findById(followedId: Long): Flux<Long>
}