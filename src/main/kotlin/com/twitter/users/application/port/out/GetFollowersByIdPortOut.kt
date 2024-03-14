package com.twitter.users.application.port.out

import reactor.core.publisher.Flux

interface GetFollowersByIdPortOut {

    fun getById(followedId: Long): Flux<Long>
}