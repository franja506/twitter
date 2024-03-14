package com.twitter.tweets.adapter.out.kafka

import com.twitter.shared.error.exceptions.QueueProducerNotWrittenException
import com.twitter.shared.logging.CompanionLogger
import com.twitter.tweets.application.port.out.CreateTweetPortOut
import com.twitter.tweets.domain.Tweet
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CreateTweetProducer(
    private val kafkaTemplate: KafkaTemplate<String, Tweet>,
    @Value("\${event.topic.tweets.created}")
    private val topic: String,
) : CreateTweetPortOut {
    override suspend fun execute(tweet: Tweet): Mono<Tweet> =
        kafkaTemplate.send(topic, tweet.id.toString(), tweet)
            .log { info("created tweet event produced: {}", tweet) }
            .let { Mono.just(tweet) }
            .onErrorResume { e ->
                Mono.error(
                    QueueProducerNotWrittenException(
                        message = "Error with produce createTweet event: $tweet", e
                    )
                )
            }

    companion object: CompanionLogger()
}
