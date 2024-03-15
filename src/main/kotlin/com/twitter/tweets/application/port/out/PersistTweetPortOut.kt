package com.twitter.tweets.application.port.out

import com.twitter.tweets.domain.Tweet
import reactor.core.publisher.Mono

interface PersistTweetPortOut {

    suspend fun persist(tweet: Tweet): Mono<Tweet>
}