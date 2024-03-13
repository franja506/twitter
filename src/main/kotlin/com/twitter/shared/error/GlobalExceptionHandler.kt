package com.twitter.shared.error

import com.twitter.shared.logging.CompanionLogger
import io.micrometer.tracing.Tracer
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.server.ServerWebExchange
import java.time.OffsetDateTime


@RestControllerAdvice
class GlobalExceptionHandler(
    private val tracer: Tracer
): ResponseEntityExceptionHandler() {

    @ExceptionHandler(RuntimeException::class)
    protected suspend fun handleNotFound(ex: RuntimeException, exchange: ServerWebExchange): ProblemDetail {
        log.error("Error: ", ex)
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.message!!)
        problemDetail.title = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase
        problemDetail.type = exchange.request.uri //URI.create("https://example.com/problems/user-not-found")
        //problemDetail.setProperty("errors", List.of<Any>(ErrorDetails.API_USER_NOT_FOUND))
        problemDetail.setProperty("errors", ex.cause?.message ?: emptyList<String>())
        problemDetail.setProperty("trace_id", tracer.currentTraceContext().context()?.traceId() ?: "ssss")
        problemDetail.setProperty("timestamp", OffsetDateTime.now())
        return problemDetail
    }

    companion object : CompanionLogger()
}