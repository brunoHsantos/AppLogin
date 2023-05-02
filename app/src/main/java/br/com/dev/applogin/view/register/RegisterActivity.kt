package br.com.dev.applogin.view.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.dev.applogin.R
import br.com.dev.applogin.databinding.ActivityRegisterBinding
import br.com.dev.applogin.localData.ProfileDataBase
import br.com.dev.applogin.model.dataClass.Profile
import br.com.dev.applogin.model.repository.RepositoryLocal
import br.com.dev.applogin.view.login.LoginActivity

class RegisterActivity(val listener: Ilistener): AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel


    private val isNewLogin: Boolean by lazy { intent.extras?.getBoolean(NEW_LOGIN) ?: false }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RegisterViewModel(
                    RepositoryLocal(ProfileDataBase.getDatabaseInstance(this@RegisterActivity))
                ) as T
            }
        })[RegisterViewModel::class.java]

       lateinit var profile: Profile

        if (isNewLogin) {
            configureClick(profile)
            configureObserables()
        }
    }

    private fun configureObserables() {
        viewModel.requiredField.observe(this) {
            binding.error = it
        }
    }

    private fun configureClick(profile: Profile) {

        binding.btnCreateCount.setOnClickListener {

            val nameR = binding.nameRegister
            val ageR = binding.etAgeRegister.text.hashCode()
            val sexR = binding.sexRegister
            val emailR = binding.emailRegister
            val passwordR = binding.passwordRegister

            viewModel.createProfile(nameR, ageR, sexR, emailR, passwordR)
            LoginActivity.startActivity(this, profile.id)
            listener.profileLogin(profile.id)
        }
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