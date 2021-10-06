package br.com.zup.extrato

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
internal class ExtratoControllerTest(
    @Autowired val repository: ExtratoRepository,
    @Autowired val mockMvc: MockMvc
){

    lateinit var extrato: Extrato

    @BeforeEach
    internal fun setUp() {
        repository.deleteAll()

        extrato = Extrato(
            operacao = Operacao.PAGAMENTO_BOLETO,
            valor = BigDecimal(125.0),
            dataTransacao = LocalDateTime.now(),
            idCliente = 1,
            idConta = 1
        )
        repository.save(extrato)
    }

    @Test
    internal fun `deve retornar um extrato`() {
        mockMvc.perform(get("/api/1/extrato"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("*operacao").value(Operacao.PAGAMENTO_BOLETO.name))
            .andDo(print())
    }

    @Test
    internal fun `nao deve conter nenhum dado de extrato de cliente nao cadastrado`() {
        mockMvc.perform(get("/api/2/extrato"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string("[]"))
            .andDo(print())
    }
}