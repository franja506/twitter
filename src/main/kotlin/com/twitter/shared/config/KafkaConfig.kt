package com.twitter.shared.config

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.common.config.TopicConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin


@Configuration
class KafkaConfig {

    @Value(value = "\${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapAddress: String


    @Bean
    fun admin() = KafkaAdmin(mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapAddress))

    @Bean
    fun createdTweetTopic() =
        TopicBuilder.name("created.tweet")
            .partitions(3)
            .replicas(1)
            .config(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE)
            .build()

    @Bean
    fun updatedTimelineTopic() =
        TopicBuilder.name("updated.timeline")
            .partitions(3)
            .replicas(1)
            .config(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE)
            .build()

    fun updatedTimelinesFollowerTopic() =
        TopicBuilder.name("updated.timelines.follower")
            .partitions(3)
            .replicas(1)
            .config(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE)
            .build()

}