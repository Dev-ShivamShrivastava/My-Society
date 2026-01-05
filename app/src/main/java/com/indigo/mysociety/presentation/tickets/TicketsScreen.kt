package com.indigo.mysociety.presentation.tickets

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.domain.model.response.ServiceTicketListResponse
import com.google.gson.Gson
import com.indigo.mysociety.utils.Dimens
import com.indigo.mysociety.utils.formatUtcToIstDate
import com.indigo.mysociety.utils.showToast
import com.indigo.mysociety.utils.toArrayList

@Composable
fun TicketsScreen(
    onTicketClick: (json:String) -> Unit,
) {
    val viewModel: TicketVM = hiltViewModel()
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Pending", "Completed")

    LaunchedEffect(Unit) {
        viewModel.ticketsUIEvent.collect { event ->
            when(event){
                is TicketUIEvent.ShowToast -> context.showToast(event.message)
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color(0xFFF8F9FA))
            .padding(Dimens._16dp)
    ) {

        Text(
            text = "My Service Requests",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1E1E)
            )
        )

        Spacer(modifier = Modifier.height(Dimens._16dp))

        // 🔹 Tab Layout
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.White,
            contentColor = Color(0xFF1E88E5),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(Dimens._4dp),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = Color(0xFF1E88E5), // 👈 your custom color
                    height = 3.dp // optional: make it thicker/thinner
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        viewModel.getServiceTicketListApi(title)
                    },
                    text = {
                        Text(
                            title,
                            color = if (selectedTabIndex == index) Color(0xFF1E88E5) else Color.Gray,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(Dimens._16dp))

        // 🔹 Loading or Data View
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (state.isLoading) {
                CircularProgressIndicator(color = Color(0xFF1E88E5))
            } else {
                val currentList = state.response?.data.toArrayList()

                if (currentList.isEmpty()) {
                    Text("No services found", color = Color.Gray)
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(Dimens._12dp),
                        contentPadding = PaddingValues(bottom = Dimens._24dp)
                    ) {
                        items(currentList) { service ->
                            ServiceCard(service = service,onTicketClick)
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun ServiceCard(
    service: ServiceTicketListResponse.ServiceTicketData,
    onTicketClick: (json: String) -> Unit
) {
    val statusColor = when (service.status) {
        "Pending" -> Color(0xFFFFC107)
        "Approved" -> Color(0xFF4CAF50)
        "Rejected" -> Color(0xFFF44336)
        "Completed" -> Color(0xFF2196F3)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(Dimens._4dp, RoundedCornerShape(Dimens._16dp)).clickable {
                val json = Uri.encode(Gson().toJson(service))
                onTicketClick(json)
            },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(Dimens._16dp)
    ) {
        Column(modifier = Modifier.padding(Dimens._16dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = service.service?.ifEmpty { "N/A" } ?: "N/A",
                    fontWeight = FontWeight.Bold,
                    fontSize = Dimens._18sp,
                    color = Color.Black
                )

                Text(
                    text = service.status?.ifEmpty { "N/A" } ?: "N/A",
                    color = statusColor,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .background(statusColor.copy(alpha = 0.1f), RoundedCornerShape(Dimens._8dp))
                        .padding(horizontal = Dimens._8dp, vertical = Dimens._4dp),
                    fontSize = Dimens._12sp
                )
            }

            Spacer(modifier = Modifier.height(Dimens._8dp))

            Text(
                text = service.message?.ifEmpty { "N/A" } ?: "N/A",
                color = Color.DarkGray,
                fontSize = Dimens._14sp
            )

            Spacer(modifier = Modifier.height(Dimens._8dp))

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Requested on: ${service.createdAt?.formatUtcToIstDate()}",
                    color = Color.Gray,
                    fontSize = Dimens._12sp
                )
                Text(
                    text = "Ref: ${service._id}",
                    color = Color.Gray,
                    fontSize = Dimens._12sp
                )
            }
        }
    }
}


