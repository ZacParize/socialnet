package ru.socialnet.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.socialnet.controller.request.LoginRequest
import ru.socialnet.controller.request.RegisterRequest
import ru.socialnet.controller.response.LoginResponse
import ru.socialnet.controller.response.RegisterResponse
import ru.socialnet.service.UserService

@Controller
@Secured(SecurityRule.IS_ANONYMOUS)
class AuthController (private val userService: UserService,
    private val jwtTokenGenerator: JwtTokenGenerator) {
    private val log: Logger = LoggerFactory.getLogger(AuthController::class.java)

    @Get("/test")
    @Produces(MediaType.APPLICATION_JSON)
    fun test(): HttpResponse<Any> {
        return HttpResponse.ok(mapOf("response" to "i am alive"))
    }

    @Post("/user/register")
    @Produces(MediaType.APPLICATION_JSON)
    fun register(@Body request: RegisterRequest): HttpResponse<Any> {
        log.info(request.toString())
        val id = userService.register(request.mapToEntity())
        return if (id != null) HttpResponse.ok(RegisterResponse(id.toString())) else HttpResponse.serverError()
    }

    @Post("/login")
    @Produces(MediaType.APPLICATION_JSON)
    fun login(@Body request: LoginRequest): HttpResponse<Any> {
        val id = request.id ?: return HttpResponse.badRequest(mapOf("error" to "id required"))
        val password = request.password ?: return HttpResponse.badRequest(mapOf("error" to "password required"))
        val user = userService.login(id, password) ?: return HttpResponse.unauthorized()
        val token = jwtTokenGenerator.generateToken(mapOf("id" to user.id.toString(), "role" to listOf("USER")))
            .orElse(null) ?: return HttpResponse.serverError()
        return HttpResponse.ok(LoginResponse(token))
    }
}