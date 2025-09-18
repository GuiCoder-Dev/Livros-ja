package com.livrosja.repository

import com.livrosja.enums.BookStatus
import com.livrosja.model.BookModel
import com.livrosja.model.CustomerModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface BookRepository: JpaRepository<BookModel, Int> {

    fun findByStatus(status: BookStatus, pageable: Pageable): Page<BookModel>

    fun findByCustomer(costumer: CustomerModel): List<BookModel>

    fun findByCustomerId(customerId: Int, pageable: Pageable): Page<BookModel>
}