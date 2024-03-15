package com.twitter.tweets.adapter.`in`.web.model

import com.twitter.shared.logging.CompanionLogger
import com.twitter.tweets.domain.Tweet

data class CreateTweetRequest(
    val userId: Long,
    val tweet: String,
) {
    fun CreateTweetRequest.mapToDomain() =
        with(this) {
            Tweet(
                id = null,
                userId = userId,
                body = tweet,
            )
        }.log { info("Tweet mapped: {}", it) }

    companion object: CompanionLogger()
}
