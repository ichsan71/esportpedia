package com.marqumil.esportpedia.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("https://api.opendota.com/api/heroStats")
    suspend fun getHeroes(): List<HeroResponseItem>
}