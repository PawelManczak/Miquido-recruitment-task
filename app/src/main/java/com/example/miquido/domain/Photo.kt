package com.example.miquido.domain

data class Photo(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val imageUrl: String
){
    fun toFullSize(): Photo {
        val fullSizeUrl = "https://picsum.photos/id/$id/$width/$height"
        return this.copy(imageUrl = fullSizeUrl)
    }
}