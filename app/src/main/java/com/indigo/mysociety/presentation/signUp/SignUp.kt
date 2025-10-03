package com.indigo.mysociety.presentation.signUp

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.domain.model.request.CreateUserRequest
import com.indigo.mysociety.presentation.commonUI.LabeledInput
import com.indigo.mysociety.presentation.home.HomeVM
import com.indigo.mysociety.utils.CustomSnackBar
import com.indigo.mysociety.utils.Dimens
import com.indigo.mysociety.utils.MyFontFamily
import com.indigo.mysociety.utils.showToast
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun SignUp(
    onLoginClick: () -> Unit
) {
    val viewModel: SignUpVM = hiltViewModel()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }


    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.toastEvent.collect { message ->
            context.showToast(message)
        }
    }

    // Calendar setup
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // Restrict max date = today - 16 years
    val maxDate = Calendar.getInstance().apply {
        set(year - 16, month, day)
    }.timeInMillis

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val cal = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }
                val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                dob = formatter.format(cal.time)
            },
            year,
            month,
            day
        ).apply {
            datePicker.maxDate = maxDate
        }
    }


    // ⭐ React to API Response directly here
    LaunchedEffect(state.response) {
        state.response?.let { response ->
            if (response.status == "Success") {
                context.showToast(response.message ?: "Sign up successful")
                onLoginClick() // ⭐ Go back after success
            } else {
                context.showToast(response.message ?: "Sign up failed")
            }
        }
    }


    Box( modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(horizontal = Dimens._24dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(modifier = Modifier.height(24.dp))
            // Title
            Text(
                text = "Create new",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Account",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Already registered?
            Row {
                Text("Already Registered? ", color = Color.Gray)
                Text(
                    text = "Log in here.",
                    color = Color.Blue,
                    modifier = Modifier.clickable { onLoginClick() }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Name
            LabeledInput(label = "NAME", value = name, onValueChange = { name = it })

            // Email
            LabeledInput(label = "EMAIL", value = email, onValueChange = { email = it })

            // Phone
            LabeledInput(
                label = "PHONE NUMBER",
                value = phone,
                onValueChange = {
                    if (it.length <= 10) {
                        phone = it
                    }
                },
                keyboardType = KeyboardType.Phone
            )

            // Password
            LabeledInput(
                label = "PASSWORD",
                value = password,
                onValueChange = { password = it },
                keyboardType = KeyboardType.Password,
                isPassword = true
            )

            // DOB → opens DatePicker
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    "DATE OF BIRTH",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = MyFontFamily,
                    color = Color.Gray, modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(Modifier.height(5.dp))
                OutlinedTextField(
                    value = dob,
                    onValueChange = {},
                    readOnly = true,
                    enabled = false,
                    placeholder = {
                        Text(
                            "Select DOB", color = Color.Gray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = MyFontFamily
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { datePickerDialog.show() },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Black,
                        disabledContainerColor = Color(0xFFE0E0E0),
                        disabledBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedContainerColor = Color(0xFFE0E0E0),
                        focusedContainerColor = Color(0xFFE0E0E0)
                    )
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Sign Up Button
            Button(
                onClick = { // 👇 Basic validation before signup
                    when {
                        name.isBlank() -> {
                            context.showToast("Name is required")
                        }

                        email.isBlank() -> {
                            context.showToast("Email is required")
                        }

                        phone.isBlank() -> {
                            context.showToast("Phone number is required")
                        }

                        password.isBlank() -> {
                            context.showToast("Password is required")
                        }

                        dob.isBlank() -> {
                            context.showToast("Date of birth is required")
                        }

                        else -> {
                            viewModel.createUserApi(
                                CreateUserRequest(
                                    name = name,
                                    email = email,
                                    phoneNo = phone,
                                    password = password,
                                    dob = dob
                                )
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Sign up", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(24.dp))
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
fun SignUpPreview() {
    SignUp({})
}