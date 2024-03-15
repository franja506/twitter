package com.twitter.timeline.adapter.`in`.kafka

import com.twitter.shared.utils.kafka.ConsumerMessageResolver
import com.twitter.shared.utils.kafka.KafkaMessageConsumer
import com.twitter.timeline.application.port.`in`.PropagateTimelinePortIn
import com.twitter.tweets.domain.Tweet
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.support.Acknowledgment
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class UpdateTimelineConsumer(
    private val propagateTimelinePortIn: PropagateTimelinePortIn,
    consumerMessageResolver: ConsumerMessageResolver
): KafkaMessageConsumer(consumerMessageResolver) {
    @KafkaListener(topics = ["\${event.topic.tweets.created}"], groupId = "\${event.group.update.timeline}")
    @RetryableTopic(retryTopicSuffix = ".propagate-retry", dltTopicSuffix = ".propagate-dlt")
    fun execute(@Payload message: String, @Header(KafkaHeaders.RECEIVED_TOPIC) topic: String, ack: Acknowledgment) =
        generateConsumerMessage<Tweet>(message, topic)
            .consume(ack) {
                it.message
                    .let { tweet ->
                        propagateTimelinePortIn.propagate(tweet)
                            .log { info("tweet $tweet is distributed around his followers") }

                    }
            }

}