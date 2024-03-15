package com.twitter.tweets.application.usecase

import com.twitter.tweets.application.port.`in`.PersistTweetPortIn
import com.twitter.tweets.application.port.out.PersistTweetPortOut
import com.twitter.tweets.domain.Tweet
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class PersistTweetUseCase(
    private val persistTweetPortOut: PersistTweetPortOut
): PersistTweetPortIn {
    override fun persist(tweet: Tweet): Mono<Tweet> =
        persistTweetPortOut.persist(tweet)

}