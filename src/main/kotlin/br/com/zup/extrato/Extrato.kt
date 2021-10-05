package br.com.zup.extrato

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.EnumType.STRING
import javax.persistence.GenerationType.IDENTITY
import javax.validation.constraints.NotNull

@Entity
class Extrato(
    @field:NotNull
    @Enumerated(STRING)
    val operacao: Operacao,

    @field:NotNull
    val valor: BigDecimal,

    @field:NotNull
    val dataTransacao: LocalDateTime,

    @field:NotNull
    val idCliente: Long,

    @field:NotNull
    val idConta: Long
) {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long? = null
}
