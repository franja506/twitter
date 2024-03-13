package com.twitter.users.application.port.`in`

interface FollowUserPortIn {

    fun execute(followerId: Long, followedId: Long)
}