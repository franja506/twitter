package com.twitter.shared.error

import com.twitter.shared.logging.CompanionLogger
import io.micrometer.tracing.Tracer
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.server.ServerWebExchange
import java.time.OffsetDateTime
import java.util.function.Consumer


@RestControllerAdvice
class GlobalExceptionHandler(
    private val tracer: Tracer,
): ResponseEntityExceptionHandler() {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException): ResponseEntity<Any> {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage
        })

        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed for request")
        problemDetail.title = "Bad Request"
        problemDetail.setProperty("errors", errors)
        return ResponseEntity(problemDetail, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    protected fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException): ResponseEntity<Any> {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.localizedMessage)
        problemDetail.title = "Bad Request"
        return ResponseEntity(problemDetail, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(RuntimeException::class)
    protected suspend fun handleNotFound(ex: RuntimeException, exchange: ServerWebExchange): ProblemDetail {
        log.error("Error: ", ex)
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.message!!)
        problemDetail.title = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase
        problemDetail.type = exchange.request.uri
        problemDetail.setProperty("errors", ex.cause?.message ?: emptyList<String>())
        problemDetail.setProperty("trace_id", tracer.currentTraceContext().context()?.traceId() ?: "ssss")
        problemDetail.setProperty("timestamp", OffsetDateTime.now())
        return problemDetail
    }

    @ExceptionHandler(Exception::class)
    protected fun handleException(ex: Exception): ResponseEntity<Any> {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.localizedMessage)
        problemDetail.title = "Internal Server Error"
        return ResponseEntity(problemDetail, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    companion object : CompanionLogger()
}