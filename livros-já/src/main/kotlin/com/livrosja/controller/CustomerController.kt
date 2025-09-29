package com.livrosja.controller

import com.livrosja.controller.request.PostCustomerRequest
import com.livrosja.controller.request.PutCustomerRequest
import com.livrosja.controller.response.CostumerResponse
import com.livrosja.extesion.toCustomerModel
import com.livrosja.extesion.toCustomerResponse
import com.livrosja.repository.CustomerRepository
import com.livrosja.service.CustomersService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


// fazer paginação no costumer na próx aula
@RestController
@RequestMapping("customers")
class CustomerController(
    private val customersService: CustomersService,
) {
    @PostMapping // Cria um usuário
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid customer: PostCustomerRequest) {
        return customersService.create(customer.toCustomerModel())
    }

    @GetMapping // retorna todos os usuários ou retorna um/vários pelas suas letras
    fun getAll(@RequestParam name: String?, @PageableDefault(page = 0, size = 10) pageable: Pageable): Page<CostumerResponse> {
       return customersService.getAll(name, pageable).map{ it.toCustomerResponse() }
    }

    @GetMapping("/{id}") // retorna um usuário pelo seu id
    fun getById(@PathVariable id: Int): CostumerResponse{
        return customersService.getById(id).toCustomerResponse()
    }

    @GetMapping("/actives") // retorna somente os usuários ativos
    fun findActives(): List<CostumerResponse>{
        return customersService.findActives().map{ it.toCustomerResponse() }
    }

    @PutMapping("/{id}") // atualiza um usuário pelo seu id
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody customer: PutCustomerRequest){
        val customerSaved = customersService.getById(id)
        return customersService.update(customer.toCustomerModel(customerSaved))
    }

    @DeleteMapping("/{id}") // deleta um usuário pelo seu id
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int){
        return customersService.delete(id)
    }
}


