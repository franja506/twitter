package com.twitter.users.adapter.out.db

import com.twitter.shared.error.exceptions.FollowException
import com.twitter.shared.logging.CompanionLogger
import com.twitter.users.application.port.out.CreateUserPortOut
import com.twitter.users.application.port.out.GetFollowersByIdPortOut
import com.twitter.users.application.port.out.PersistFollowedPortOut
import com.twitter.users.domain.User
import io.r2dbc.spi.ConnectionFactory
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.bind
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class UserRepository(
     connectionFactory: ConnectionFactory
): CreateUserPortOut, PersistFollowedPortOut, GetFollowersByIdPortOut {

    private val dbClient = DatabaseClient.create(connectionFactory)
    override suspend fun create(user: User): Mono<User> =
        dbClient.sql("insert into user (id, username, email) values (:id, :username, :email)")
            .bind("id", user.id)
            .bind("username", user.username)
            .bind("email", user.email)
            .fetch()
            .rowsUpdated()
            .thenReturn(user)
            .onErrorResume { e -> Mono.error(e)  }


    override suspend fun follow(followerId: Long, followedId: Long): Mono<Long> =
        dbClient.sql("insert into follower_followed (id_follower, id_followed) values (:followerId, :followedId)")
            .bind("followerId", followerId)
            .bind("followedId", followedId)
            .fetch()
            .rowsUpdated()
            .onErrorResume { e -> Mono.error(FollowException("Problems with follow people", e)) }

    override fun getById(followedId: Long): Flux<Long> =
        dbClient.sql("select id_follower from follower_followed where id_followed = :followedId")
            .bind("followedId", followedId)
            .mapValue(Long::class.java)
            .all()
            .onErrorResume { e -> Mono.error(FollowException("Problems with follow people", e)) }


}