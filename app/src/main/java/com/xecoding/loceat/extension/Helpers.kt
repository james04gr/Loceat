package com.xecoding.loceat.extension

import com.google.android.gms.maps.model.LatLng
import com.xecoding.loceat.model.entities.CategoryGroup
import com.xecoding.loceat.model.response.Venue

fun LatLng.stringConvert(): String = "${this.latitude},${this.longitude}"

fun List<Venue>.toCategoryGroups(): List<CategoryGroup> {
    return this.filter {
        it.categories.isNotEmpty()
    }.groupBy {
        it.categories.first().id
    }.map {
        CategoryGroup(it.value.first().categories.first().name, it.value)
    }
}