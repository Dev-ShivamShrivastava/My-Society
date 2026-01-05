package com.indigo.mysociety.presentation.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.domain.model.request.CreateServiceRequest
import com.domain.model.request.LoginRequest
import com.indigo.mysociety.R
import com.indigo.mysociety.presentation.commonUI.LabeledInput
import com.indigo.mysociety.utils.MyFontFamily
import com.indigo.mysociety.utils.isValidEmail
import com.indigo.mysociety.utils.showToast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val viewModel: HomeVM = hiltViewModel()
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()





    var name by remember { mutableStateOf("shivam Shrivastava") }
    var phone by remember { mutableStateOf("6200968475") }
    var service by remember { mutableStateOf("Plumbing") }
    var message by remember { mutableStateOf("Change it") }

    var expanded by remember { mutableStateOf(false) }

    var isFlipped by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "rotation"
    )

    LaunchedEffect(Unit) {
        viewModel.homeUiEvent.collect { event ->
            when(event){
                is HomeUIEvent.ShowToast -> context.showToast(event.message)
                HomeUIEvent.NavigateDetailsScreen -> {
                    isFlipped = true
                }

            }
        }
    }

    // 🔙 Handle back press
    BackHandler(enabled = true) {
        if (isFlipped) {
            // If on thank-you screen, go back to form instead of exiting
            name = ""
            phone = ""
            service = ""
            message = ""
            isFlipped = false
        } else {
            // Default behavior (exit activity)
            (context as? Activity)?.finish()
        }
    }

    Box(){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),// peach bg
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Top Logo + Title
            Spacer(Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .border(2.dp, Color.Black, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("S", fontSize = 36.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(12.dp))

            Text(
                "MY SOCIETY",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = MyFontFamily
            )
            Text(
                "All Your Home Services, One App.",
                color = Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = MyFontFamily
            )

            Spacer(Modifier.height(24.dp))

            // Dark Blue Card

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            ) {
                // Background Image
                Image(
                    painter = painterResource(id = R.drawable.home_bg), // your drawable
                    contentDescription = null,
                    contentScale = ContentScale.Crop, // fills & crops nicely
                    modifier = Modifier.matchParentSize()  // covers full Box
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                )
                {
                    Text(
                        "QUICK SERVICE REQUEST",
                        color = Color(0xFFE57373),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Send Inspection Request",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(24.dp))


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                rotationY = rotation
                                cameraDistance = 8 * density // adds 3D depth
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (rotation <= 90f) {
                            // 👉 Front Side (Form)
                            Column(
                                modifier = Modifier.verticalScroll(rememberScrollState()),
                            ) {

                                // Name Field
                                LabeledInput(
                                    label = "Your Name",
                                    labelColor = Color.White,
                                    value = name,
                                    onValueChange = { name = it })

                                // Phone Field
                                LabeledInput(
                                    label = "Phone Number",
                                    labelColor = Color.White,
                                    value = phone,
                                    onValueChange = {
                                        if (it.length <= 10) {
                                            phone = it
                                        }
                                    },
                                    keyboardType = KeyboardType.Phone
                                )


                                Spacer(Modifier.height(12.dp))
                                Text(
                                    "Choose Services",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = MyFontFamily,
                                    color = Color.White, modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                )
                                Spacer(Modifier.height(5.dp))

                                // Dropdown for Services
                                ExposedDropdownMenuBox(
                                    expanded = expanded,
                                    onExpandedChange = { expanded = !expanded }
                                ) {
                                    OutlinedTextField(
                                        value = service,
                                        onValueChange = { },
                                        readOnly = true,
                                        shape = RoundedCornerShape(12.dp),
                                        placeholder = {
                                            Text(
                                                "Please Select", color = Color.Gray,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Medium,
                                                fontFamily = MyFontFamily,
                                                textAlign = TextAlign.Center
                                            )
                                        },
                                        trailingIcon = {
                                            Icon(
                                                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp
                                                else Icons.Filled.KeyboardArrowDown,
                                                contentDescription = null,
                                                tint = Color.Black // 👈 change this to your desired color
                                            )
                                        },
                                        modifier = Modifier
                                            .menuAnchor()
                                            .fillMaxWidth(),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            unfocusedBorderColor = Color.Transparent,
                                            focusedBorderColor = Color.Transparent,
                                            unfocusedContainerColor = Color(0xFFE0E0E0),
                                            focusedContainerColor = Color(0xFFE0E0E0),

                                            focusedTextColor = Color.Black,
                                            unfocusedTextColor = Color.Black,
                                            disabledTextColor = Color.Black,
                                            cursorColor = Color.Black,
                                            disabledContainerColor = Color.LightGray,
                                        )
                                    )

                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }
                                    ) {
                                        viewModel.serviceList.forEach { option ->
                                            DropdownMenuItem(
                                                text = { Text(option) },
                                                onClick = {
                                                    service = option
                                                    expanded = false
                                                }
                                            )
                                        }
                                    }
                                }

                                Spacer(Modifier.height(12.dp))

                                Text(
                                    "Message",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = MyFontFamily,
                                    color = Color.White, modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                )
                                Spacer(Modifier.height(5.dp))


                                // Message Field
                                OutlinedTextField(
                                    value = message,
                                    onValueChange = { message = it },
                                    placeholder = {
                                        Text(
                                            "Message", color = Color.Gray,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium,
                                            fontFamily = MyFontFamily
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp),
                                    shape = RoundedCornerShape(12.dp),

                                    colors = OutlinedTextFieldDefaults.colors(
                                        unfocusedBorderColor = Color.Transparent,
                                        focusedBorderColor = Color.Transparent,
                                        unfocusedContainerColor = Color(0xFFE0E0E0),
                                        focusedContainerColor = Color(0xFFE0E0E0),

                                        focusedTextColor = Color.Black,
                                        unfocusedTextColor = Color.Black,
                                        disabledTextColor = Color.Black,
                                        cursorColor = Color.Black,
                                        disabledContainerColor = Color.LightGray,
                                    )
                                )

                                Spacer(Modifier.height(20.dp))

                                // Submit Button
                                Button(
                                    onClick = {
                                        when {
                                            name.isBlank() -> {
                                                context.showToast("Name is required")
                                            }

                                            phone.isBlank() -> {
                                                context.showToast("Phone number is required")
                                            }

                                            service.isBlank() -> {
                                                context.showToast("Service is required")
                                            }

                                            message.isBlank() -> {
                                                context.showToast("Message is required")
                                            }

                                            else -> {
                                                viewModel.createServiceRequestApi(
                                                    CreateServiceRequest(
                                                        name = name,
                                                        phoneNo = phone,
                                                        service = service,
                                                        message = message
                                                    )
                                                )
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            0xFF2E7D9A
                                        )
                                    )
                                ) {
                                    Text("Submit", color = Color.White, fontSize = 16.sp)
                                }
                                Spacer(Modifier.height(30.dp))
                            }
                        } else {
                            // 👉 Back Side (Thank-you message)

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                                    .padding(20.dp)
                                    .graphicsLayer {
                                        rotationY = 180f   // flip content back to correct direction
                                    }) {
                                // Background Image
//                            Image(
//                                painter = painterResource(id = R.drawable.submit_bg), // your drawable
//                                contentDescription = null,
//                                contentScale = ContentScale.Crop, // fills & crops nicely
//                                modifier = Modifier.matchParentSize()  // covers full Box
//                            )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Thank you for submitting your request!\n" +
                                                "Our team will connect with you shortly.\n\n" +
                                                "For faster assistance, you can reach directly via WhatsApp or Call.",
                                        textAlign = TextAlign.Center,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = MyFontFamily
                                    )

                                    Spacer(Modifier.height(20.dp))
                                    Row(
                                        modifier = Modifier
                                            .clickable {
                                                try {
                                                    val phoneNumber =
                                                        viewModel.whatsappNumber // 👈 add country code + number (without + sign)
                                                    // Final formatted message
                                                    val message = """
Hello, I need some help regarding "$service".
Here are my details:

Name: $name
Phone: $phone
Message: $message
""".trimIndent()


                                                    val url = "https://wa.me/$phoneNumber?text=${
                                                        Uri.encode(message)
                                                    }"
                                                    val intent = Intent(Intent.ACTION_VIEW).apply {
                                                        data = Uri.parse(url)
                                                        setPackage("com.whatsapp") // ensures only WhatsApp handles it
                                                    }
                                                    context.startActivity(intent)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                    Toast.makeText(
                                                        context,
                                                        "WhatsApp not installed",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                            .padding(8.dp), // optional padding for touch target
                                        verticalAlignment = Alignment.CenterVertically) {
                                        Image(
                                            painter = painterResource(id = R.drawable.whatsapp),
                                            contentDescription = null,
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(Modifier.width(5.dp))
                                        Text(
                                            "Whatsapp",
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 16.sp,
                                            color = Color(0xFF4EC817)
                                        )
                                    }

                                    Spacer(Modifier.height(10.dp))

                                    Row(
                                        modifier = Modifier
                                            .clickable {
                                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                                    data = Uri.parse("tel:${viewModel.mobileNumber}")
                                                }
                                                context.startActivity(intent)
                                            }
                                            .padding(8.dp), // optional padding for touch target
                                        verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_call),
                                            contentDescription = null,
                                            modifier = Modifier.size(24.dp),
                                            tint = Color(0xFFEE6C70)
                                        )
                                        Spacer(Modifier.width(5.dp))
                                        Text(
                                            "Call Now",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color(0xFFEE6C70)
                                        )
                                    }


                                    Spacer(Modifier.height(40.dp))
                                    Button(onClick = {
                                        name = ""
                                        phone = ""
                                        service = ""
                                        message = ""
                                        isFlipped = false
                                    }) {
                                        Text("Back")
                                    }
                                }
                            }

                        }
                    }
                }
            }

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
fun HomePreview() {
    HomeScreen()
}