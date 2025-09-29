package com.livrosja.controller.request

import com.livrosja.validation.EmailAvailable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class PostCustomerRequest(

    @field: NotEmpty(message = "Nome deve ser preenchido")
    var name: String,

    @field: Email(message = "E-mail deve ser válido")
    @EmailAvailable
    var email: String,

    @field: NotEmpty(message = "Senha deve ser preenchida")
    var password: String
)

