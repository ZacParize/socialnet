package ru.socialnet.repository

import jakarta.inject.Singleton
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.impl.DSL.field
import org.jooq.impl.DSL.table
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import ru.socialnet.model.User
import java.time.LocalDate

@Singleton
class UserRepository(private val dsl: DSLContext) {

    private val encoder = BCryptPasswordEncoder()

    fun create(user: User): Int? {
        return dsl.insertInto(table("users"))
            .columns(
                field("first_name"),
                field("last_name"),
                field("birth_date"),
                field("gender"),
                field("interests"),
                field("city"),
                field("password")
            )
            .values(
                user.firstName,
                user.lastName,
                user.birthDate,
                user.gender,
                user.interests,
                user.city,
                encoder.encode(user.password)
            )
            .returning(field("id"))
            .fetchOne()
            ?.getValue("id", Int::class.java)
    }

    fun findById(id: Int): User? {
        val rec = dsl.select()
            .from(table("users"))
            .where(field("id").eq(id))
            .fetchOne() ?: return null
        return rec.toUser()
    }

    fun findByLogin(login: String): List<User> {
        return dsl.select()
            .from(table("users"))
            .where(field("first_name").eq(login))
            .fetch()
            .map { it.toUser() }
    }

    fun findByLoginAndPassword(login: String, password: String): User? {
        return findByLogin(login).find { encoder.matches(password, it.password) }
    }

    fun findByIdAndPassword(id: Int, password: String): User? {
        val user = findById(id)
        return if (user != null && encoder.matches(password, user.password)) user else null;
    }

    private fun Record.toUser(): User = User(
        id = this.getValue("id", Int::class.java),
        firstName = this.getValue("first_name", String::class.java) ?: "",
        lastName = this.getValue("last_name", String::class.java) ?: "",
        birthDate = this.getValue("birth_date", LocalDate::class.java) ?: LocalDate.now(),
        gender = this.getValue("gender", String::class.java) ?: "",
        interests = this.getValue("interests", String::class.java) ?: "",
        city = this.getValue("city", String::class.java) ?: "",
        password = this.getValue("password", String::class.java) ?: ""
    )
}