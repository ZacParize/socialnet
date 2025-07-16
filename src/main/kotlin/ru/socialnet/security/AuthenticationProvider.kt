package ru.socialnet.security

import io.micronaut.core.annotation.NonNull
import io.micronaut.core.annotation.Nullable
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationFailureReason
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.provider.HttpRequestAuthenticationProvider
import io.micronaut.security.rules.SecurityRule
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.socialnet.repository.UserRepository

@Singleton
class AuthenticationProvider<B>(private val userRepository: UserRepository) : HttpRequestAuthenticationProvider<B> {

    private val log: Logger = LoggerFactory.getLogger(AuthenticationProvider::class.java)

    override fun authenticate(
        requestContext: @Nullable HttpRequest<B>?,
        authRequest: @NonNull AuthenticationRequest<String, String>
    ): @NonNull AuthenticationResponse? {
        val id = authRequest.identity.toInt()
        val password = authRequest.secret as String
        log.info("Auth request {}, {}", id, password)
        val user = userRepository.findByIdAndPassword(id, password)
        return if (user != null) AuthenticationResponse.success(user.id.toString(), listOf(SecurityRule.IS_AUTHENTICATED, "USER"))
            else AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH)
    }
}