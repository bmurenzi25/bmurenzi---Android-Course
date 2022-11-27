package model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ToDoViewModel: ViewModel() {
    var todoList = mutableStateListOf<ToDo>()

    fun add(newToDo: ToDo){
        todoList.add(newToDo)
    }

    fun delete(toDo: ToDo){
        todoList.remove(toDo)
    }

    fun markCompleted(toDo: ToDo)
    {
        toDo.completed = !toDo.completed
    }
}