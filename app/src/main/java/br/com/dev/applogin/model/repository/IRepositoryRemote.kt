package br.com.dev.applogin.model.repository

import br.com.dev.applogin.model.dto.*

//Instância da nossa classe `Repository Remote`, que recebe funções onde utilizaremos como quisermos

interface IRepositoryRemote {

    /* Funções que esperam receber variáveis QUE ESPERAM receber outros tipos de valor.
        Quando a solicitamos essas funções, temos que respeitar as condicoes dos parametros,
        passando o valor que cada variável espera receber.
     */

    fun createProfile(profile: Usuario, isSuccess: () -> Unit, isError: () -> Unit)

    fun enterLogin(login: LoginResponse, isSuccess: (IdProfile?) -> Unit, isError: () -> Unit)

    fun getProfileById(isLoading: (Boolean) -> Unit, _id: String, isSuccess: (ProfileDetail?) -> Unit, isError: () -> Unit)


}