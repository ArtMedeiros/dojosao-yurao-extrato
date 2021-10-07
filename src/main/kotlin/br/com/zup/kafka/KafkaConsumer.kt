package br.com.zup.kafka

import br.com.zup.extrato.ExtratoKafka
import br.com.zup.extrato.ExtratoRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class KafkaConsumer(
    val repository: ExtratoRepository
) {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    @KafkaListener(topics=["\${kafka.consumer.topic}"], groupId = "\${kafka.consumer.group.id}")
    fun consume(message: ExtratoKafka, ack:Acknowledgment) {
        //Caso a operação ocorra sem erros, é feito o commit da mensagem
        try {
            repository.save(message.toExtrato())
            ack.acknowledge()
            log.info("Transação de ${message.operacao} salva com sucesso")
        } catch (e: Exception) {
            log.error("Não foi possível processar a mensage: $message\nErro inesperado ${e.message}")
        }
    }
}