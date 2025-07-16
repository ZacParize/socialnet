package ru.socialnet.security

import io.micronaut.core.annotation.NonNull
import io.micronaut.core.annotation.Nullable
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationFailureReason
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.provider.HttpRequestAuthenticationProvider
import jakarta.inject.Singleton
import ru.socialnet.repository.UserRepository

@Singleton
class AuthenticationProvider<B>(private val userRepository: UserRepository) : HttpRequestAuthenticationProvider<B> {

    override fun authenticate(
        requestContext: @Nullable HttpRequest<B>?,
        authRequest: @NonNull AuthenticationRequest<String, String>
    ): @NonNull AuthenticationResponse? {
        val id = authRequest.identity.toInt()
        val password = authRequest.secret as String
        val user = userRepository.findByIdAndPassword(id, password)
        return if (user != null) AuthenticationResponse.success(user.id.toString(), listOf("USER"))
            else AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH)
    }
}