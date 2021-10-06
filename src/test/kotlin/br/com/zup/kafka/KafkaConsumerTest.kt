package br.com.zup.kafka

import br.com.zup.extrato.ExtratoKafka
import br.com.zup.extrato.ExtratoRepository
import br.com.zup.extrato.Operacao
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import java.math.BigDecimal
import java.time.LocalDateTime

@SpringBootTest
internal class KafkaConsumerTest(
    @Autowired val kafkaConsumer: KafkaConsumer,
    @Autowired val repository: ExtratoRepository
) {

    lateinit var extratoKafka: ExtratoKafka

    @BeforeEach
    internal fun setUp() {
        extratoKafka = ExtratoKafka(
            operacao = Operacao.RECARGA_CELULAR,
            valor = BigDecimal(50.0),
            dataTransacao = LocalDateTime.now(),
            idCliente = 2,
            idConta = 2
        )
    }

    @Test
    internal fun `deve salvar a operacao`() {
        kafkaConsumer.consume(extratoKafka)
        val obj = repository.findByIdClienteOrderByDataTransacaoDesc(extratoKafka.idCliente, PageRequest.ofSize(20))

        assertTrue(obj.isNotEmpty())
    }
}