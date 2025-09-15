package com.livrosja.controller.response

import com.livrosja.enums.CustomerStatus

data class CostumerResponse(
    var id: Int? = null,
    var name: String,
    var email: String,
    var status: CustomerStatus
)




