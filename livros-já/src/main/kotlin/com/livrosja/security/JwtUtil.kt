package com.livrosja.security

import com.livrosja.enums.Errors
import com.livrosja.exception.AuthenticationException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil(
    private val secretKey: SecretKey
) {
    fun generateToken(id: Int): String {
        val expiration = Date(System.currentTimeMillis() + 1000 * 60 * 60) // 1 hora


        // como ele vai construir o JWT
        return Jwts.builder()
            .subject(id.toString())
            .expiration( expiration)
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()

    }

    // valida token, não nulo e não expirado
    fun isValidToken(token: String): Boolean {
        val claims = getClains(token)
        if(claims.subject == null || claims.expiration == null || Date().after(claims.expiration)) {
            return false
        }
        return true
    }

    // é da minha aplicação (assinatura bate)?
    private fun getClains(token: String): Claims {
        try{
            return Jwts.parser()
                .verifyWith(secretKey) // verifica secret key
                .build()
                .parseSignedClaims(token).payload // pega payload
        } catch (ex: Exception) {
            throw AuthenticationException(errorCode = Errors.LJ1401.code, message = Errors.LJ1401.message)
        }
    }


    fun getSubject(token: String): String {
        return getClains(token).subject // pega o subject da função getClains, pois ela tem o payload
    }

}


