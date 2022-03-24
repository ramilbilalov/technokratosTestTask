package com.test.technokratos.request.questList

import com.google.gson.annotations.SerializedName

data class QuestListResponse(
    @SerializedName("results")
    val results: List<QuestListItem>
) {
    data class QuestListItem(
        @SerializedName("gender")
        val gender: String,

        @SerializedName("name")
        val name: NameListItem,

        @SerializedName("location")
        val location: LocationListItem,

        @SerializedName("dob")
        val dob: DobListItem,

        @SerializedName("phone")
        val phone: String,

        @SerializedName("picture")
        val picture: PictureListItem,
    )
}