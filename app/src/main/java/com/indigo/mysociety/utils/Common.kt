package com.indigo.mysociety.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.indigo.mysociety.R
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

val MyFontFamily = FontFamily(
    Font(R.font.dm_serif_text_regular, weight = FontWeight.Normal),
    Font(R.font.dm_serif_text_regular, weight = FontWeight.Medium),
    Font(R.font.dm_serif_text_italic, weight = FontWeight.Bold),
)

fun Context.showToast(message: String) {
   Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
    return emailRegex.matches(email)
}

fun <T>List<T>?.toArrayList(): ArrayList<T> {
    if (this == null) return ArrayList()
    return ArrayList(this)
}

fun String.formatUtcToIstDate(): String {
    return try {
        // Step 1: Define input format (UTC)
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        // Step 2: Define output format (IST)
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata")

        // Step 3: Parse and convert
        val date = inputFormat.parse(this)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        e.printStackTrace()
        this
    }
}


@Composable
fun CustomSnackBar(message: String, isError: Boolean) {
    Snackbar(
        modifier = Modifier
            .padding(Dimens._8dp)
            .clip(RoundedCornerShape(Dimens._12dp)),
        containerColor = if (isError) Color(0xFFD32F2F) else Color(0xFF388E3C), // red / green
        contentColor = Color.White
    ) {
        Text(text = message, style = MaterialTheme.typography.bodyMedium)
    }
}

