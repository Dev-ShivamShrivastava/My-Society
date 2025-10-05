package com.indigo.mysociety.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.indigo.mysociety.R
import com.indigo.mysociety.utils.showToast

@Composable
fun SettingsScreen(
    onLogoutClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onFAQClick: () -> Unit
) {
    val viewModel: SettingsVM = hiltViewModel()
    val userInfo = viewModel.userInfo  // Compose will recompose when userInfo changes

    var showLogoutDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color(0xFFF7F8FA))
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {

        // --- Header ---
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // --- Profile Section ---
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                Image(
                    painter = painterResource(id = R.drawable.ic_profile_placeholder),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFDADADA))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = userInfo?.name?.ifEmpty { "N/A" } ?: "N/A",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = userInfo?.email?.ifEmpty { "N/A" } ?: "N/A",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Text(
                        text = userInfo?.phoneNo.takeIf { it != null }?.let { "+91 $it" } ?: "N/A",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Settings Options ---
        SettingsItem(
            icon = Icons.Default.Lock,
            title = "Change Password",
            onClick = onChangePasswordClick
        )
        SettingsItem(
            icon = Icons.Default.Lock,
            title = "Privacy Policy",
            onClick = onPrivacyPolicyClick
        )
        SettingsItem(
            icon = Icons.Default.Star,
            title = "FAQ",
            onClick = onFAQClick
        )

        Spacer(modifier = Modifier.height(12.dp))

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // --- Logout Button ---
        SettingsItem(
            icon = Icons.Default.ExitToApp,
            title = "Logout",
            onClick = {
                showLogoutDialog = true
            },
            textColor = Color(0xFFD32F2F),
            iconTint = Color(0xFFD32F2F)
        )

        // --- Logout AlertDialog ---
        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.clearAllInfo()
                        showLogoutDialog = false
                        // ✅ Handle logout logic
                        context.showToast("Logged out successfully")
                        onLogoutClick()
                    }) {
                        Text("Logout", color = Color.Red, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text("Cancel", color = Color.Gray)
                    }
                },
                title = { Text("Logout") },
                text = { Text("Are you sure you want to logout?") }
            )
        }
    }
}


@Composable
fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    textColor: Color = Color.Black,
    iconTint: Color = Color.Black,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp, horizontal = 12.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = iconTint,
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }

    Spacer(modifier = Modifier.height(8.dp))
}