package com.livrosja.model

import com.livrosja.enums.CustomerStatus
import com.livrosja.enums.Roles
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn

@Entity(name = "customer")
data class CustomerModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "name")
    var name: String,

    @Column(name = "email")
    var email: String,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: CustomerStatus,

    @Column(name = "password")
    var password: String,

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @CollectionTable("customer_roles", joinColumns = [JoinColumn(name = "customer_id")])
    @ElementCollection(targetClass = Roles::class, fetch = FetchType.EAGER)
    var roles: Set<Roles> = setOf()
)
