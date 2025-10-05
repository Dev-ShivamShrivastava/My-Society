package com.indigo.mysociety.navigations

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.indigo.mysociety.utils.Dimens

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem(ScreensRoutes.Home.name, ScreensRoutes.Home.name, Icons.Default.Home),
        BottomNavItem(ScreensRoutes.Tickets.name, ScreensRoutes.Tickets.name, Icons.Default.Menu),
        BottomNavItem(ScreensRoutes.Settings.name, ScreensRoutes.Settings.name, Icons.Default.Settings)
    )

    NavigationBar(
        tonalElevation = Dimens._0dp,
        containerColor = Color.Transparent,
        modifier = Modifier.fillMaxWidth().wrapContentHeight().clip(RoundedCornerShape(topStart = Dimens._20dp, topEnd = Dimens._20dp))
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                label = { Text(item.label) },
                icon = { Icon(item.icon, contentDescription = item.label) }
            )
        }
    }
}

data class BottomNavItem(val label: String, val route: String, val icon: ImageVector)