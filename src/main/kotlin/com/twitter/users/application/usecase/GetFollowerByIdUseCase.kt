package com.twitter.users.application.usecase

import com.twitter.users.application.port.`in`.GetFollowersByIdPortIn
import com.twitter.users.application.port.out.GetFollowersByIdPortOut
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class GetFollowerByIdUseCase(
    private val retrieveFollower: GetFollowersByIdPortOut
): GetFollowersByIdPortIn {

    override fun findById(followedId: Long): Flux<Long> =
        retrieveFollower.getById(followedId)

}