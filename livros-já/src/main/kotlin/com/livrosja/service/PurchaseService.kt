package com.livrosja.service


import com.livrosja.enums.Errors
import com.livrosja.event.PurchaseEvent
import com.livrosja.exception.NotFoundException
import com.livrosja.model.PurchaseModel
import com.livrosja.repository.CustomerRepository
import com.livrosja.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository,
    private val aplicationEventPublisher: ApplicationEventPublisher,
    private val customerRepository: CustomerRepository
) {

    //POST
    fun create(purchaseModel: PurchaseModel) {

        aplicationEventPublisher.publishEvent(PurchaseEvent(purchaseModel, purchaseModel))

    }

    fun update(purchaseModel: PurchaseModel) {
        purchaseRepository.save(purchaseModel)
    }

    //GET
    fun findById(customerId: Int, pageable: Pageable): Page<PurchaseModel> {
        val purchases = purchaseRepository.findByCustomerId(customerId, pageable)

        if(!customerRepository.existsById(customerId)) {
            throw NotFoundException(Errors.LJ1101.message.format(customerId), Errors.LJ1101.code)
        }

        return purchases
    }

}
