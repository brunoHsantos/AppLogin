package br.com.dev.applogin.view.login


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import br.com.dev.applogin.R
import br.com.dev.applogin.databinding.ActivityLoginBinding
import br.com.dev.applogin.localData.SharedPreferencesManager
import br.com.dev.applogin.model.dto.IdProfile
import br.com.dev.applogin.model.dto.LoginResponse
import br.com.dev.applogin.model.repository.RepositoryRemote
import br.com.dev.applogin.remoteData.ApiService
import br.com.dev.applogin.remoteData.IApi
import br.com.dev.applogin.view.detail.ProfileDetailActivity
import br.com.dev.applogin.view.register.RegisterActivity

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)


        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(
                    repositoryRemote = RepositoryRemote(ApiService.getService().create(IApi::class.java))
                )as T
            }

        })[LoginViewModel::class.java]

        var idLogin = SharedPreferencesManager(this)

        val userId = idLogin.getUserId()

        if (userId != null){
            ProfileDetailActivity.startProfile(this, userId)
        }else{
            enter()
            createProfileClick()
        }

        configureObservables()
    }


    private fun enter() {

        binding.login.setOnClickListener {
            val email = binding.etEmailLogin.text.toString()
            val senha = binding.etPassowrdLogin.text.toString()

            val login = LoginResponse(email, senha)

            viewModel.getProfileRemote(login)

            }
        }


    private fun configureObservables(){

        viewModel.requiredField.observe(this) {
            binding.error = it
        }
        viewModel.profileLoginRemoteId.observe(this){IdProfile->
            IdProfile.let {
                 ProfileDetailActivity.startProfile(this, IdProfile?.idPessoa )
                 }
            }

        viewModel.loginError.observe(this){
            Toast.makeText(this, "Email/senha inválidos!", Toast.LENGTH_SHORT).show()
            clearLogin()
        }
    }


    private fun clearLogin(){
            binding.etEmailLogin.text?.clear()
            binding.etPassowrdLogin.text?.clear()
            binding.etEmailLogin.requestFocus()

    }


    private fun createProfileClick() {
        binding.textRegister.setOnClickListener {
            RegisterActivity.startActivityRegister(this, true)
        }
    }

    companion object {
        const val IS_LOGIN = "isCreate"
        fun startActivity(context: Context, idUser: Int? = null) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.putExtra(IS_LOGIN, idUser)
            context.startActivity(intent)
        }
    }
}