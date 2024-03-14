package com.twitter.timeline.application.port.out

import com.twitter.tweets.domain.Tweet
import org.springframework.data.redis.connection.zset.Tuple
import reactor.core.publisher.Mono

interface UpdateFollowerTimelinePortOut {

    fun updateFollower(pair: Pair<Long, Tweet>): Mono<Pair<Long, Tweet>>
}