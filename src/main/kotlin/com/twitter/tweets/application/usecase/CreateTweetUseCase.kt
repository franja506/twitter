package com.twitter.tweets.application.usecase

import com.twitter.tweets.application.port.`in`.CreateTweetPortIn
import com.twitter.tweets.domain.Tweet
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Component

@Component
class CreateTweetUseCase: CreateTweetPortIn {
    override fun execute(tweet: Tweet): Flow<Tweet> {
        TODO("Not yet implemented")
    }
}