package br.com.zup.extrato

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ExtratoRepository : JpaRepository<Extrato, Long> {

    fun findByIdClienteOrderByDataTransacaoDesc(idCliente: Long, pageable: Pageable): List<Extrato>
}