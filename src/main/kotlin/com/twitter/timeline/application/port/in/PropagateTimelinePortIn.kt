package com.twitter.timeline.application.port.`in`

import com.twitter.tweets.domain.Tweet

interface PropagateTimelinePortIn {
    suspend fun propagate(tweet: Tweet)
}