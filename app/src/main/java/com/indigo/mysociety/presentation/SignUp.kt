package com.indigo.mysociety.presentation

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.indigo.mysociety.presentation.commonUI.LabeledInput
import com.indigo.mysociety.utils.MyFontFamily
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun SignUp(
    onSignUpClick: (String, String, String, String, String) -> Unit,
    onLoginClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }


    val context = LocalContext.current

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            onValueChange = { phone = it },
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
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)) {
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
                placeholder = { Text("Select DOB", color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = MyFontFamily) },
                modifier = Modifier
                    .fillMaxWidth().clickable(interactionSource = interactionSource,indication = null) { datePickerDialog.show() },
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
            onClick = { onSignUpClick(name, email, phone, password, dob) },
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
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    SignUp({ str, str1, str2, str3, str4 -> }, {})
}