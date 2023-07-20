package br.com.dev.applogin.view.detailTask

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.dev.applogin.R
import br.com.dev.applogin.databinding.ActivityTaskDetailBinding
import br.com.dev.applogin.localData.TaskDatabase
import br.com.dev.applogin.model.repository.RepositoryLocal
import br.com.dev.applogin.view.tasks.TaskActivity
import java.text.SimpleDateFormat
import java.util.*

class ActivityDetailTask: AppCompatActivity() {

    private lateinit var binding: ActivityTaskDetailBinding
    private lateinit var viewModel: TaskDetailViewModel

    private val taskId: Int? by lazy { intent.extras?.getInt(TASK_ID_ARG) }

    private val profileEdit: String? by lazy {intent.extras?.getString(ActivityDetailTask.TASK_ID_PROF)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_detail)

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TaskDetailViewModel(
                    repositoryLocal = RepositoryLocal(TaskDatabase.getDatabaseInstance(this@ActivityDetailTask))
                ) as T
            }
        })[TaskDetailViewModel::class.java]

        taskId?.let { id ->
            viewModel.getTaskById(id)
        } ?: run {
            finish()
        }

        configureObservable()

    }


    private fun configureObservable() {

        viewModel.taskObservable.observe(this) { task ->
            binding.apply {
                owner = task.owner
                title = task.title
                description = task.description
                val formatDte = formatDate(task.dateLimit)
                date = formatDte
            }
        }
        binding.btnArrowBack.setOnClickListener {
            TaskActivity.startActivityTask(this, profileEdit)
        }

        binding.finishTask.setOnClickListener {
            viewModel.taskObservable.observe(this){task->
                viewModel.deleteTask(task)
                TaskActivity.startActivityTask(this, profileEdit)
                Toast.makeText(this, "Tarefa finalizada!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun formatDate(date: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        return dateFormat.format(calendar.time)
    }

    companion object {
        const val TASK_ID_ARG = "taskIdArgs"

        const val TASK_ID_PROF = "taskIdProfile"
        fun startActivity(context: Context, taskId: Int = 0, profile: String?) {
            val intent = Intent(context, ActivityDetailTask::class.java)
            intent.putExtra(TASK_ID_ARG, taskId)
            intent.putExtra(TASK_ID_PROF, profile)
            context.startActivity(intent)
        }
    }


    private var isOnMainScreen = true

    override fun onBackPressed() {
        if (isOnMainScreen) {
            // Permita que o botão "voltar" funcione normalmente, chamando o super.onBackPressed()
            super.onBackPressed()
            TaskActivity.startActivityTask(this, profileEdit)
        } else {
            // Bloqueie o botão "voltar" ou faça qualquer ação desejada quando o usuário não estiver na tela principal
            // Por exemplo, exiba uma mensagem informando que o usuário não pode voltar neste momento
        }
    }
}