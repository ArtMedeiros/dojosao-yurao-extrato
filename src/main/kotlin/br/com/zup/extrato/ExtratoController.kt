package br.com.zup.extrato

import org.jboss.logging.Logger
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/extrato")
class ExtratoController(
    val repository: ExtratoRepository
) {

    val log = Logger.getLogger(this::class.java)

    @GetMapping("/{idCliente}")
    fun buscarExtrato(@PathVariable idCliente: Long, pageable: Pageable = PageRequest.ofSize(20)): ResponseEntity<List<ExtratoResponse>> {
        val page = if (pageable.pageSize > 50) PageRequest.ofSize(20) else pageable

        log.info("Retornando ${page.pageSize} operações realizadas pelo cliente")

        return repository.findByIdClienteOrderByDataTransacaoDesc(idCliente, page)
            .map { ExtratoResponse.fromExtrato(it) }
            .let { ResponseEntity.ok(it) }
    }
}