package ru.socialnet.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Produces
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.socialnet.controller.response.UserResponse
import ru.socialnet.service.UserService

@Controller
@Secured(SecurityRule.IS_AUTHENTICATED, "USER")
class UserController(private val userService: UserService) {
    private val log: Logger = LoggerFactory.getLogger(UserController::class.java)

    @Get(value = "/user/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUser(@PathVariable id: String): HttpResponse<UserResponse> {
        log.info("Received request user by id: {}", id)
        val safeId = id.toIntOrNull() ?: return HttpResponse.notFound()
        val user = UserResponse.from(userService.getUser(safeId))
        log.info("Found user by id: {}", user)
        return if (user != null) HttpResponse.ok(user) else HttpResponse.notFound()
    }

    @Get(value = "/user/search/{firstName}/{secondName}")
    @Produces(MediaType.APPLICATION_JSON)
    fun search(@PathVariable firstName: String, @PathVariable secondName: String): HttpResponse<List<UserResponse?>> {
        log.info("Received request search by firstName: {}, secondName: {}", firstName, secondName)
        val users = userService.search(firstName, secondName).map { UserResponse.from(it) }
        log.info("Found users by by firstName: {}, secondName: {}, {}", firstName, secondName, users)
        return HttpResponse.ok(users)
    }
}