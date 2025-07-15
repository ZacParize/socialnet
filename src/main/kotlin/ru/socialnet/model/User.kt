package ru.socialnet.model

import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDate

@Serdeable
data class User(
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
    val gender: String,
    val interests: String,
    val city: String,
    val password: String // для простоты, в реальности хранить хэш!
)
