package com.twitter.timeline.adapter.`in`.web

import com.twitter.shared.logging.CompanionLogger
import com.twitter.timeline.application.port.`in`.GetTimelineByIdPortIn
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/timeline")
class TimelineController(
    private val getTimelineByIdPortIn: GetTimelineByIdPortIn
) {

    @GetMapping("/{userId}")
    fun retrieve(@PathVariable userId: Long) = getTimelineByIdPortIn.execute(userId)


    companion object: CompanionLogger()
}