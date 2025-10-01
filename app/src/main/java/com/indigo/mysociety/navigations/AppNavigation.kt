package com.indigo.mysociety.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.indigo.mysociety.presentation.HomeScreen
import com.indigo.mysociety.presentation.SignIn
import com.indigo.mysociety.presentation.SignUp
import com.indigo.mysociety.presentation.Splash

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreensRoutes.Splash.name) {

        composable(ScreensRoutes.Splash.name) {
            Splash {
                navController.navigate(ScreensRoutes.SignIn.name) {
                    popUpTo(ScreensRoutes.Splash.name) {
                        inclusive = true
                    } // remove splash from backstack
                }
            }
        }

        composable(ScreensRoutes.SignUp.name) {
            SignUp({ str, str1, str2, str3, str4 ->
                navController.navigateUp()
            }, {
                navController.navigateUp()
            })
        }

        composable(ScreensRoutes.SignIn.name) {
            SignIn({ str, str1 ->
                navController.navigate(ScreensRoutes.Home.name) {
                    popUpTo(ScreensRoutes.SignIn.name) {
                        inclusive = true
                    } // remove splash from backstack
                }
            }, onSignup = {
                navController.navigate(ScreensRoutes.SignUp.name)
            }, onForgotPassword = {}, onGuestLogin = {
                navController.navigate(ScreensRoutes.Home.name) {
                    popUpTo(ScreensRoutes.SignIn.name) {
                        inclusive = true
                    } // remove splash from backstack
                }
            })
        }
        composable(ScreensRoutes.Home.name) {
            HomeScreen { str, str1, str2, str3 ->

            }
        }
    }
}