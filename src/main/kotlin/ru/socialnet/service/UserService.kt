package ru.socialnet.service

import jakarta.inject.Singleton
import ru.socialnet.model.User
import ru.socialnet.repository.UserRepository

@Singleton
class UserService(private val userRepository: UserRepository) {
    fun register(user: User): Int? = userRepository.create(user)
    fun getUser(id: Int): User? = userRepository.findById(id)
    fun login(id: String, password: String): User? = userRepository.findByIdAndPassword(id.toInt(), password)
}