package com.twitter.tweets.domain

data class Tweet(
    val id: Long,
    val userId: Long,
    val body: String,
)
