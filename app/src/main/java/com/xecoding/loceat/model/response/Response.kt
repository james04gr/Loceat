package com.xecoding.loceat.model.response

data class Response(
    val confident: Boolean,
    val venues: List<Venue>
)