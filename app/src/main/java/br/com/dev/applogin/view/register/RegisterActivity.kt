package br.com.dev.applogin.view.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.dev.applogin.R
import br.com.dev.applogin.databinding.ActivityRegisterBinding
import br.com.dev.applogin.model.dto.Usuario
import br.com.dev.applogin.model.repository.RepositoryRemote
import br.com.dev.applogin.remoteData.ApiService
import br.com.dev.applogin.remoteData.IApi
import br.com.dev.applogin.view.login.LoginActivity

class RegisterActivity(): AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel


    private val isNewLogin: Boolean by lazy { intent.extras?.getBoolean(NEW_LOGIN) ?: false }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RegisterViewModel(
                    repositoryRemote = RepositoryRemote(ApiService.getService().create(IApi::class.java))
                ) as T
            }
        })[RegisterViewModel::class.java]


        if(isNewLogin){
            configureClick()
            configureError()
        }
    }
    private fun configureError(){
        viewModel.requiredField.observe(this){
            binding.error = it
        }

        viewModel.createProfileResponse.observe(this){response->
            if (response){
                LoginActivity.startActivity(this)
                Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Erro ao criar conta", Toast.LENGTH_SHORT).show()
            }
            clearField()
        }
   }


    private fun configureClick() {

        binding.btnCreateCount.setOnClickListener {
            val nameR = binding.etNameRegister.text.toString()
            val ageR = binding.etAgeRegister.text.toString()
            val sexR = binding.etSexRegister.text.toString()
            val emailR = binding.etEmailRegister.text.toString()
            val passwordR = binding.etPassowrdRegister.text.toString()

            viewModel.createProfile(nameR, ageR.toIntOrNull(), sexR, emailR, passwordR)

             }
        }

    private fun clearField() {
        binding.etNameRegister.text?.clear()
        binding.etAgeRegister.text?.clear()
        binding.etSexRegister.text?.clear()
        binding.etEmailRegister.text?.clear()
        binding.etPassowrdRegister.text?.clear()

    }


    companion object {
            const val NEW_LOGIN = "isCreate"
            fun startActivityRegister(context: Context, create: Boolean = false) {
                val intent = Intent(context, RegisterActivity::class.java)
                intent.putExtra(NEW_LOGIN, create)
                context.startActivity(intent)
            }
     }
}
