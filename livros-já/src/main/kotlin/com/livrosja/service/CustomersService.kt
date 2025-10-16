package com.livrosja.service


import com.livrosja.enums.CustomerStatus
import com.livrosja.enums.Errors
import com.livrosja.enums.Roles
import com.livrosja.exception.NotFoundException
import com.livrosja.model.CustomerModel
import com.livrosja.repository.CustomerRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomersService(
    private val customerRepository: CustomerRepository,
    private val bookService: BookService,
    private val passwordEncoder: PasswordEncoder
) {

    //POST
    fun create(customer: CustomerModel) {
        val customerWithRole = customer.copy(
            roles = setOf(Roles.CUSTOMER),
            password = passwordEncoder.encode(customer.password)
        )

        customerRepository.save(customerWithRole)
    }

    //GET
    fun getAll(name: String?, pageable: Pageable?): Page<CustomerModel> {
        name?.let{
            return customerRepository.findByNameContaining(pageable = pageable!!, name = name)
        }
        return customerRepository.findAll(pageable = pageable!!)
    }

    //GET{ID}
    fun getById(id: Int): CustomerModel{
        return customerRepository.findById(id).orElseThrow{NotFoundException(Errors.LJ1101.message.format(id), Errors.LJ1101.code)}
    }

    //PUT
    fun update(customer: CustomerModel){
        if(!customerRepository.existsById(customer.id!!)){
            throw Exception("Customer not found")
        }
        customerRepository.save(customer)
    }

    //DELETE (id)
    fun delete(id: Int){
        val customer = getById(id)
        bookService.deleteByCustomer(customer)

        customer.status = CustomerStatus.INATIVO
        customerRepository.save(customer)
    }

    //GET (ATIVOS)
    fun findActives(pageable: Pageable): Page<CustomerModel>{
        return customerRepository.findByStatus(status = CustomerStatus.ATIVO, pageable = pageable)
    }

    fun emailAvailable(email: String): Boolean {
        return !customerRepository.existsByEmail(email)
    }

}