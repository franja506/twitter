package com.twitter.tweets.adapter.`in`.web

import com.twitter.tweets.adapter.`in`.web.model.CreateTweetRequest
import com.twitter.tweets.application.port.`in`.CreateTweetPortIn
import com.twitter.tweets.domain.Tweet
import jakarta.validation.Valid
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/tweets")
class TweetsController(
    private val createTweetPortIn: CreateTweetPortIn
) {

    @PostMapping
    suspend fun create(@Valid @RequestBody request: CreateTweetRequest): Tweet =
        request
            .mapToDomain()
            .createTweet()
            .awaitSingle()

    private fun CreateTweetRequest.mapToDomain() = this.mapToDomain()

    private suspend fun Tweet.createTweet() = createTweetPortIn.execute(this)


}
