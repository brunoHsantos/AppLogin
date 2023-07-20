package br.com.dev.applogin.view.createTask

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.dev.applogin.R
import br.com.dev.applogin.databinding.ActivityTaskFormBinding
import br.com.dev.applogin.localData.SharedPreferencesManager
import br.com.dev.applogin.localData.TaskDatabase
import br.com.dev.applogin.model.repository.RepositoryLocal
import br.com.dev.applogin.view.tasks.TaskActivity

class ActivityFormTask: AppCompatActivity() {


    private lateinit var binding: ActivityTaskFormBinding
    private lateinit var viewModel: FormTaskViewModel

    private val createTask: String? by lazy { intent.extras?.getString(IS_CREATE_TASK) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_form)

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FormTaskViewModel(
                    RepositoryLocal(TaskDatabase.getDatabaseInstance(this@ActivityFormTask))
                ) as T
            }
        })[FormTaskViewModel::class.java]

            configureObservables()
            configureError()

    }


    fun configureError() {
        viewModel.requiredField.observe(this) {
            binding.error = it
        }
    }

    fun configureObservables(){

        binding.btnCreatTask.setOnClickListener {
            val ownerTask = binding.etTaskOwner.text.toString()
            val titleTask = binding.etTaskTitle.text.toString()
            val descriptionTask = binding.etTaskDescription.text.toString()
            val dateTask = binding.datelimit.date

            if (ownerTask != null && ownerTask.isNotEmpty()){
                viewModel.createTask(ownerTask, titleTask, descriptionTask, dateTask)
                Toast.makeText(this, "Tarefa Adicionada!", Toast.LENGTH_SHORT).show()
            }else{
                configureError()
            }
            clearField()
        }

        binding.btnBackTask.setOnClickListener {
            TaskActivity.startActivityTask(this, createTask)
        }
    }


    private fun clearField() {
        binding.etTaskOwner.text?.clear()
        binding.etTaskTitle.text?.clear()
        binding.etTaskDescription.text?.clear()
        binding.datelimit.date
        binding.etTaskOwner.requestFocus()
    }


    companion object {
        const val IS_CREATE_TASK = "isFromApiArgs"
        fun startActivityForm(context: Context, isProfile: String?) {
            val intent = Intent(context, ActivityFormTask::class.java)
            intent.putExtra(IS_CREATE_TASK, isProfile)
            context.startActivity(intent)
        }
    }

    private var isOnMainScreen = true

    override fun onBackPressed() {
        if (isOnMainScreen) {  // Permita que o botão "voltar" funcione normalmente, chamando o super.onBackPressed()

            var idLogin = SharedPreferencesManager(this)
            val userId = idLogin.getUserId()
            TaskActivity.startActivityTask(this, userId)
            //super.onBackPressed()
        } else {
            // Bloqueie o botão "voltar" ou faça qualquer ação desejada quando o usuário não estiver na tela principal
            // Por exemplo, exiba uma mensagem informando que o usuário não pode voltar neste momento
        }
    }
}
