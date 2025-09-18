package com.livrosja.controller

import com.livrosja.controller.mapper.PurchaseMapper
import com.livrosja.controller.request.PostPurchaseRequest
import com.livrosja.controller.response.PurchaseResponse
import com.livrosja.extesion.toPurchaseResponse
import com.livrosja.service.PurchaseService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("purchases")
class PurchaseController(
    private val purchaseService: PurchaseService,
    private val purchaseMapper: PurchaseMapper,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // efetua uma compra de um ou mais livros
    fun purchase(@RequestBody @Valid request: PostPurchaseRequest){
        purchaseService.create(purchaseMapper.toModel(request))
    }

    @GetMapping("/purchased/{id}") // retorna os livros comprados de um usuário de acordo com seu id
    fun listPurchased(@PathVariable id: Int, @PageableDefault(page = 0, size = 10) pageable: Pageable): Page<PurchaseResponse> {
        return purchaseService.findById(id, pageable).map{it.toPurchaseResponse()}
    }
}