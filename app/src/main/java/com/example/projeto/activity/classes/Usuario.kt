import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Usuario (
    var cpf: String? = null,
    var cpfCnpj: String? = null,
    var senha: String? = null,
    var nome: String? = null,
    var nascimento: String? = null,
    var plano: String? = null,
    var vencimento: String? = null,
    var cidade: String? = null,
    var rua: String? = null,
    var numero: String? = null,
    var bairro: String? = null,
    var estado: String? = null,
    var cod: String? = null,
    var mensagem: String = "",
    var login: String? = null
) : Parcelable