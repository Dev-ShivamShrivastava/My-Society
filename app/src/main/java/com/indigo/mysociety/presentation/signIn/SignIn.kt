package com.indigo.mysociety.presentation.signIn

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.domain.model.request.CreateUserRequest
import com.domain.model.request.LoginRequest
import com.indigo.mysociety.R
import com.indigo.mysociety.presentation.signUp.SignUpVM
import com.indigo.mysociety.utils.MyFontFamily
import com.indigo.mysociety.utils.isValidEmail
import com.indigo.mysociety.utils.showToast

@Composable
fun SignIn(
    onLogin: () -> Unit,
    onSignup: () -> Unit,
    onForgotPassword: () -> Unit,
    onGuestLogin: () -> Unit
) {
    val viewModel: SignInVM = hiltViewModel()

    // Dynamic fields (state)
    var emailOrPhone by remember { mutableStateOf("shivam@gmail.com") }
    var password by remember { mutableStateOf("shivam123") }

    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is LoginUiEvent.ShowToast -> context.showToast(event.message)
                LoginUiEvent.NavigateHome -> {
                    onLogin()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE0CC)) // peach background
    ) {
        Image(
            painter = painterResource(id = R.drawable.sign_in_bg), // your image from drawable
            contentDescription = null,
            contentScale = ContentScale.Crop, // scale to fill the screen
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.50f)
        )
        // White rounded card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f) // card takes bottom portion
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .background(Color.White)
                .align(Alignment.BottomCenter)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(Modifier.height(16.dp))

            Text(
                "Login",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Sign in to continue.",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(Modifier.height(24.dp))

            // Email / Phone
            Text(
                "EMAIL / PHONE NUMBER",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = MyFontFamily,
                color = Color.Gray, modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(Modifier.height(5.dp))
            TextField(
                value = emailOrPhone,
                onValueChange = { emailOrPhone = it },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp)),
                placeholder = {
                    Text(
                        "Enter Here",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = MyFontFamily
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledTextColor = Color.Black,
                    cursorColor = Color.Black,

                    focusedContainerColor = Color(0xFFE0E0E0),
                    unfocusedContainerColor = Color(0xFFE0E0E0),
                    disabledContainerColor = Color.LightGray,

                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
            )
            Spacer(Modifier.height(16.dp))

            // Password
            Text(
                "Password",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = MyFontFamily,
                color = Color.Gray, modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(Modifier.height(5.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp)),
                placeholder = {
                    Text(
                        "Enter Here",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = MyFontFamily
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledTextColor = Color.Black,
                    cursorColor = Color.Black,

                    focusedContainerColor = Color(0xFFE0E0E0),
                    unfocusedContainerColor = Color(0xFFE0E0E0),
                    disabledContainerColor = Color.LightGray,

                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
            )

            Spacer(Modifier.height(24.dp))

            // Login Button
            Button(
                onClick = {
                    viewModel.onLoginClicked(emailOrPhone, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Log in", color = Color.White, fontSize = 18.sp)
            }

            Spacer(Modifier.height(16.dp))

            // Forgot / Signup
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Forgot Password?",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        Toast.makeText(
                            context,
                            "Please check registered email. Forgot password link sent.",
                            Toast.LENGTH_SHORT
                        ).show()
                        onForgotPassword()
                    }
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    "Signup !",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onSignup() }
                )
            }

            Spacer(Modifier.height(16.dp))

            // Guest Login Button
            Button(
                onClick = { onGuestLogin() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Login as Guest", color = Color.White, fontSize = 16.sp)
            }
            Spacer(Modifier.height(20.dp))

        }

        // Loader overlay
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x88000000)), // semi-transparent background
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    SignIn({}, {}, {}, {})
}