package com.livrosja.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.Positive
import org.jetbrains.annotations.NotNull

data class PostPurchaseRequest(


    @field: NotNull
    @JsonAlias("book_ids")
    val bookIds: Set<Int>,
)

// @field: NotNull
//    @field: Positive
//    @JsonAlias("customer_id")
//    val customerId: Int,

