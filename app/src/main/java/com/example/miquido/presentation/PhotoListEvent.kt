package com.example.miquido.presentation


sealed interface PhotoListEvent {
   data object Error : PhotoListEvent
}