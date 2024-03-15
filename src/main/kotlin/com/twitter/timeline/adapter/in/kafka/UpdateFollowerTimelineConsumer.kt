package com.twitter.timeline.adapter.`in`.kafka

import com.twitter.shared.logging.CompanionLogger
import com.twitter.shared.utils.kafka.ConsumerMessageResolver
import com.twitter.timeline.application.port.`in`.UpdateFollowerTimelinePortIn
import com.twitter.tweets.domain.Tweet
import com.twitter.shared.utils.kafka.KafkaMessageConsumer
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.support.Acknowledgment
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class UpdateFollowerTimelineConsumer(
    private val updateFollower: UpdateFollowerTimelinePortIn,
    consumerMessageResolver: ConsumerMessageResolver
): KafkaMessageConsumer(consumerMessageResolver) {

    @KafkaListener(topics = ["\${event.topic.timelines.updateFollowers}"], groupId = "\${event.group.update.followers-timeline}")
    @RetryableTopic(retryTopicSuffix = ".retry", dltTopicSuffix = ".dlt")
    suspend fun execute(@Payload message: String, @Header(KafkaHeaders.RECEIVED_TOPIC) topic: String, ack: Acknowledgment) =
        generateConsumerMessage<Pair<Long, Tweet>>(message, topic)
            .consume(ack) {
                it.message
                    .let { userTimeline -> updateFollower.update(userTimeline)}
                    .log { (id,tweet) -> info("timeline for user $id has updated with tweet: $tweet") }
            }
    companion object: CompanionLogger()
}
