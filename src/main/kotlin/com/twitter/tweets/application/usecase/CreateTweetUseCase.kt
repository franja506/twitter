package com.twitter.tweets.application.usecase

import com.twitter.shared.utils.IdProvider
import com.twitter.tweets.application.port.`in`.CreateTweetPortIn
import com.twitter.tweets.application.port.out.CreateTweetPortOut
import com.twitter.tweets.domain.Tweet
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CreateTweetUseCase(
    private val createTweetPortOut: CreateTweetPortOut,
    private val idProvider: IdProvider
): CreateTweetPortIn {
    override suspend fun execute(tweet: Tweet): Mono<Tweet> =
        tweet.copy(id = idProvider.provide())
            .let { createTweetPortOut.execute(it) }

}