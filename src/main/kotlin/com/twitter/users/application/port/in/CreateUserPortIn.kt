package com.twitter.users.application.port.`in`

import com.twitter.users.domain.User
import reactor.core.publisher.Mono

interface CreateUserPortIn {
    suspend infix fun execute(user: User): Mono<User>
}