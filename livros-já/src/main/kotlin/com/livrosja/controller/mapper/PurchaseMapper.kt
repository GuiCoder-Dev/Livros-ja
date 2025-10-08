package com.livrosja.controller.mapper

import com.livrosja.controller.request.PostPurchaseRequest
import com.livrosja.model.PurchaseModel
import com.livrosja.security.UserSecurityDetails
import com.livrosja.service.BookService
import com.livrosja.service.CustomersService
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component


@Component
class PurchaseMapper(
    private val bookService: BookService,
    private val customersService: CustomersService
) {

    fun toModel(request: PostPurchaseRequest, authentication: Authentication): PurchaseModel {
        val principal = authentication.principal as UserSecurityDetails
        val customer = customersService.getById(principal.id)
        val books = bookService.findAllByIds(request.bookIds)

        return PurchaseModel(
            customer = customer,
            books = books.toMutableList(),
            price = books.sumOf {it.price}
        )
    }

}