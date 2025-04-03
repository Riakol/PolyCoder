package com.example.wikianimals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wikianimals.ui.theme.WikiCode
import com.example.wikianimals.ui_components.InfoScreen
import com.example.wikianimals.ui_components.MainScreen
import com.example.wikianimals.utils.ListItem
import com.example.wikianimals.utils.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var item: ListItem? = null

            WikiCode {
                    NavHost(
                        navController = navController, startDestination = Routes.MAIN_SCREEN) {
                        composable(Routes.MAIN_SCREEN) {
                            MainScreen(context = this@MainActivity) {listItem ->
                                item = listItem
                                navController.navigate(Routes.INFO_SCREEN)
                            }
                        }

                        composable(Routes.INFO_SCREEN) {
                            InfoScreen(item = item!!)
                        }
                    }
                }
            }
        }
    }



