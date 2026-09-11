package com.example.consultacep.Api

import com.example.consultacep.Model.ResponseEndereco
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepService {

    @GET("ws/{cep}/json/")
    fun consultarCep(
        @Path("cep") cep: String
    ): Call<ResponseEndereco>
}
