package com.livrosja.controller.mapper

import com.livrosja.controller.request.PostPurchaseRequest
import com.livrosja.model.PurchaseModel
import com.livrosja.service.BookService
import com.livrosja.service.CustomersService
import org.springframework.stereotype.Component


@Component
class PurchaseMapper(
    private val bookService: BookService,
    private val customersService: CustomersService
) {

    fun toModel(request: PostPurchaseRequest): PurchaseModel {
        val costumer = customersService.getById(request.customerId)
        val books = bookService.findAllByIds(request.bookIds)

        return PurchaseModel(
            customer = costumer,
            books = books.toMutableList(),
            price = books.sumOf {it.price}
        )
    }

}