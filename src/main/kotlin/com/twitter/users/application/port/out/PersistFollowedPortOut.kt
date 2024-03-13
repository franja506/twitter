package com.twitter.users.application.port.out

interface PersistFollowedPortOut {

    fun execute(followerId: Long, followedId: Long)
}