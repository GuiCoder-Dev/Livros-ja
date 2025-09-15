package com.livrosja.event.listener


import com.livrosja.event.PurchaseEvent
import com.livrosja.service.BookService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component


@Component
class UptadeBookStatusSoldListener(
    private val bookService: BookService
) {


    @EventListener
    fun listen(purchaseEvent: PurchaseEvent) {
        bookService.purchase(purchaseEvent.purchaseModel.books)
    }




}