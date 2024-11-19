package com.example.miquido.domain

data class Photo(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val imageUrl: String
){}