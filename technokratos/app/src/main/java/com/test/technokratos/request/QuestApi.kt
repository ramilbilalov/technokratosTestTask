package com.test.technokratos.request

import com.test.technokratos.request.questList.QuestListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface QuestApi {
    @GET("api/")
    @Headers("Content-Type: application/json")
    fun getQuestList(): Single<QuestListResponse>
}