package com.example.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lab4.navigation.NavItem
import com.example.lab4.ui.compose.ContactsScreen
import com.example.lab4.ui.compose.KeypadScreen
import com.example.lab4.ui.compose.RecentsScreen
import com.example.lab4.ui.theme.Lab4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab4Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreenView()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
@Composable
fun NavigationGraph(navController: NavHostController) {

    NavHost(navController, startDestination = NavItem.Keypad.path) {
        composable(NavItem.Keypad.path) {
            KeypadScreen()
        }
        composable(NavItem.Recent.path) {
            RecentsScreen()
        }
        composable(NavItem.Contacts.path) {
            ContactsScreen()
        }
    }
}

@Composable
fun BottomNav(navController: NavController) {
    val links = listOf(
        NavItem.Keypad,
        NavItem.Recent,
        NavItem.Contacts
    )
    BottomNavigation(
        contentColor = Color.White,
        backgroundColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        links.forEach { link ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = link.icon), contentDescription = link.title) },
                label = { Text(text = link.title) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.6f),
                selected = currentRoute == link.path,
                onClick = {
                    navController.navigate(link.path) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun MainScreenView(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNav(navController = navController) }
    ) {
        NavigationGraph(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Lab4Theme {
        Greeting("Android")
    }
}