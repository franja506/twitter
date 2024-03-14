package com.twitter.timeline.application.usecases

import com.twitter.timeline.application.port.`in`.GetTimelineByIdPortIn
import com.twitter.tweets.domain.Tweet
import com.twitter.users.application.port.out.CreateUserPortOut
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Component

@Component
class FindTimelineByIdUseCase(
    private val getTimeline: CreateUserPortOut
): GetTimelineByIdPortIn {
    override fun execute(userId: Long): Flow<Tweet> {
        TODO("Not yet implemented")
    }
}