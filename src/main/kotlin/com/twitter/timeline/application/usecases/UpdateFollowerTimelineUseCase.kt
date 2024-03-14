package com.twitter.timeline.application.usecases

import com.twitter.timeline.application.port.`in`.UpdateFollowerTimelinePortIn
import com.twitter.timeline.application.port.out.UpdateFollowerTimelinePortOut
import com.twitter.tweets.domain.Tweet
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component

@Component
class UpdateFollowerTimelineUseCase(
    private val updateFollowerTimeline: UpdateFollowerTimelinePortOut
): UpdateFollowerTimelinePortIn {

    override suspend fun update(pair: Pair<Long, Tweet>) : Pair<Long, Tweet> =
        updateFollowerTimeline.updateFollower(pair).awaitSingle()

}