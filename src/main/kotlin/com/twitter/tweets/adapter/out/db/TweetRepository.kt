package com.twitter.tweets.adapter.out.db

import com.twitter.tweets.application.port.out.PersistTweetPortOut
import com.twitter.tweets.domain.Tweet
import io.r2dbc.spi.ConnectionFactory
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class TweetRepository(
    private val connectionFactory: ConnectionFactory
): PersistTweetPortOut {

    private val dbClient = DatabaseClient.create(connectionFactory)
    override suspend fun persist(tweet: Tweet): Mono<Tweet> =
        dbClient.sql("insert into tweet (id, user_id, body) values (:id, :user_id, :body)")
            .bind("id", tweet.id!!)
            .bind("user_id", tweet.userId)
            .bind("body", tweet.body)
            .fetch()
            .rowsUpdated()
            .thenReturn(tweet)
            .onErrorResume { e -> Mono.error(e) }
}