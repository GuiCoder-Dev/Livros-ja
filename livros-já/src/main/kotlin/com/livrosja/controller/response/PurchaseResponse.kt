package com.livrosja.controller.response

import com.livrosja.model.BookModel
import com.livrosja.model.CustomerModel

data class PurchaseResponse(
    var books: MutableList<BookResponse2>,
)


