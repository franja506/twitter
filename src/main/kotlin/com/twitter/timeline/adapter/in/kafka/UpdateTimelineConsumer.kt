package com.twitter.timeline.adapter.`in`.kafka

import com.twitter.timeline.application.port.`in`.PropagateTimelinePortIn
import com.twitter.tweets.domain.Tweet
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class UpdateTimelineConsumer(
    private val propagateTimelinePortIn: PropagateTimelinePortIn
) {
    @KafkaListener(topics = ["\${event.topic.timelines.update}"], groupId = "\${event.group.update.timeline}")
    @RetryableTopic(retryTopicSuffix = ".propagate-retry", dltTopicSuffix = ".propagate-dlt")
    suspend fun execute(@Payload tweet: Tweet, ack: Acknowledgment) =
         propagateTimelinePortIn.propagate(tweet)

}