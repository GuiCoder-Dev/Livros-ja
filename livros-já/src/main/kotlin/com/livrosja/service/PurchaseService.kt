package com.livrosja.service

import com.livrosja.event.PurchaseEvent
import com.livrosja.model.PurchaseModel
import com.livrosja.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository,
    private val aplicationEventPublisher: ApplicationEventPublisher
) {

    //POST
    fun create(purchaseModel: PurchaseModel) {

        // Fazer a verificação aqui

        aplicationEventPublisher.publishEvent(PurchaseEvent(purchaseModel, purchaseModel))


    }

    fun update(purchaseModel: PurchaseModel) {
        purchaseRepository.save(purchaseModel)
    }
}
