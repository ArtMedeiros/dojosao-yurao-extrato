package br.com.zup.extrato

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/{idCliente}/extrato")
class ExtratoController(
    val repository: ExtratoRepository
) {
    @Value(value = "\${kafka.consumer.topic}")
    private lateinit var topicName:String

    @GetMapping
    fun buscarExtrato(@PathVariable idCliente: Long): ResponseEntity<List<ExtratoResponse>> {
        return repository.findByIdClienteOrderByDataTransacaoDesc(idCliente, PageRequest.ofSize(20))
            .map { ExtratoResponse.fromExtrato(it) }
            .let { ResponseEntity.ok(it) }
    }
}