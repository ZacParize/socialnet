package ru.socialnet.controller.response

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class RegisterResponse(val userId: String)