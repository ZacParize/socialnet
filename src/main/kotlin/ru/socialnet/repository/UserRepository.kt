package ru.socialnet.repository

import jakarta.inject.Singleton
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.impl.DSL.field
import org.jooq.impl.DSL.table
import ru.socialnet.model.User
import java.time.LocalDate

@Singleton
class UserRepository(private val dsl: DSLContext) {
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
                user.password
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

    fun findByLoginAndPassword(login: String, password: String): User? {
        val rec = dsl.select()
            .from(table("users"))
            .where(field("first_name").eq(login))
            .and(field("password").eq(password))
            .fetchOne() ?: return null
        return rec.toUser()
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