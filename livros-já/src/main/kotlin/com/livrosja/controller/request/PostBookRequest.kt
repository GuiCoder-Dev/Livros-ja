package com.livrosja.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import com.livrosja.security.UserSecurityDetails
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import java.math.BigDecimal

data class PostBookRequest(

    @field:NotBlank(message = "Nome deve ser preenchido")
    var name: String,

    @field:NotNull(message = "Preço deve ser estabelecido")
    var price: BigDecimal,


)


// CÓDIGO
// @JsonAlias("customer_id")
//    var customerId: Int


// Anotação:
