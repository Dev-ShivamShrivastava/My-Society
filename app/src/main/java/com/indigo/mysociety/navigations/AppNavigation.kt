package com.indigo.mysociety.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.indigo.mysociety.presentation.home.HomeScreen
import com.indigo.mysociety.presentation.signIn.SignIn
import com.indigo.mysociety.presentation.signUp.SignUp
import com.indigo.mysociety.presentation.splash.Splash

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreensRoutes.Splash.name) {

        composable(ScreensRoutes.Splash.name) {
            Splash {route->
                when (route) {
                    ScreensRoutes.SignIn.name -> {
                        navController.navigate(ScreensRoutes.SignIn.name) {
                            popUpTo(ScreensRoutes.Splash.name) {
                                inclusive = true
                            } // remove splash from backstack
                        }
                    }
                    ScreensRoutes.Home.name -> {
                        navController.navigate(ScreensRoutes.Home.name) {
                            popUpTo(ScreensRoutes.Splash.name) {
                                inclusive = true
                            } // remove splash from backstack
                        }
                    }
                }


            }
        }

        composable(ScreensRoutes.SignUp.name) {
            SignUp({
                navController.navigateUp()
            })
        }

        composable(ScreensRoutes.SignIn.name) {
            SignIn({
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