package com.twitter.tweets.adapter.`in`.kafka

import com.twitter.shared.logging.CompanionLogger
import com.twitter.shared.utils.kafka.ConsumerMessageResolver
import com.twitter.shared.utils.kafka.KafkaMessageConsumer
import com.twitter.tweets.application.port.`in`.PersistTweetPortIn
import com.twitter.tweets.domain.Tweet
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.support.Acknowledgment
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component


@Component
class PersistTweetConsumer(
    private val persistTweet: PersistTweetPortIn,
    consumerMessageResolver: ConsumerMessageResolver
): KafkaMessageConsumer(consumerMessageResolver) {
    @KafkaListener(topics = ["\${event.topic.tweets.created}"], groupId = "\${event.group.persist.tweets}")
    @RetryableTopic(retryTopicSuffix = ".persist-retry", dltTopicSuffix = ".persist-dlt")
    suspend fun execute(@Payload message: String, @Header(KafkaHeaders.RECEIVED_TOPIC) topic: String, ack: Acknowledgment) =
        generateConsumerMessage<Tweet>(message, topic)
            .consume(ack) {
                it.message
                    .let { tweet -> persistTweet.persist(tweet)}
                    .log { info("tweet $it is persisted in database")  }
            }

    companion object: CompanionLogger()
}
