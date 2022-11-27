package com.example.lab3.compose

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab3.R
import com.example.lab3.ui.theme.Lab3Theme
import com.example.lab3.model.ToDo
import com.example.lab3.model.ToDoViewModel
import java.util.*
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ToDoScreen() {
    val viewModel: ToDoViewModel = viewModel()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        backgroundColor = MaterialTheme.colors.surface,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {showDialog = true}
            )
            {
                Icon(Icons.Filled.Add, contentDescription = "")
            }
        },
        content = {
            if (showDialog){
                addTodoDialog(context, dismissDialog = {showDialog = false}, {viewModel.add(it)})
            }

            LazyColumn(
                contentPadding = PaddingValues(
                    vertical = 8.dp,
                    horizontal = 8.dp
                )
            )
            {
                items(viewModel.todoList, key = {toDo -> toDo.id}) { toDo ->
                    ToDoItem(item = toDo, context,  { viewModel.markCompleted(toDo) }, { viewModel.delete(it) },)
                }
            }
        }
    )
}

@Composable
fun addTodoDialog(context: Context, dismissDialog:() -> Unit, addToDo: (ToDo) -> Unit){
    var toDoTitleField by remember {
        mutableStateOf("")
    }
    var toDoTimeField by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = { dismissDialog },
        title = {Text(text = stringResource(id = R.string.addToDo), style = MaterialTheme.typography.h6)},
        text = {
            Column(modifier = Modifier.padding(top=20.dp)) {
                OutlinedTextField(label = {Text(text = stringResource(id = R.string.title))}, value = toDoTitleField, onValueChange = {toDoTitleField = it})
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(label = {Text(text = stringResource(id = R.string.time))},value = toDoTimeField, onValueChange = {toDoTimeField = it})
            }
        },
        confirmButton = {
            Button(onClick = {
                if(toDoTitleField.isNotEmpty()) {
                    val toDoID = UUID.randomUUID().toString();
                    addToDo(ToDo(toDoID, toDoTitleField, toDoTimeField, false))
                    Toast.makeText(
                        context,
                        context.resources.getString(R.string.addedToDO),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                dismissDialog()
            })
            {
                Text(text = stringResource(id = R.string.add))
            }
        },dismissButton = {
            Button(onClick = {
                dismissDialog()
            }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    )
}

@Composable
fun deleteToDoDialog(context: Context, dismissDialog:() -> Unit, item: ToDo, deleteToDo: (ToDo) -> Unit){
    AlertDialog(
        onDismissRequest = { dismissDialog},
        title = {Text(text = stringResource(id = R.string.delete), style = MaterialTheme.typography.h6)},
        confirmButton = {
            Button(onClick = {
                deleteToDo(item)
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.deleteItem),
                    Toast.LENGTH_SHORT
                ).show()
                dismissDialog()
            })
            {
                Text(text = stringResource(id = R.string.yes))
            }
        },dismissButton = {
            Button(onClick = {
                dismissDialog()
            }) {
                Text(text = stringResource(id = R.string.no))
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToDoItem(
    item: ToDo,
    context: Context,
    markComplete: (ToDo) -> Unit,
    deleteToDo: (ToDo) -> Unit) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var isComplete by remember { mutableStateOf(false) }

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        border = BorderStroke(2.dp, color = MaterialTheme.colors.primaryVariant),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    Toast
                        .makeText(context, item.toDoTitle, Toast.LENGTH_SHORT)
                        .show()
                }
            )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = isComplete, onCheckedChange = {
                isComplete = it
                markComplete(item)
            })
            Spacer(modifier = Modifier.size(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.toDoTitle, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = context.resources.getString(R.string.time) + ": " + item.time, style = MaterialTheme.typography.body1)
            }
            IconButton(onClick = { showDeleteDialog = true }) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
            }
        }

    }
    if (showDeleteDialog){
        deleteToDoDialog(context, dismissDialog = { showDeleteDialog = false }, item, deleteToDo)
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Lab3Theme {
        ToDoScreen()
    }
}


