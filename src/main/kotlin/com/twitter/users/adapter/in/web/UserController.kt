package com.twitter.users.adapter.`in`.web

import com.twitter.users.application.port.`in`.CreateUserPortIn
import com.twitter.users.application.port.`in`.FollowUserPortIn
import com.twitter.users.domain.User
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/users")
class UserController(
    private val createUserPortIn: CreateUserPortIn,
    private val followUserPortIn: FollowUserPortIn,
) {
    @PostMapping
    suspend fun create(@RequestBody user: User): User =
        createUserPortIn.execute(user)
            .awaitSingle()

    @PostMapping("/{userId}/follow/{followedId}")
    fun follow(@PathVariable userId: Long, @PathVariable followedId: Long) = followUserPortIn.execute(userId,followedId)
}