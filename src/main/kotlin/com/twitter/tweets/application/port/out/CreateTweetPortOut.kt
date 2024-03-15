package com.twitter.tweets.application.port.out

import com.twitter.tweets.domain.Tweet
import reactor.core.publisher.Mono

interface CreateTweetPortOut {
    suspend fun execute(tweet: Tweet): Mono<Tweet>
}