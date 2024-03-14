package com.twitter.timeline.application.usecases

import com.twitter.timeline.application.port.`in`.PropagateTimelinePortIn
import com.twitter.timeline.application.port.out.UpdateFollowerTimelinePortOut
import com.twitter.tweets.domain.Tweet
import com.twitter.users.application.port.`in`.GetFollowersByIdPortIn
import org.springframework.stereotype.Component

@Component
class PropagateTimelineUseCase(
    private val getFollowersById: GetFollowersByIdPortIn,
    private val updateFollowerTimeline: UpdateFollowerTimelinePortOut,
) : PropagateTimelinePortIn {
    override suspend fun propagate(tweet: Tweet) {

        val followers = getFollowersById.findById(tweet.userId)

        followers.map { updateFollowerTimeline.updateFollower(it to tweet) }
    }
}