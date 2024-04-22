package kr.j_jun.jlog

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JlogApplication

fun main(args: Array<String>) {
    runApplication<JlogApplication>(*args)
}


