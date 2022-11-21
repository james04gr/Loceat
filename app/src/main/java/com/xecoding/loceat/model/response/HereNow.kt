package com.xecoding.loceat.model.response

data class HereNow(
    val count: Int,
    val groups: List<Group>,
    val summary: String
)