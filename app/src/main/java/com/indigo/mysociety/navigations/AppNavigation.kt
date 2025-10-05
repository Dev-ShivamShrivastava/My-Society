package com.indigo.mysociety.navigations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.indigo.mysociety.presentation.home.HomeScreen
import com.indigo.mysociety.presentation.settings.SettingsScreen
import com.indigo.mysociety.presentation.signIn.SignIn
import com.indigo.mysociety.presentation.signUp.SignUp
import com.indigo.mysociety.presentation.splash.Splash
import com.indigo.mysociety.presentation.tickets.TicketsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()


    val bottomBarScreens = listOf(
        ScreensRoutes.Home.name,
        ScreensRoutes.Tickets.name,
        ScreensRoutes.Settings.name
    ) // only show for these

    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry.value?.destination?.route

    Column(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = ScreensRoutes.Splash.name,
            modifier = Modifier.weight(1f)
        ) {
            composable(ScreensRoutes.Splash.name) {
                Splash { route ->
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
                HomeScreen()
            }
            composable(ScreensRoutes.Tickets.name) {
                TicketsScreen()
            }
            composable(ScreensRoutes.Settings.name) {
                SettingsScreen(onLogoutClick={
                    navController.navigate(ScreensRoutes.SignIn.name) {
                        popUpTo(ScreensRoutes.Home.name) { inclusive = true } // clears backstack
                    }
                },onChangePasswordClick= {},onPrivacyPolicyClick= {},onFAQClick= {})
            }

        }
        AnimatedVisibility(
            visible = currentDestination in bottomBarScreens,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(durationMillis = 300)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(durationMillis = 300)
            )
        ) {
            BottomNavigationBar(navController = navController)
        }
    }


}