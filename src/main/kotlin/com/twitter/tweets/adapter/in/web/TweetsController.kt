package com.twitter.tweets.adapter.`in`.web

import com.twitter.tweets.application.port.`in`.CreateTweetPortIn
import com.twitter.tweets.domain.Tweet
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/tweets")
class TweetsController(
    private val createTweetPortIn: CreateTweetPortIn
) {

    @PostMapping
    fun create(tweet: Tweet) = createTweetPortIn.execute(tweet)
}
