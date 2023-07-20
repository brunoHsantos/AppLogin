package br.com.dev.applogin.view.tasks

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.dev.applogin.R
import br.com.dev.applogin.databinding.ItemTaskListBinding
import br.com.dev.applogin.model.dto.Task
import java.text.SimpleDateFormat
import java.util.*


class TaskAdapter(
    val context: Context,
    val tasks: MutableList<Task>,
    val listener: TaskListener

) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding: ItemTaskListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_task_list,
            parent, false
        )
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    fun updateTask() {
        notifyDataSetChanged()
    }

    fun removeTask(position: Int) {
        tasks.removeAt(position)
        notifyItemRemoved(position)
    }

    fun removeTaskWithAnimation(position: Int) {
        tasks.removeAt(position)
        notifyItemRemoved(position)
    }



    inner class TaskViewHolder(val binding: ItemTaskListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.apply {
                taskWoner = task.owner
                val formateDate = formatDate(task.dateLimit)
                viewDate = formateDate

                background = if (task.isChecked) ContextCompat.getColor(context,
                    R.color.teal_150
                ) else ContextCompat.getColor(
                    context,
                    R.color.white
                )

                binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                    listener.onCheckboxClicked(task, isChecked)
                }

                binding.taskInformation.setOnClickListener {
                    listener.onEditTaskClicked(task.id)
                }
            }

        }

        private fun formatDate(date: Long): String {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = date
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
            return dateFormat.format(calendar.time)
        }
    }
}