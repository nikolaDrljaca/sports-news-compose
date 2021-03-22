package com.example.sportsnews

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.navigation.compose.rememberNavController
import com.example.sportsnews.network.ConnectionLiveData
import com.example.sportsnews.ui.screens.newshome.NewsHome
import com.example.sportsnews.ui.screens.newshome.SportsNewsBottomNavigation
import com.example.sportsnews.ui.theme.SportsNewsTheme

class MainActivity : AppCompatActivity() {
    lateinit var connectionLiveData: ConnectionLiveData

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectionLiveData = ConnectionLiveData(this)

        setContent {
            SportsNewsTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        SportsNewsBottomNavigation(navController = navController)
                    }
                ) {
                    AppNavigator(navController = navController)
                }
            }
        }
    }
}