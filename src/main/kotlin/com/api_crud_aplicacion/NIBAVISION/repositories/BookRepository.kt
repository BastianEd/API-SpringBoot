package com.api_crud_aplicacion.NIBAVISION.repositories
import com.api_crud_aplicacion.NIBAVISION.models.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long> {
    // JpaRepository ya nos da:
        // findAll() (Leer todos)
        // findById(id) (Leer uno)
        // save(book) (Crear / Actualizar)
        // deleteById(id) (Borrar)
}