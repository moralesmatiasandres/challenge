package com.example.challenge.network.responses

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
