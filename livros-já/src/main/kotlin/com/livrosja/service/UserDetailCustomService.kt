package com.livrosja.service

import com.livrosja.enums.Errors
import com.livrosja.exception.AuthenticationException
import com.livrosja.repository.CustomerRepository
import com.livrosja.security.UserSecurityDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailCustomService(
    private val customerRepository: CustomerRepository
): UserDetailsService {

    override fun loadUserByUsername(id: String): UserDetails {
        val customer = customerRepository.findById(id.toInt()).orElseThrow{AuthenticationException(errorCode = Errors.LJ1401.code, message = Errors.LJ1401.message)}
        return UserSecurityDetails(customer)
    }
}


