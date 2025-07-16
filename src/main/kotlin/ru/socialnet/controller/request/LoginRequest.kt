package ru.socialnet.controller.request

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class LoginRequest(val id: String?, val password: String?)