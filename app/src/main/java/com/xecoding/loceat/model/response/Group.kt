package com.xecoding.loceat.model.response

data class Group(
    val count: Int,
    val items: List<Any>,
    val name: String,
    val type: String
)