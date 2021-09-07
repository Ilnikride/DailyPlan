package com.planing.day

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling

@EnableFeignClients
@EnableScheduling
@SpringBootApplication
class DayApplication

fun main(vararg args: String) {
    runApplication<DayApplication>(*args)
}
