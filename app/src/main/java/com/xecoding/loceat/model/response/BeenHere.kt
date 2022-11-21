package com.xecoding.loceat.model.response

data class BeenHere(
    val count: Int,
    val lastCheckinExpiredAt: Int,
    val marked: Boolean,
    val unconfirmedCount: Int
)