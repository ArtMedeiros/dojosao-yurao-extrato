package br.com.zup.extrato

import java.math.BigDecimal
import java.time.LocalDateTime

data class ExtratoResponse(
    val operacao: Operacao,
    val idCliente: Long,
    val idConta: Long,
    val valor: BigDecimal,
    val dataTransacao: LocalDateTime
) {

    companion object {
        fun fromExtrato(extrato: Extrato): ExtratoResponse {
            return ExtratoResponse(
                operacao = extrato.operacao,
                idCliente = extrato.idCliente,
                idConta = extrato.idConta,
                valor = extrato.valor,
                dataTransacao = extrato.dataTransacao
            )
        }
    }
}
