package com.livrosja.service


import com.livrosja.enums.BookStatus
import com.livrosja.enums.CustomerStatus
import com.livrosja.enums.Errors
import com.livrosja.exception.BadCreateException
import com.livrosja.exception.NotFoundException
import com.livrosja.model.BookModel
import com.livrosja.model.CustomerModel
import com.livrosja.repository.BookRepository
import com.livrosja.repository.CustomerRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val customerRepository: CustomerRepository,
) {

    //POST
    fun create(book: BookModel) {

        val customer = book.customer!!.status

        if(customer == CustomerStatus.ATIVO) {
            bookRepository.save(book)
        } else {
            throw BadCreateException(Errors.LJ1003.message, Errors.LJ1003.code)
        }
    }

    //GET
    fun findAll(pageable: Pageable): Page<BookModel> {
        return bookRepository.findAll(pageable)
    }

    //GET(BookStatus)
    fun findActives(pageable: Pageable): Page<BookModel> {
        return bookRepository.findByStatus(BookStatus.ATIVO, pageable)
    }

    //GET(ID)
    fun findById(id: Int): BookModel {
        return bookRepository.findById(id).orElseThrow{NotFoundException(Errors.LJ1001.message.format(id),Errors.LJ1001.code) }
    }

    //GET(ID)
    fun findCustomerId(customerId: Int, pageable: Pageable): Page<BookModel>{
        val booksForCustomer = bookRepository.findByCustomerId(customerId, pageable = pageable)

        if(!customerRepository.existsById(customerId)) {
            throw NotFoundException(Errors.LJ1101.message.format(customerId), Errors.LJ1101.code)
        }

        return booksForCustomer
    }

    //PUT
    fun update(book: BookModel) {
        bookRepository.save(book)
    }

    //DELETE(ID)
    fun deleteStatusById(id: Int){
        val book = findById(id)
        book.status = BookStatus.CANCELADO
        update(book)
    }

    //DELETE (ATRAVÉS DO CUSTOMER)
    fun deleteByCustomer(customer: CustomerModel) {
        val books = bookRepository.findByCustomer(customer)
        for(book in books){
            book.status = BookStatus.DELETADO
        }
        bookRepository.saveAll(books)
    }

    fun findAllByIds(bookIds: Set<Int>): List<BookModel> {
        return bookRepository.findAllById(bookIds).toList()
    }

    fun purchase(books: MutableList<BookModel>) {
        books.map{
            it.status = BookStatus.VENDIDO
        }
        bookRepository.saveAll(books)
    }

}