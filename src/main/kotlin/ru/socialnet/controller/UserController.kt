package ru.socialnet.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Produces
import io.micronaut.security.annotation.Secured
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.socialnet.controller.response.UserResponse
import ru.socialnet.service.UserService

@Controller
@Secured("USER")
class UserController(private val userService: UserService) {
    private val log: Logger = LoggerFactory.getLogger(UserController::class.java)

    @Get(value = "/user/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUser(@PathVariable id: String): HttpResponse<UserResponse> {
        val safeId = id.toIntOrNull() ?: return HttpResponse.notFound()
        val user = UserResponse.from(userService.getUser(safeId))
        log.info("Found user by id: {}", user)
        return if (user != null) HttpResponse.ok(user) else HttpResponse.notFound()
    }
}