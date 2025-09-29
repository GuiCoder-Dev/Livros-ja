package com.livrosja.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.livrosja.controller.request.LoginRequest
import com.livrosja.enums.Errors
import com.livrosja.exception.AuthenticationException
import com.livrosja.repository.CustomerRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val customerRepository: CustomerRepository
): UsernamePasswordAuthenticationFilter(authenticationManager) {

    // O SPRING CRIA UM /LOGIN (ENDPOINT)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {

        try {
            val loginRequest = jacksonObjectMapper().readValue(request.inputStream, LoginRequest::class.java)
            val id = customerRepository.findByEmail(loginRequest.email)?.id
            val authenticationToken = UsernamePasswordAuthenticationToken(id, loginRequest.password)

            return authenticationManager.authenticate(authenticationToken)
        } catch (ex: Exception) {
            throw AuthenticationException(errorCode = Errors.LJ1401.code, message = Errors.LJ1401.message)
        }

    }

    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse,  chain: FilterChain, authResult: Authentication) {
       val id = (authResult.principal as UserSecurityDetails).id




        response.addHeader("Authorization", "1234567")
    }

}