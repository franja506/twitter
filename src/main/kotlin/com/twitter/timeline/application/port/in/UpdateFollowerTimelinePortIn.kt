package com.twitter.timeline.application.port.`in`

import com.twitter.tweets.domain.Tweet

interface UpdateFollowerTimelinePortIn {

    fun update(pair: Pair<Long, Tweet>): Pair<Long, Tweet>
}
