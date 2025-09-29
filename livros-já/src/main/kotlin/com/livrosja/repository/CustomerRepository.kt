package com.livrosja.repository

import com.livrosja.enums.CustomerStatus
import com.livrosja.model.CustomerModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository


interface CustomerRepository: JpaRepository<CustomerModel, Int> {

    fun findByNameContaining(name: String?, pageable: Pageable): Page<CustomerModel>

    fun findByStatus(status: CustomerStatus): List<CustomerModel>

    override fun findAll(pageable: Pageable): Page<CustomerModel>

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): CustomerModel?
}