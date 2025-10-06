package com.livrosja.security


import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import javax.crypto.SecretKey

@Configuration
class SecretKey {

    @Bean
    fun jwtSecretKey(@Value($$"${jwt.secret}") secret: String): SecretKey {
        return Keys.hmacShaKeyFor(secret.toByteArray())
    }

}

//object JwtKeys {
//    @Value($$"${jwt.secret}")
//    private val secret: String? = null
//    val secretKey: SecretKey = Keys.hmacShaKeyFor(secret!!.toByteArray())
//}
//
//= com.livrosja.security.SecretKey.JwtKeys.secretKey