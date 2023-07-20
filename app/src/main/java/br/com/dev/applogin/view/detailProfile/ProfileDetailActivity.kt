package br.com.dev.applogin.view.detailProfile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.dev.applogin.R
import br.com.dev.applogin.databinding.ActivityProfileBinding
import br.com.dev.applogin.localData.SharedPreferencesManager
import br.com.dev.applogin.localData.TaskDatabase
import br.com.dev.applogin.model.dto.UserProfile
import br.com.dev.applogin.model.repository.RepositoryLocal
import br.com.dev.applogin.model.repository.RepositoryRemote
import br.com.dev.applogin.remoteData.ApiService
import br.com.dev.applogin.remoteData.IApi
import br.com.dev.applogin.view.login.LoginActivity
import br.com.dev.applogin.view.tasks.TaskActivity
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class ProfileDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: DetailViewModel

    private val REQUEST_IMAGE_GALLERY = 101

    private val profileId: String? by lazy {intent.extras?.getString(ID_LOGIN)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DetailViewModel(
                    repositoryRemote = RepositoryRemote(ApiService.getService().create(IApi::class.java)),
                    repositoryLocal = RepositoryLocal(TaskDatabase.getDatabaseInstance(this@ProfileDetailActivity))
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
        backTask()

        updatePhoto()
    }



    private fun configureProfile(){

        viewModel.isLoading.observe(this) { isLoading->
            binding.loadingProfile.isVisible = isLoading
        }

        viewModel.profileDetail.observe(this){profileDetail->
            binding.apply {
                nameProfile = profileDetail.nome
                sexProfile = profileDetail.sexo
                emailProfile = profileDetail.email
                passwordProfile = profileDetail.senha
            }
        }

        viewModel.profileError.observe(this) {
            Toast.makeText(this, "Erro ao buscar perfil", Toast.LENGTH_LONG).show()
        }
    }

    private fun backTask(){
        binding.btnArrowBack.setOnClickListener {
            TaskActivity.startActivityTask(this, profileId)
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

    private fun updatePhoto(){
        binding.addPhotoProfile.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // Permissão já concedida, abra a galeria
                openGallery()
            } else {
                // Solicite permissão em tempo de execução
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_IMAGE_GALLERY)
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_IMAGE_GALLERY) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida, abra a galeria
                openGallery()
            } else {
                Toast.makeText(this, "Acesso Negado!", Toast.LENGTH_SHORT).show()
                // Permissão negada, lide com isso de acordo com o seu aplicativo
                // Por exemplo, exiba uma mensagem informando que a permissão é necessária para acessar a galeria
            }
        }
    }


    private fun openGallery() {
        //Aqui, criamos um Intent para abrir a galeria de imagens externas do dispositivo.
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY)//Esta linha inicia a atividade da galeria usando o Intent criado anteriormente. O código de solicitação REQUEST_IMAGE_GALLERY é usado para identificar a resposta da galeria mais tarde.
    }

    //Esta função é chamada quando a atividade da galeria é finalizada e retorna o resultado.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Verificamos se o código de solicitação é o mesmo que REQUEST_IMAGE_GALLERY e se o resultado é bem-sucedido.
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data // Url da imagem, caso exista
            imageUri?.let {
                try {
                    val inputStream = contentResolver.openInputStream(it)//Le os dados da imagem
                    val imageBytes = inputStream?.let { getBytes(it) }//Le os bytes da imagem

                    // Criar uma cópia do UserProfile
                    val updatedUserProfile = UserProfile(profilePhoto = imageBytes)

                    // Atualizar a foto do perfil do usuário no ViewModel
                    viewModel.profilePhoto.observe(this){userProfile->
                        userProfile.profilePhoto = imageBytes
                    }
                    viewModel.updatePhoto(updatedUserProfile)

                             // Carregue a nova foto do perfil no ImageView usando Glide
                             Glide.with(this@ProfileDetailActivity)
                                 .load(updatedUserProfile.profilePhoto)
                                 .into(binding.profile) // Aqui o Glide carrega a foto no ImageView "profile"

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getBytes(inputStream: InputStream): ByteArray {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len: Int
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }


    companion object {
        const val ID_LOGIN = "isEnter"
        fun startProfile(context: Context, profileId: String?)  {
            val intent = Intent(context, ProfileDetailActivity::class.java)
            intent.putExtra(ID_LOGIN, profileId)
            context.startActivity(intent)
        }
    }


    private var isOnMainScreen = false
    override fun onBackPressed() {
        if (isOnMainScreen) {
            // Permita que o botão "voltar" funcione normalmente, chamando o super.onBackPressed()
            super.onBackPressed()
        } else {
            TaskActivity.startActivityTask(this, profileId)
            // Bloqueie o botão "voltar" ou faça qualquer ação desejada quando o usuário não estiver na tela principal
            // Por exemplo, exiba uma mensagem informando que o usuário não pode voltar neste momento
        }
    }
}