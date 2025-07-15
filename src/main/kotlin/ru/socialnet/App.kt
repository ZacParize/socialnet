package ru.socialnet

import io.micronaut.runtime.Micronaut

object App {
    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.run(*args)
    }
}