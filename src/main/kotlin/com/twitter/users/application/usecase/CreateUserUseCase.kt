package com.twitter.users.application.usecase

import com.twitter.shared.logging.CompanionLogger
import com.twitter.users.application.port.out.CreateUserPortOut
import com.twitter.users.application.port.`in`.CreateUserPortIn
import com.twitter.users.domain.User
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CreateUserUseCase(
    private val createUserPortOut: CreateUserPortOut
): CreateUserPortIn {
    override suspend fun execute(user: User): Mono<User> =
        createUserPortOut.create(user)
            .log { info("")}

    companion object: CompanionLogger()
}