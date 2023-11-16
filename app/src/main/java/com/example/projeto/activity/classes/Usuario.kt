package com.example.projeto.activity.classes


class Usuario {

    private var cpf: String = ""
    private var cpfCnpj: String = ""
    private lateinit var senha: String
    private lateinit var nome: String
    private lateinit var nascimento: String
    private lateinit var plano: String
    private lateinit var vencimento: String
    private lateinit var cidade: String
    private  lateinit var rua: String
    private lateinit var numero: String
    private lateinit var bairro: String
    private lateinit var estado: String
    lateinit var cod: String
    var mensagem: String = ""
    var login: String = ""



    fun getCpf(): String = cpf
    fun setCpf(cpf: String) {
        this.cpf = cpf
    }

    fun getSenha(): String = senha
    fun setSenha(senha: String) {
        this.senha = senha
    }

    fun getNome(): String = nome
    fun setNome(nome: String) {
        this.nome = nome
    }

    fun getNascimento(): String = nascimento
    fun setNascimento(nasc: String) {
        nascimento = nasc
    }

    fun getPlano(): String = plano
    fun setPlano(plano: String) {
        this.plano = plano
    }

    fun getVencimento(): String = vencimento
    fun setVencimento(venc: String) {
        this.vencimento = venc
    }

    fun getCidade(): String = cidade

    fun setCidade(cidade: String) {
        this.cidade = cidade
    }

    fun getRua(): String = rua
    fun setRua(rua: String) {
        this.rua = rua
    }

    fun getNumero(): String = numero
    fun setNumero(num: String) {
        numero = num
    }

    fun getBairro(): String = bairro
    fun setBairro(bairro: String) {
        this.bairro = bairro
    }

    fun getEstado(): String = estado
    fun setEstado(estado: String) {
        this.estado = estado
    }


}