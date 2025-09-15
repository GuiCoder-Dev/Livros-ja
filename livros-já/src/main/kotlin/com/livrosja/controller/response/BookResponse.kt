package com.livrosja.controller.response

import com.livrosja.enums.BookStatus
import com.livrosja.model.CustomerModel
import java.math.BigDecimal

class BookResponse(
    var id: Int? = null,
    var name: String,
    var price: BigDecimal,
    var customer: CustomerModel? = null,
    var status: BookStatus? = null
)



