package com.twitter.timeline.application.port.`in`

import com.twitter.tweets.domain.Tweet
import kotlinx.coroutines.flow.Flow

interface GetTimelineByIdPortIn {
    fun execute(userId: Long): Flow<Tweet>
}