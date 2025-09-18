package com.livrosja.event.listener


import com.livrosja.event.PurchaseEvent
import com.livrosja.service.BookService
import org.springframework.context.event.EventListener
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component


@Component
class UptadeBookStatusSoldListener(
    private val bookService: BookService
) {


    @EventListener
    @Order(1)
    fun listen(purchaseEvent: PurchaseEvent) {
        bookService.purchase(purchaseEvent.purchaseModel.books)
    }

}