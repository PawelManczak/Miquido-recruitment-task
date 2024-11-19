package com.example.miquido.data.model

data class PhotoDto(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val download_url: String
) {
    fun getSquareImageUrl(size: Int): String {
        return "https://picsum.photos/id/$id/$size/$size"
    }
}
