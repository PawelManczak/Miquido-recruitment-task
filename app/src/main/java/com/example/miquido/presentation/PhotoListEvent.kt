package com.example.miquido.presentation

import com.example.miquido.domain.util.NetworkError


sealed interface PhotoListEvent {
   data class Error(val error: NetworkError): PhotoListEvent
}