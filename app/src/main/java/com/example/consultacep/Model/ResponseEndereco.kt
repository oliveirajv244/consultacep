package com.example.consultacep.Model

data class ResponseEndereco(
    val cep: String?,
    val logradouro: String?,
    val complemento: String?,
    val bairro: String?,
    val localidade: String?,
    val uf: String?,
    val estado: String?,
    val regiao: String?,
    val ddd: String?,
    val erro: Boolean?
)
