package com.example.tpago.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/datos")
    fun obtenerDatos(): Call<List<Dato>>
}
