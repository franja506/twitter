package com.twitter.tweets.application.port.`in`

import com.twitter.tweets.domain.Tweet
import kotlinx.coroutines.flow.Flow

interface CreateTweetPortIn {
    fun execute(tweet: Tweet): Flow<Tweet>
}