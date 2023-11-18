package com.example.projeto.activity.classes


class Usuario {

    private var cpf: String? = null
    private var cpfCnpj: String? = null
    private var senha: String? = null
    private var nome: String? = null
    private var nascimento: String? = null
    private var plano: String? = null
    private var vencimento: String? = null
    private var cidade: String? = null
    private var rua: String? = null
    private var numero: String? = null
    private var bairro: String? = null
    private var estado: String? = null
    var cod: String? = null
    var mensagem: String = ""
    private var login: String? = null

    fun getLogin(): String? = login

    fun setLogin(login: String) {
        this.login = login
    }

    fun getCpf(): String? = cpf
    fun setCpf(cpf: String) {
        this.cpf = cpf
    }

    fun getSenha(): String? = senha
    fun setSenha(senha: String) {
        this.senha = senha
    }

    fun getNome(): String? = nome
    fun setNome(nome: String) {
        this.nome = nome
    }

    fun getNascimento(): String? = nascimento
    fun setNascimento(nasc: String) {
        nascimento = nasc
    }

    fun getPlano(): String? = plano
    fun setPlano(plano: String) {
        this.plano = plano
    }

    fun getVencimento(): String? = vencimento
    fun setVencimento(venc: String) {
        this.vencimento = venc
    }

    fun getCidade(): String? = cidade

    fun setCidade(cidade: String) {
        this.cidade = cidade
    }

    fun getRua(): String? = rua
    fun setRua(rua: String) {
        this.rua = rua
    }

    fun getNumero(): String? = numero
    fun setNumero(num: String) {
        numero = num
    }

    fun getBairro(): String? = bairro
    fun setBairro(bairro: String) {
        this.bairro = bairro
    }

    fun getEstado(): String? = estado
    fun setEstado(estado: String) {
        this.estado = estado
    }


}