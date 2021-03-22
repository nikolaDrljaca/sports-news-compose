package com.example.sportsnews

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import com.example.sportsnews.network.NetworkArticle
import com.example.sportsnews.ui.screens.newsdetail.NewsDetail
import com.example.sportsnews.ui.screens.newshome.BottomNavItems.*
import com.example.sportsnews.ui.screens.newshome.NewsHome

@Composable
fun AppNavigator(
    navController: NavHostController
) {
   NavHost(
       navController = navController,
       startDestination = HomeItem.route
   ) {
       composable(HomeItem.route) {
           NewsHome(
               onArticleClicked = { articleUrl ->
                   navController.navigate("newsDetail/$articleUrl")
               }
           )
       }
       composable(AccountItem.route) {
           //empty on purpose
       }
       composable(FavoritesItem.route) {
           //empty on purpose
       }
       composable(
           route = "newsDetail/{articleUrl}",
           arguments = listOf(
               navArgument("articleUrl") { type = NavType.StringType }
           )
       ) {
           NewsDetail(onBackPressed = { navController.navigateUp() })
       }
   }
}