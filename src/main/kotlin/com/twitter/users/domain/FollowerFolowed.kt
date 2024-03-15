package com.twitter.users.domain

data class FollowerFolowed(
    val followerId: Long,
    val followedId: Long,
)
