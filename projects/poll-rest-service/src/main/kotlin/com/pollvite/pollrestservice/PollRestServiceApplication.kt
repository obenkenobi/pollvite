package com.pollvite.pollrestservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PollRestServiceApplication

fun main(args: Array<String>) {
    runApplication<PollRestServiceApplication>(*args)
}
