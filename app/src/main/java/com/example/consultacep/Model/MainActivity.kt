package com.example.consultacep.Model

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.consultacep.Api.ViaCepClient
import com.example.consultacep.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var edtCep: EditText
    private lateinit var btnConsultar: Button

    private lateinit var txtCep: TextView
    private lateinit var txtRua: TextView
    private lateinit var txtBairro: TextView
    private lateinit var txtCidade: TextView
    private lateinit var txtEstado: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtCep = findViewById(R.id.edtCep)
        btnConsultar = findViewById(R.id.btnConsultar)

        txtCep = findViewById(R.id.txtCep)
        txtRua = findViewById(R.id.txtRua)
        txtBairro = findViewById(R.id.txtBairro)
        txtCidade = findViewById(R.id.txtCidade)
        txtEstado = findViewById(R.id.txtEstado)


        btnConsultar.setOnClickListener {
            consultarCep()
        }
    }

    private fun consultarCep() {

        val cep = edtCep.text.toString()
            .replace("-", "")
            .replace(" ", "")

        if (cep.length != 8 || !cep.all { it.isDigit() }) {
            Toast.makeText(
                this,
                "Digite um CEP válido com 8 números.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        btnConsultar.isEnabled = false

        ViaCepClient.instance.consultarCep(cep)
            .enqueue(object : Callback<ResponseEndereco> {

                override fun onResponse(
                    call: Call<ResponseEndereco>,
                    response: Response<ResponseEndereco>
                ) {
                    btnConsultar.isEnabled = true

                    if (response.isSuccessful) {

                        val endereco = response.body()

                        if (endereco == null || endereco.erro == true) {
                            limparCampos()

                            Toast.makeText(
                                this@MainActivity,
                                "CEP não encontrado.",
                                Toast.LENGTH_SHORT
                            ).show()

                            return
                        }

                        txtCep.text = "CEP: ${endereco.cep ?: "-"}"
                        txtRua.text = "Rua: ${endereco.logradouro ?: "-"}"
                        txtBairro.text = "Bairro: ${endereco.bairro ?: "-"}"
                        txtCidade.text =
                            "Cidade: ${endereco.localidade ?: "-"}"
                        txtEstado.text =
                            "Estado: ${endereco.estado ?: "-"} (${endereco.uf ?: "-"})"
                        txtRua.text = "Logradouro: ${endereco.logradouro ?: "-"}"
                    } else {
                        limparCampos()

                        Toast.makeText(
                            this@MainActivity,
                            "Erro ao consultar o CEP.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<ResponseEndereco>,
                    t: Throwable
                ) {
                    btnConsultar.isEnabled = true
                    limparCampos()

                    Toast.makeText(
                        this@MainActivity,
                        "Erro de conexão: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun limparCampos() {
        txtCep.text = "CEP:"
        txtRua.text = "Logradouro:"
        txtBairro.text = "Bairro:"
        txtCidade.text = "Cidade:"
        txtEstado.text = "Estado:"
    }


}

