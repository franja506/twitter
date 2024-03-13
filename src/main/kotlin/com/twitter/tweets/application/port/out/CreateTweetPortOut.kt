package com.twitter.tweets.application.port.out

import com.twitter.tweets.domain.Tweet

interface CreateTweetPortOut {
    fun execute(tweet: Tweet)
}