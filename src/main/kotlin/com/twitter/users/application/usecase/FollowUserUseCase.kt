package com.twitter.users.application.usecase

import com.twitter.users.application.port.`in`.FollowUserPortIn
import org.springframework.stereotype.Component

@Component
class FollowUserUseCase: FollowUserPortIn {
    override fun execute(followerId: Long, followedId: Long) {
        TODO("Not yet implemented")
    }
}