package com.livrosja.security.annotationPermition

import com.livrosja.security.UserSecurityDetails
import com.livrosja.service.BookService
import org.springframework.security.core.Authentication

import org.springframework.stereotype.Component

@Component("bookPermissionEvaluator")
class BookPermissionEvaluator(
    private val bookService: BookService,
) {

    fun hasAccess(authentication: Authentication, bookId: Int): Boolean {
        val book = bookService.findById(bookId)
        val principal = authentication.principal as UserSecurityDetails
        return book.customer!!.id == principal.id
    }

}
