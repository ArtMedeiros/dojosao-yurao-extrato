package br.com.zup.kafka

import br.com.zup.extrato.ExtratoKafka
import br.com.zup.extrato.ExtratoRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumer(
    val repository: ExtratoRepository
) {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(topics=["\${kafka.consumer.topic}"], groupId = "\${kafka.consumer.group.id}")
    fun consume(message: ExtratoKafka) {
        repository.save(message.toExtrato())
        log.info("Transação de ${message.operacao} salva com sucesso")
    }
}