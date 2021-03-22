package com.example.sportsnews.ui.screens.newshome

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
import com.example.sportsnews.R
import com.example.sportsnews.ui.screens.newshome.BottomNavItems.*

sealed class BottomNavItems(
    val route: String,
    val icon: Int
) {
    object HomeItem : BottomNavItems("Home", R.drawable.ic_round_home_24)
    object AccountItem : BottomNavItems("Account", R.drawable.ic_round_account_circle_24)
    object FavoritesItem : BottomNavItems("Favorites", R.drawable.ic_baseline_bookmark_border_24)
}

private val bottomNavItems = listOf(HomeItem, AccountItem, FavoritesItem)

@ExperimentalAnimationApi
@Composable
fun SportsNewsBottomNavigation(
    navController: NavController
) {
    Surface(
        shape = RoundedCornerShape(topStartPercent = 50, topEndPercent = 50),
        elevation = 8.dp,
        color = MaterialTheme.colors.secondary
    ) {
        BottomNavigation(
            elevation = 0.dp,
            backgroundColor = Color.Transparent,
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth()
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            val backstackRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
            val fallbackRoute = kotlin.runCatching {
                navController.graph.findNode(navController.graph.startDestination)!!.arguments[KEY_ROUTE]!!.defaultValue
            }.getOrNull()

            val currentRoute = backstackRoute ?: fallbackRoute

            bottomNavItems.forEach { item ->
                val selected = currentRoute == item.route

                BottomNavigationItem(
                    icon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = null,
                                tint = if (selected) MaterialTheme.colors.primary else Color.Gray
                            )
                            AnimatedVisibility(visible = selected) {
                                Text(
                                    text = item.route,
                                    style = MaterialTheme.typography.subtitle2.copy(
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 12.sp
                                    ),
                                    color = MaterialTheme.colors.primary,
                                    modifier = Modifier.padding(start = 6.dp)
                                )
                            }
                        }
                    },
                    selected = selected,
                    onClick = {
                        if (currentRoute != item.route) {
                            Log.e("Nikola", "SportsNewsBottomNavigation: $selected")
                            navController.navigate(item.route) {
                                popUpTo = navController.graph.startDestination
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        }
    }
}