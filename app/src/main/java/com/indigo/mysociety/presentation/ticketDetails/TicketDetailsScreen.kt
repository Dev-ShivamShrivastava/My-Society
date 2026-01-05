package com.indigo.mysociety.presentation.ticketDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.domain.model.response.ServiceTicketListResponse
import com.indigo.mysociety.utils.formatUtcToIstDate

@Composable
fun TicketDetailsScreen(ticket : ServiceTicketListResponse.ServiceTicketData){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = ticket.service ?: "N/A",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Status: ${ticket.status}",
            color = Color.Gray
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = ticket.message ?: "No message",
            fontSize = 16.sp
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Requested on: ${ticket.createdAt?.formatUtcToIstDate()}",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Reference: ${ticket._id}",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}