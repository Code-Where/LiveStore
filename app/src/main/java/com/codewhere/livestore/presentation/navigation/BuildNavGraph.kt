package com.codewhere.livestore.presentation.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.codewhere.livestore.presentation.detailed_product.ProductScreen
import com.codewhere.livestore.presentation.home.HomeScreen
import com.codewhere.livestore.presentation.splash.SplashScreen

@Composable
fun BuildNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val startDestination = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) Routes.HomeScreen else Routes.SplashScreen
    NavHost(navController = navController, startDestination = startDestination){
        composable<Routes.SplashScreen>{
            SplashScreen(modifier = modifier) {
                navController.navigate(Routes.HomeScreen)
            }
        }
        composable<Routes.HomeScreen>{
            HomeScreen(
                modifier = modifier,
                navController = navController
            )
        }
        composable<Routes.ProductScreen>{ backstack ->
            val productId = backstack.toRoute<Routes.ProductScreen>().productId
            ProductScreen(modifier = modifier, productId = productId, onBack = { navController.popBackStack() })
        }
    }
}