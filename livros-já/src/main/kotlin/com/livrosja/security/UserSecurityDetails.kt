package com.livrosja.security

import com.livrosja.enums.CustomerStatus
import com.livrosja.model.CustomerModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class UserSecurityDetails(val customer: CustomerModel): UserDetails
{
    val id: Int = customer.id!!

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return customer.roles.map { SimpleGrantedAuthority(it.description) }.toMutableList()
    }

    override fun getPassword(): String {
        return customer.password
    }

    override fun getUsername(): String {
        return customer.id.toString()
    }

    override fun isAccountNonExpired(): Boolean {
        return true //sem tratamento ainda
    }

    override fun isAccountNonLocked(): Boolean {
        return true //sem tratamento ainda
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true //sem tratamento ainda
    }

    override fun isEnabled(): Boolean {
       return customer.status == CustomerStatus.ATIVO
    }

}