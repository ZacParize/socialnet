package ru.socialnet.controller.request

import io.micronaut.serde.annotation.Serdeable
import ru.socialnet.model.User
import java.time.LocalDate

@Serdeable
data class RegisterRequest(
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
    val gender: String,
    val interests: String,
    val city: String,
    val password: String
) {
    fun mapToEntity(): User {
        return User(
            this.id,
            this.firstName,
            this.lastName,
            this.birthDate,
            this.gender,
            this.interests,
            this.city,
            this.password
        )
    }
}