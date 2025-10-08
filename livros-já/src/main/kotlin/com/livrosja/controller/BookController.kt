package com.livrosja.controller

import com.livrosja.controller.request.PostBookRequest
import com.livrosja.controller.request.PutBookRequest
import com.livrosja.controller.response.BookResponse
import com.livrosja.controller.response.BookResponse2
import com.livrosja.extesion.toBookModel
import com.livrosja.extesion.toBookResponse
import com.livrosja.extesion.toBookResponse2
import com.livrosja.security.UserSecurityDetails
import com.livrosja.service.BookService
import com.livrosja.service.CustomersService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("books")
class BookController(
    private val bookService: BookService,
    private val customerService: CustomersService

) {
    @PostMapping // cria livros
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid request: PostBookRequest, authentication: Authentication) {
        val principal = authentication.principal as UserSecurityDetails
        val customer = customerService.getById(principal.id)
        //val customer = customerService.getById(request.customerId)
        bookService.create(request.toBookModel(customer))
    }


    @GetMapping("/all") // retorna todos os livros já criados
    fun findAll(@PageableDefault(page = 0, size = 10) pageable: Pageable): Page<BookResponse2> {
        return bookService.findAll(pageable).map {it.toBookResponse2()}
    }

    @GetMapping("/actives") // retorna somente os livros ativos
    fun findActives(@PageableDefault(page = 0, size = 10) pageable: Pageable): Page<BookResponse2> {
        return bookService.findActives(pageable).map {it.toBookResponse2()}
    }

    @GetMapping("/{id}") // retorna um livro pelo id
    @PreAuthorize("hasRole('ROLE_ADMIN') || @bookPermissionEvaluator.hasAccess(authentication, #id)")
    fun findById (@PathVariable id: Int): BookResponse2{
        return bookService.findById(id).toBookResponse2()
    }

    @GetMapping("/bookspercostumers/{id}") // retornar todos os livros de um usuário pelo seu id (do usuário)
    @PreAuthorize("hasRole('ROLE_ADMIN') || #id == authentication.principal.id")
    fun findBooksPerCostumer(@PathVariable id: Int, @PageableDefault(page = 0, size = 10) pageable: Pageable): Page<BookResponse2> {
           return bookService.findCustomerId(id, pageable = pageable).map {it.toBookResponse2()}
    }

    @PutMapping("/{id}") // atualiza um livro pelo id
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN') || @bookPermissionEvaluator.hasAccess(authentication, #id)")
    fun update (@PathVariable id: Int, @RequestBody book: PutBookRequest){
        val bookSaved = bookService.findById(id)
        bookService.update(book.toBookModel(previousValue = bookSaved))
    }

    @DeleteMapping("/{id}") // deleta um livro pelo id
    @PreAuthorize("hasRole('ROLE_ADMIN') || @bookPermissionEvaluator.hasAccess(authentication, #id)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteStatusById (@PathVariable id: Int){
        return bookService.deleteStatusById(id)
    }

}
