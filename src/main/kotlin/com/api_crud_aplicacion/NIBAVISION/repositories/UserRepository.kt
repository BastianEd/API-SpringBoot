package com.api_crud_aplicacion.NIBAVISION.repositories

import com.api_crud_aplicacion.NIBAVISION.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Long> {
    // Spring Data JPA entiende este nombre de función y crea la consulta SQL por nosotros
    // "SELECT * FROM users WHERE email = ?"
    fun findByEmail(email: String): Optional<User>
}