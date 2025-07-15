package ru.socialnet.controller.request

data class RegisterRequest(
    val firstName: String,
    val secondName: String,
    val birthDate: String,
    val biography: String?,
    val city: String?,
    val password: String
)