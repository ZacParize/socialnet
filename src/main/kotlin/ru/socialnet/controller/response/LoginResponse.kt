package ru.socialnet.controller.response

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class LoginResponse(val token: String)