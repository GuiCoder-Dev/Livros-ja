package com.livrosja.repository

import com.livrosja.model.PurchaseModel
import org.springframework.data.jpa.repository.JpaRepository

interface PurchaseRepository: JpaRepository<PurchaseModel, Int> {

}
