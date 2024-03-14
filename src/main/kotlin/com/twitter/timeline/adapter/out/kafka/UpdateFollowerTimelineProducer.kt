package com.twitter.timeline.adapter.out.kafka

import com.twitter.shared.error.exceptions.QueueProducerNotWrittenException
import com.twitter.timeline.application.port.out.UpdateFollowerTimelinePortOut
import com.twitter.tweets.adapter.out.kafka.CreateTweetProducer.Companion.log
import com.twitter.tweets.domain.Tweet
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class UpdateFollowerTimelineProducer(
    private val kafkaTemplate: KafkaTemplate<String, Pair<Long, Tweet>>,
    @Value("\${event.topic.timelines.updateFollowers}")
    private val topic: String,
): UpdateFollowerTimelinePortOut {

    override fun updateFollower(pair: Pair<Long, Tweet>): Mono<Pair<Long, Tweet>> =
        kafkaTemplate.send(topic, pair.first.toString(), pair)
            .log { info("created update timeline follower produced: {}", pair) }
            .let { Mono.just(pair) }
            .onErrorResume { e ->
                Mono.error(
                    QueueProducerNotWrittenException(
                        message = "Error with produce updateTimelineFollower event for follower: ${pair.first} with tweet: ${pair.second}", e
                    )
                )
            }
}