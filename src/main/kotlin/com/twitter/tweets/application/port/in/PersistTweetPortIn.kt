package com.twitter.tweets.application.port.`in`

import com.twitter.tweets.domain.Tweet
import reactor.core.publisher.Mono

interface PersistTweetPortIn {

    suspend fun persist(tweet: Tweet): Mono<Tweet>
}