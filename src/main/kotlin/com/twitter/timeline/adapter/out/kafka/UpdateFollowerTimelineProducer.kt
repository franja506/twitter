package com.twitter.timeline.adapter.out.kafka

import com.twitter.shared.utils.kafka.KafkaObjectMapper
import com.twitter.timeline.application.port.out.UpdateFollowerTimelinePortOut
import com.twitter.tweets.adapter.out.kafka.CreateTweetProducer.Companion.log
import com.twitter.tweets.domain.Tweet
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class UpdateFollowerTimelineProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    @Value("\${event.topic.timelines.updateFollowers}")
    private val topic: String,
    private val objectMapper: KafkaObjectMapper
): UpdateFollowerTimelinePortOut {

    override fun updateFollower(pair: Pair<Long, Tweet>): Pair<Long, Tweet> =
         kafkaTemplate.send(topic, pair.first.toString(), pair.asMessage())
             .let { pair }
             .log { info("created update timeline follower produced: {}", pair) }

    private fun Pair<Long,Tweet>.asMessage() = objectMapper.serialize(this)
}