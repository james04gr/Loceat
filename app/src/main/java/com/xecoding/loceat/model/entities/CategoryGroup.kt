package com.xecoding.loceat.model.entities

import com.xecoding.loceat.model.response.Venue

data class CategoryGroup(var categoryName: String, var venues: List<Venue>)