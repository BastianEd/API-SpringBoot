package com.api_crud_aplicacion.NIBAVISION.controllers

import com.api_crud_aplicacion.NIBAVISION.models.Book
import com.api_crud_aplicacion.NIBAVISION.repositories.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/books") // URL base: http://localhost:8080/api/books
class BookController {

    @Autowired
    private lateinit var bookRepository: BookRepository

    // --- READ (Leer Todos) ---
    // GET /api/books
    @GetMapping
    fun getAllBooks(): List<Book> {
        // Tu app Android llamará a esto en la Home Screen
        return bookRepository.findAll()
    }

    // --- READ (Leer Uno) ---
    // GET /api/books/1
    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Long): ResponseEntity<Book> {
        return bookRepository.findById(id)
            .map { ResponseEntity.ok(it) } // Devuelve 200 OK con el libro
            .orElse(ResponseEntity.notFound().build()) // Devuelve 404 Not Found
    }

    // --- CREATE (Crear) ---
    // POST /api/books
    @PostMapping
    fun createBook(@RequestBody book: Book): Book {
        return bookRepository.save(book)
    }

    // --- UPDATE (Actualizar) ---
    // PUT /api/books/1
    @PutMapping("/{id}")
    fun updateBook(@PathVariable id: Long, @RequestBody bookDetails: Book): ResponseEntity<Book> {
        return bookRepository.findById(id)
            .map { existingBook ->
                existingBook.title = bookDetails.title
                existingBook.author = bookDetails.author
                existingBook.price = bookDetails.price
                existingBook.priceValue = bookDetails.priceValue
                existingBook.coverImageUrl = bookDetails.coverImageUrl
                existingBook.isNew = bookDetails.isNew

                ResponseEntity.ok(bookRepository.save(existingBook))
            }
            .orElse(ResponseEntity.notFound().build())
    }

    // --- DELETE (Borrar) ---
    // DELETE /api/books/1
    @DeleteMapping("/{id}")
    fun deleteBook(@PathVariable id: Long): ResponseEntity<Any> {
        // 1. Buscamos el libro por su ID
        val bookOptional = bookRepository.findById(id)

        // 2. Usamos un 'if' para verificar si el Optional contiene un valor
        return if (bookOptional.isPresent) {
            // 3. Si existe, lo borramos
            bookRepository.delete(bookOptional.get())
            // 4. Devolvemos 200 OK
            ResponseEntity.ok().build()
        } else {
            // 5. Si no existe, devolvemos 404 Not Found
            ResponseEntity.notFound().build()
        }
    }
}