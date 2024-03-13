package com.twitter.tweets.adapter.`in`.web

import com.ninjasquad.springmockk.MockkBean
import com.twitter.users.adapter.`in`.web.UserController
import com.twitter.users.application.port.`in`.CreateUserPortIn
import com.twitter.users.application.port.`in`.FollowUserPortIn
import com.twitter.users.domain.User
import io.mockk.coEvery
import io.mockk.coVerify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@WebFluxTest(controllers = [UserController::class])
@AutoConfigureObservability
class UserControllerTest {

    @Autowired
    private lateinit var client: WebTestClient

    @MockkBean
    private lateinit var createUserPortIn: CreateUserPortIn

    @MockkBean
    private lateinit var followUserPortIn: FollowUserPortIn

    @Test
    fun `should create a user`() {

        val user = User(1, "john_doe")

        coEvery { createUserPortIn.execute(user) } returns Mono.just(user)


        client.post()
            .uri("v1/users")
            .bodyValue(user)
            .exchange()
            .expectStatus()
            .isOk
            .expectBody(User::class.java)

        coVerify { createUserPortIn.execute(user) }
    }

}