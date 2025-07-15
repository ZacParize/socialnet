package ru.socialnet.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.socialnet.controller.request.UserRequest
import ru.socialnet.controller.response.UserResponse
import ru.socialnet.model.User
import ru.socialnet.service.UserService

@Controller
class UserController(private val userService: UserService) {
    private val log: Logger = LoggerFactory.getLogger(UserController::class.java)

    @Post("/user/register")
    @Produces(MediaType.APPLICATION_JSON)
    fun register(@Body user: UserRequest): HttpResponse<Any> {
        val id = userService.register(user.mapToEntity())
        return if (id != null) HttpResponse.ok(mapOf("id" to id)) else HttpResponse.serverError()
    }

    @Get(value = "/user/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUser(@PathVariable id: String): HttpResponse<UserResponse> {
        val safeId = id.toIntOrNull() ?: return HttpResponse.notFound()
        val user = UserResponse.from(userService.getUser(safeId))
        log.info("Found user by id: {}", user)
        return if (user != null) HttpResponse.ok(user) else HttpResponse.notFound()
    }

    @Post("/login")
    @Produces(MediaType.APPLICATION_JSON)
    fun login(@Body credentials: Map<String, String>): HttpResponse<Any> {
        val login = credentials["login"] ?: return HttpResponse.badRequest(mapOf("error" to "login required"))
        val password = credentials["password"] ?: return HttpResponse.badRequest(mapOf("error" to "password required"))
        val user = userService.login(login, password)
        return if (user != null) HttpResponse.ok(user) else HttpResponse.unauthorized()
    }
}