package com.livrosja.security

import com.livrosja.enums.Errors
import com.livrosja.exception.AuthenticationException
import com.livrosja.service.UserDetailCustomService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

class AuthorizationFilter(
    authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil,
    private val userDetails: UserDetailCustomService
): BasicAuthenticationFilter(authenticationManager){

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorization = request.getHeader("Authorization")

        if(authorization != null && authorization.startsWith("Bearer ")){
            val auth = getAuthentication(authorization.split(" ")[1])
            SecurityContextHolder.getContext().authentication = auth // linha principal (final)
        }
        chain.doFilter(request, response)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        if(!jwtUtil.isValidToken(token)){
            throw AuthenticationException(errorCode = Errors.LJ1401.code, message = Errors.LJ1401.message)
        }
        val subject = jwtUtil.getSubject(token)
        val customer = userDetails.loadUserByUsername(subject) // interligação entre subject e id (neste caso)
        return UsernamePasswordAuthenticationToken(customer, null, customer.authorities) // guarda os dados do usuário autenticado
    }

}






