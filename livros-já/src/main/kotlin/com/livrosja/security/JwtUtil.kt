package com.livrosja.security

import io.jsonwebtoken.Jwts
import java.util.Date


class JwtUtil {

    fun generateToken(id: String){
        Jwts.builder()
            .subject(id)
            .expiration(Date())
            //.signWith( )

            // assinatura
            // chave privada
            // ver outra alternativa para o signWith


    }

}