package com.twitter.users.application.usecase

import com.twitter.users.application.port.`in`.FollowUserPortIn
import com.twitter.users.application.port.out.PersistFollowedPortOut
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class FollowUserUseCase(
    private val followUserPortOut: PersistFollowedPortOut
): FollowUserPortIn {
    override suspend fun execute(followerId: Long, followedId: Long): Mono<Long> =
        followUserPortOut.follow(followerId, followedId)

}