package br.com.dev.applogin.view.tasks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.dev.applogin.R
import br.com.dev.applogin.databinding.ActivityTasklistBinding
import br.com.dev.applogin.localData.TaskDatabase
import br.com.dev.applogin.model.dto.Task
import br.com.dev.applogin.model.repository.RepositoryLocal
import br.com.dev.applogin.view.createTask.ActivityFormTask
import br.com.dev.applogin.view.detailProfile.ProfileDetailActivity
import br.com.dev.applogin.view.detailTask.ActivityDetailTask
import java.util.Collections

class TaskActivity: AppCompatActivity(), TaskListener {


    private  var adapter: TaskAdapter? = null


    private lateinit var binding: ActivityTasklistBinding
    private lateinit var viewModel: TaskViewModel


    private val profileEdit: String? by lazy {intent.extras?.getString(TaskActivity.IS_PROFILE)}



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tasklist)


        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TaskViewModel(
                    repositoryLocal = RepositoryLocal(TaskDatabase.getDatabaseInstance(this@TaskActivity))
                ) as T
            }
        })[TaskViewModel::class.java]


        binding.iconToolbar.setOnClickListener {
            ProfileDetailActivity.startProfile(this, profileEdit)
        }


        viewModel.getTask()
        configureObservables()
        openFormTask()


        val helper = androidx.recyclerview.widget.ItemTouchHelper(
            ItemTouchHelper(androidx.recyclerview.widget.ItemTouchHelper.UP
                    or androidx.recyclerview.widget.ItemTouchHelper.DOWN,
                androidx.recyclerview.widget.ItemTouchHelper.LEFT
            )
        )

        helper.attachToRecyclerView(binding.recyclerTasks)
    }

    fun configureObservables(){
        viewModel.isLoading.observe(this){isLoading->
            binding.loadingTask.isVisible = isLoading
        }
        viewModel.taskLocal.observe(this){tasks->
            tasks?.let {
                configureRecyclerView(tasks.toMutableList())
            }
            if (tasks == null){
                viewModel.taskRemoteError.observe(this) {
                    Toast.makeText(this, "Nenhuma tarefa, crie uma! ->", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun openFormTask(){
        binding.createtask.setOnClickListener {
            ActivityFormTask.startActivityForm(this, profileEdit)
        }
    }


    private fun configureRecyclerView(taskList: MutableList<Task>) {
        adapter = TaskAdapter(this, taskList, this)
        // O adapter recebe um listener que no caso foi implementado pela Acitvity, por isso o uso do this
        binding.recyclerTasks.adapter = adapter
        binding.recyclerTasks.layoutManager = LinearLayoutManager(this)
    }


    override fun onCheckboxClicked(task: Task, isChecked: Boolean) {
        task.isChecked = isChecked
        viewModel.deleteTask(task)
        adapter?.updateTask()
    }


    override fun onEditTaskClicked(taskId: Int) {
        ActivityDetailTask.startActivity(this, taskId, profileEdit)
    }



    inner class ItemTouchHelper(dragDirs: Int, swipeDirs: Int) : androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback(
        dragDirs, swipeDirs
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {

            val from = viewHolder.adapterPosition
            val to = target.adapterPosition

            Collections.swap(adapter?.tasks, from, to)
            adapter?.notifyItemMoved(from, to)


            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            adapter?.tasks?.removeAt(viewHolder.adapterPosition)
//            adapter?.notifyItemRemoved(viewHolder.adapterPosition)
            val position = viewHolder.adapterPosition
            val task = adapter?.tasks?.get(position)
            task?.let {
                viewModel.deleteTask(it) // Remove a tarefa do ViewModel
            }

        }
    }



    companion object {
        const val IS_PROFILE = "isProfile"
        fun startActivityTask(context: Context, idProfile: String? = "") {
            val intent = Intent(context, TaskActivity::class.java)
            intent.putExtra(IS_PROFILE, idProfile)
            context.startActivity(intent)
        }
    }


    private var isOnMainScreen = false

    override fun onBackPressed() {
        if (isOnMainScreen) {
            // Permita que o botão "voltar" funcione normalmente, chamando o super.onBackPressed()
            super.onBackPressed()
        } else {
            // Bloqueie o botão "voltar" ou faça qualquer ação desejada quando o usuário não estiver na tela principal
            // Por exemplo, exiba uma mensagem informando que o usuário não pode voltar neste momento
        }
    }
}