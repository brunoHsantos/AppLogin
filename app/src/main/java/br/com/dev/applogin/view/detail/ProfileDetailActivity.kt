package br.com.dev.applogin.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.dev.applogin.R
import br.com.dev.applogin.databinding.ActivityProfileBinding
import br.com.dev.applogin.model.repository.RepositoryRemote
import br.com.dev.applogin.remoteData.ApiService
import br.com.dev.applogin.remoteData.IApi
import br.com.dev.applogin.view.login.LoginActivity

class ProfileDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: DetailViewModel


    private val profileId: Int? by lazy { intent.extras?.getInt(IS_LOGIN) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DetailViewModel(
                    repositoryRemote = RepositoryRemote(
                        ApiService.getService().create(IApi::class.java)
                    )
                ) as T
            }
        })[DetailViewModel::class.java]

        profileId?.let {
            viewModel.profileById(it)
        }?: run{
            finish()
        }
        configureProfile()
        logout()
    }



    private fun configureProfile(){
        viewModel.profileDetail.observe(this){
            binding.apply {
                nameProfile = it.name
                ageProfile = it.age
                sexProfile = it.sex
            }
        }
        viewModel.profileError.observe(this){
            Toast.makeText(this, "Erro ao Logar!", Toast.LENGTH_LONG).show()
        }
    }

    private fun logout(){
        binding.btnLogout.setOnClickListener{
            LoginActivity.startActivity(this)
            finish()
        }
    }


    companion object {
        const val IS_LOGIN = "isEnter"
        fun startProfile(context: Context, profileId: Int?) {
            val intent = Intent(context, ProfileDetailActivity::class.java)
            intent.putExtra(IS_LOGIN, profileId)
            context.startActivity(intent)
        }
    }
}