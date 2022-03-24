package com.test.technokratos.request.questList

data class NameListItem(
    val title: String,
    val first: String,
    val last: String
)

data class LocationListItem(
    val street: StreetListItem,
    val city: String,
    val country: String,
    val coordinates: CoordinatesListItem
)

data class DobListItem(val date: String, val age: String)

data class StreetListItem(val number: String, val name: String)

data class CoordinatesListItem(val latitude: String, val longitude: String)

data class PictureListItem(
    val large: String,
    val medium: String,
    val thumbnail: String
)