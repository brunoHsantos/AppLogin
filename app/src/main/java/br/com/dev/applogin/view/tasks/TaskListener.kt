package br.com.dev.applogin.view.tasks

import br.com.dev.applogin.model.dto.Task

interface TaskListener {


    fun onCheckboxClicked(task: Task, isChecked: Boolean) {}
    fun onEditTaskClicked(taskId: Int) {}

}