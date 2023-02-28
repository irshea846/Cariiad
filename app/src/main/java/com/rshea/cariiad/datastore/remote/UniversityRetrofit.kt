package com.rshea.cariiad.datastore.remote

import retrofit2.http.GET

interface UniversityRetrofit {

    @GET("/search?country=United+Kingdom")
    suspend fun get(): List<UniversityNetworkEntity>
}