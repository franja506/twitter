package com.twitter.users.adapter.out.db

import com.twitter.users.application.port.out.CreateUserPortOut
import com.twitter.users.domain.User
import io.r2dbc.spi.ConnectionFactory
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.bind
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class UserRepository(
    private val connectionFactory: ConnectionFactory
): CreateUserPortOut {
    override suspend fun create(user: User): Mono<User> =
        DatabaseClient.create(connectionFactory)
            .sql("insert into user (id, username) values (:id, :username)")
            .bind("id", user.id)
            .bind("username", user.userName)
            .fetch()
            .rowsUpdated()
            .thenReturn(user)
}