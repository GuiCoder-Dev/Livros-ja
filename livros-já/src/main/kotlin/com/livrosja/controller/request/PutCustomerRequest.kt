package com.livrosja.controller.request



data class PutCustomerRequest(
    var name: String?,
    var email: String?,
)

// Put não tem validação