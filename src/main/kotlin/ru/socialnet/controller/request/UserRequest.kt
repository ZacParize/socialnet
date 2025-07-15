package ru.socialnet.controller.request

import ru.socialnet.controller.response.UserResponse
import ru.socialnet.model.User
import java.time.LocalDate

data class UserRequest(
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
    val gender: String,
    val interests: String,
    val city: String,
    val password: String // для простоты, в реальности хранить хэш!
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