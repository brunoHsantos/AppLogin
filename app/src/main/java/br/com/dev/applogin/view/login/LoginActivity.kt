package br.com.dev.applogin.view.login


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.dev.applogin.R
import br.com.dev.applogin.databinding.ActivityLoginBinding
import br.com.dev.applogin.localData.ProfileDataBase
import br.com.dev.applogin.model.dataClass.Profile
import br.com.dev.applogin.model.repository.RepositoryLocal
import br.com.dev.applogin.model.repository.RepositoryRemote
import br.com.dev.applogin.remoteData.ApiService
import br.com.dev.applogin.remoteData.IApi
import br.com.dev.applogin.view.detail.ProfileDetailActivity
import br.com.dev.applogin.view.register.Ilistener
import br.com.dev.applogin.view.register.RegisterActivity

class LoginActivity: AppCompatActivity(), Ilistener {


    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var profile: Profile



    private val profileId: Int? by lazy { intent.extras?.getInt(IS_CREATE_LOGIN) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)


        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(
                    repositoryLocal = RepositoryLocal(ProfileDataBase.getDatabaseInstance(this@LoginActivity)),
                    repositoryRemote = RepositoryRemote(ApiService.getService().create(IApi::class.java)
                    )

                )as T
            }

        })[LoginViewModel::class.java]


        if (profileId != null){
            viewModel.loginProfile(profileId)
            profileLogin(profileId)
        }else{
            enterLogin(profile)
        }
        createProfileClick()
    }


    private fun enterLogin(profileId: Profile){
        binding.login.setOnClickListener {
            binding.apply {
                if (email == profileId.email && password == profileId.password) {
                    profileLogin(profileId.id)
                }else{
                    viewModel.loginFailed.observe(this@LoginActivity){
                        binding.error = it
                    }
                }
            }
        }
    }

    private fun createProfileClick() {
        binding.textRegister.setOnClickListener {
            RegisterActivity.startActivityRegister(this, true)
        }
    }


    override fun profileLogin(profileId: Int?) {
        ProfileDetailActivity.startProfile(this, profileId)
    }


    companion object {
        const val IS_CREATE_LOGIN = "isCreate"
        fun startActivity(context: Context, isLogin: Int? = null) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.putExtra(IS_CREATE_LOGIN, isLogin)
            context.startActivity(intent)
        }
    }

}