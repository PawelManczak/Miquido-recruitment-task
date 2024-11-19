package com.example.miquido.data.mapper

import com.example.miquido.data.model.PhotoDto
import com.example.miquido.domain.Photo

fun PhotoDto.toDomain(size: Int): Photo {
    return Photo(
        id = id,
        author = author,
        width = width,
        height = height,
        imageUrl = getSquareImageUrl(size)
    )
}

fun PhotoDto.toDomain(): Photo {
    return Photo(
        id = id,
        author = author,
        width = width,
        height = height,
        imageUrl = download_url
    )
}