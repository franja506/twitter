package com.twitter.users.application.port.out

import reactor.core.publisher.Mono

interface PersistFollowedPortOut {

    suspend fun follow(followerId: Long, followedId: Long): Mono<Long>
}