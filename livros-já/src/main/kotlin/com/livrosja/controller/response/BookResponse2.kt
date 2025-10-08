package com.livrosja.controller.response

import com.livrosja.enums.BookStatus
import com.livrosja.model.CustomerModel
import java.math.BigDecimal

class BookResponse2(
    var id: Int? = null,
    var name: String,
    var price: BigDecimal,
    var customer: CustomerResponse2? = null,
    var status: BookStatus? = null
)



