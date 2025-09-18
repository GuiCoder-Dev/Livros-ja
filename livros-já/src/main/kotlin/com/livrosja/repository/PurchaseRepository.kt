package com.livrosja.repository

import com.livrosja.model.PurchaseModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository


interface PurchaseRepository: JpaRepository<PurchaseModel, Int> {

    fun findByCustomerId(customerId: Int, pageable: Pageable): Page<PurchaseModel>
}
