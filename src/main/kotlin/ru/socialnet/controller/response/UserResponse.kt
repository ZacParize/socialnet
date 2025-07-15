package ru.socialnet.controller.response

import ru.socialnet.model.User

data class UserResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val interests: String?,
    val city: String?
) {
    companion object {
        fun from(user: User?): UserResponse? {
            return if (user != null)
                UserResponse(
                    user.id.toString(),
                    user.firstName,
                    user.lastName,
                    user.birthDate.toString(),
                    user.interests,
                    user.city
                )
            else null
        }
    }
}