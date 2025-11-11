package com.api_crud_aplicacion.NIBAVISION.models

import jakarta.persistence.*

@Entity
@Table(name = "books")
class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0, // Usamos Long como estándar en Spring/JPA

    var title: String? = null,
    var author: String? = null,
    var price: String? = null,
    var priceValue: Double? = null,
    var coverImageUrl: String? = null,
    var isNew: Boolean = false
)