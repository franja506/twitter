package com.twitter.users.application.port.out

import com.twitter.users.domain.User
import reactor.core.publisher.Mono

interface CreateUserPortOut {
     suspend infix fun create(user: User): Mono<User>
}