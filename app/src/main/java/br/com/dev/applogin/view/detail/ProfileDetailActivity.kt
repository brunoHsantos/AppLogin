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
import br.com.dev.applogin.localData.SharedPreferencesManager
import br.com.dev.applogin.model.dto.IdProfile
import br.com.dev.applogin.model.dto.ProfileDetail
import br.com.dev.applogin.model.repository.RepositoryRemote
import br.com.dev.applogin.remoteData.ApiService
import br.com.dev.applogin.remoteData.IApi
import br.com.dev.applogin.view.login.LoginActivity

class ProfileDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: DetailViewModel

    private val profileId: String? by lazy {intent.extras?.getString(ID_LOGIN)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DetailViewModel(
                    repositoryRemote = RepositoryRemote(ApiService.getService().create(IApi::class.java)
                    )
                ) as T
            }

        })[DetailViewModel::class.java]



        profileId?.let {id->
            viewModel.profileById(id)
        }?:run {
            finish()
        }


        val int = SharedPreferencesManager(this)
        int.saveUserId(profileId)

        configureProfile()
        logout()
    }






    private fun configureProfile(){

        viewModel.isLoading.observe(this){
            binding.isLoading = it
        }


        viewModel.profileDetail.observe(this){profileDetail->
            binding.apply {
                nameProfile = profileDetail.nome
                ageProfile = profileDetail.idade
                binding.sexProfile = profileDetail.sexo
            }

        }

        viewModel.profileError.observe(this) {
            Toast.makeText(this, "Erro ao buscar tarefa", Toast.LENGTH_LONG).show()

        }
    }



    private fun logout(){
        binding.btnLogout.setOnClickListener{
            LoginActivity.startActivity(this)
            finish()
            val int = SharedPreferencesManager(this)
            int.clearUserId()
        }
    }




    companion object {
        const val ID_LOGIN = "isEnter"
        fun startProfile(context: Context, profileId: String?)  {
            val intent = Intent(context, ProfileDetailActivity::class.java)
            intent.putExtra(ID_LOGIN, profileId)
            context.startActivity(intent)
        }
    }
}