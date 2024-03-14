package com.twitter.users.application.port.`in`

import reactor.core.publisher.Mono

interface FollowUserPortIn {

    suspend fun execute(followerId: Long, followedId: Long): Mono<Long>
}