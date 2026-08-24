package dev.gustavo.consultapaises.network

import dev.gustavo.consultapaises.CountrySearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CountryApiService {

    @GET("countries/v5/name")
    suspend fun searchByName(@Query("q") query: String): CountrySearchResponse
}
