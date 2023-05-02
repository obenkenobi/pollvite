package com.pollvite.pollservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PollServiceApplication

fun main(args: Array<String>) {
    runApplication<PollServiceApplication>(*args)
}
