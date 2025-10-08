package com.livrosja.extesion

import com.livrosja.controller.request.PostBookRequest
import com.livrosja.controller.request.PostCustomerRequest
import com.livrosja.controller.request.PostPurchaseRequest
import com.livrosja.controller.request.PutBookRequest
import com.livrosja.controller.request.PutCustomerRequest
import com.livrosja.controller.response.BookResponse
import com.livrosja.controller.response.BookResponse2
import com.livrosja.controller.response.CostumerResponse
import com.livrosja.controller.response.CustomerResponse2
import com.livrosja.controller.response.PurchaseResponse
import com.livrosja.enums.BookStatus
import com.livrosja.enums.CustomerStatus
import com.livrosja.model.BookModel
import com.livrosja.model.CustomerModel
import com.livrosja.model.PurchaseModel
import kotlin.collections.MutableList


// Resquest To Model
fun PostCustomerRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(
        name = this.name,
        email = this.email,
        status = CustomerStatus.ATIVO,
        password = this.password,
    )
}

fun PutCustomerRequest.toCustomerModel(previousValue: CustomerModel): CustomerModel {
    return CustomerModel(
        id = previousValue.id,
        name = this.name ?: previousValue.name,
        email = this.email ?: previousValue.email,
        status = CustomerStatus.ATIVO,
        password = previousValue.password,
    )
}

fun PostBookRequest.toBookModel(customer: CustomerModel): BookModel {
    return BookModel(
        name = this.name,
        price = this.price,
        status = BookStatus.ATIVO,
        customer = customer,
    )
}

fun PutBookRequest.toBookModel(previousValue: BookModel): BookModel {
    return BookModel(
        id = previousValue.id,
        name = this.name ?: previousValue.name,
        price = this.price ?: previousValue.price,
        status = BookStatus.ATIVO,
        customer = previousValue.customer
    ) // mesmo com a verificação no BookModel, ainda é possível alterar o status de deletado ou cancelado para ativo, através do método PUT
}


// Model to Response

fun CustomerModel.toCustomerResponse(): CostumerResponse{
    return CostumerResponse(
        id = this.id,
        name = this.name,
        email = this.email,
        status = this.status
    )
}

fun BookModel.toBookResponse(): BookResponse {
    return BookResponse(
        id = this.id,
        name = this.name,
        price = this.price,
        customer = this.customer,
        status = this.status,
    )
}



fun CustomerModel.toCustomerResponse2(): CustomerResponse2 {
    return CustomerResponse2(
        id = this.id,
        name = this.name
    )
}

fun BookModel.toBookResponse2(): BookResponse2 {
    return BookResponse2(
        id = this.id,
        name = this.name,
        price = this.price,
        customer = this.customer?.toCustomerResponse2(),
        status = this.status,
    )
}


fun PurchaseModel.toPurchaseResponse(): PurchaseResponse {
    return PurchaseResponse(
        books = this.books.map { it.toBookResponse2() }.toMutableList()
    )
}




