package com.example.newmonage.api

data class MonageData(
    val tanggal: String,
    val label: String,
    val amount: Double,
    val description: String? = null,
    val done_at: String? = null,
)