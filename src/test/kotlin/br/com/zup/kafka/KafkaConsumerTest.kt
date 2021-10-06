package br.com.zup.kafka

import br.com.zup.extrato.ExtratoKafka
import br.com.zup.extrato.ExtratoRepository
import br.com.zup.extrato.Operacao
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageRequest
import org.springframework.kafka.support.Acknowledgment
import java.math.BigDecimal
import java.time.LocalDateTime

@SpringBootTest
internal class KafkaConsumerTest(
    @Autowired val kafkaConsumer: KafkaConsumer,
    @Autowired val repository: ExtratoRepository
) {
    @MockBean
    lateinit var ack: Acknowledgment

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

        repository.deleteAll()
    }

    @Test
    internal fun `deve salvar a operacao`() {
        kafkaConsumer.consume(extratoKafka, ack)
        val obj = repository.findByIdClienteOrderByDataTransacaoDesc(extratoKafka.idCliente, PageRequest.ofSize(20))

        assertTrue(obj.isNotEmpty())
    }

    @Test
    internal fun `nao deve salvar operacao`() {
        val extratoMock = Mockito.mock(ExtratoKafka::class.java)

        given(extratoMock.toExtrato()).willThrow(IllegalArgumentException::class.java)

        kafkaConsumer.consume(extratoMock, ack)

        val obj = repository.findByIdClienteOrderByDataTransacaoDesc(extratoKafka.idCliente, PageRequest.ofSize(20))
        assertTrue(obj.isEmpty())
    }
}