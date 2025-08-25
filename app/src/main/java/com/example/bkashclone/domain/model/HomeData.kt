package com.example.bkashclone.domain.model

data class HomeData(
    val balance: String,
    val services: List<Service>,
    val offers: List<Offer>,
    val bundles: List<Bundle>
)
