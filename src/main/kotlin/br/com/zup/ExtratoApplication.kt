package br.com.zup

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication
@EnableKafka
class ExtratoApplication

fun main(args: Array<String>) {
	runApplication<ExtratoApplication>(*args)
}
