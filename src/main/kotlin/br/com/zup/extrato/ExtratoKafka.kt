package br.com.zup.extrato

import java.math.BigDecimal
import java.time.LocalDateTime

data class ExtratoKafka(
    val operacao: Operacao,
    val valor: BigDecimal,
    val dataTransacao: LocalDateTime,
    val idCliente: Long,
    val idConta: Long
) {
    fun toExtrato(): Extrato = Extrato(
        operacao = operacao,
        valor = valor,
        dataTransacao = dataTransacao,
        idCliente = idCliente,
        idConta = idConta
    )
}