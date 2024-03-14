package com.twitter.timeline.adapter.`in`.kafka

import com.twitter.shared.logging.CompanionLogger
import com.twitter.timeline.application.port.`in`.UpdateFollowerTimelinePortIn
import com.twitter.tweets.domain.Tweet
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class UpdateFollowerTimelineConsumer(
    private val updateFollower: UpdateFollowerTimelinePortIn
) {

    @KafkaListener(topics = ["\${event.topic.timelines.updateFollowers}"], groupId = "\${event.group.update.followers-timeline}")
    @RetryableTopic(retryTopicSuffix = ".persist-retry", dltTopicSuffix = ".persist-dlt")
    suspend fun execute(@Payload pair: Pair<Long, Tweet>, ack: Acknowledgment): Pair<Long, Tweet> = updateFollower.update(pair)


    companion object: CompanionLogger()
}