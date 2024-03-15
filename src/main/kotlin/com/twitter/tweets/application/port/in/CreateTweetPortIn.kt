package com.twitter.tweets.application.port.`in`

import com.twitter.tweets.domain.Tweet
import reactor.core.publisher.Mono

interface CreateTweetPortIn {
    suspend fun execute(tweet: Tweet): Mono<Tweet>
}