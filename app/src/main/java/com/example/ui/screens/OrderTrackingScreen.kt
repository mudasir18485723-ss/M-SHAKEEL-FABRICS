package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.OrderEntity
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamSurfaceVariant
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.LowStockOrange
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyLight
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SilkWhite
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarmCream
import com.example.ui.viewmodel.StoreViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrderTrackingScreen(
    viewModel: StoreViewModel,
    onNavigateShop: () -> Unit
) {
    val trackingQuery by viewModel.trackingQuery.collectAsState()
    val trackedOrders by viewModel.trackedOrders.collectAsState()
    val currencyFormatter = NumberFormat.getNumberInstance(Locale.US)
    val dateFormatter = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmCream)
    ) {
        // Header
        Surface(
            color = NavyDark,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "ORDER STATUS & DISPATCH TRACKING",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = GoldLight,
                        letterSpacing = 1.sp
                    )
                )
                Text(
                    text = "Search by Order ID (#MSF-...) or contact number",
                    style = MaterialTheme.typography.bodySmall.copy(color = GoldPrimary)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = trackingQuery,
                    onValueChange = { viewModel.setTrackingQuery(it) },
                    placeholder = { Text("Enter Order ID or Mobile Number", fontSize = 12.sp) },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = GoldLight)
                    },
                    trailingIcon = {
                        if (trackingQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.setTrackingQuery("") }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Clear", tint = GoldLight)
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GoldAccent,
                        unfocusedBorderColor = GoldPrimary.copy(alpha = 0.5f),
                        focusedContainerColor = NavyLight,
                        unfocusedContainerColor = NavyLight,
                        focusedTextColor = SilkWhite,
                        unfocusedTextColor = SilkWhite
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("tracking_search_input")
                )
            }
        }

        if (trackedOrders.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Inventory,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(54.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (trackingQuery.isBlank()) "No Orders Placed Yet" else "No Matching Order Found",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = FontFamily.Serif,
                            color = NavyPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (trackingQuery.isBlank()) {
                            "Place an order from our luxury catalog to view its live courier progress here."
                        } else {
                            "Please verify the Order ID (e.g. MSF-12345) or the mobile number entered during checkout."
                        },
                        color = TextSecondary,
                        fontSize = 12.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(trackedOrders, key = { it.orderId }) { order ->
                    OrderTrackingCard(
                        order = order,
                        currencyFormatter = currencyFormatter,
                        dateFormatter = dateFormatter
                    )
                }
            }
        }
    }
}

@Composable
fun OrderTrackingCard(
    order: OrderEntity,
    currencyFormatter: NumberFormat,
    dateFormatter: SimpleDateFormat
) {
    val statusSteps = listOf("Pending", "Confirmed", "Shipped", "Delivered")
    val currentStepIndex = when (order.status.lowercase()) {
        "pending" -> 0
        "confirmed" -> 1
        "shipped" -> 2
        "delivered" -> 3
        "cancelled" -> -1
        else -> 0
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CreamBorder, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = SilkWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row: Order ID and Status Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = order.orderId,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            color = NavyPrimary
                        )
                    )
                    Text(
                        text = dateFormatter.format(Date(order.timestamp)),
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = when (order.status.lowercase()) {
                        "delivered" -> SuccessGreen
                        "shipped" -> NavyPrimary
                        "confirmed" -> GoldDark
                        "cancelled" -> ErrorRed
                        else -> LowStockOrange
                    }
                ) {
                    Text(
                        text = order.status.uppercase(),
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Visual Progress Timeline (if not cancelled)
            if (currentStepIndex >= 0) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        statusSteps.forEachIndexed { index, stepName ->
                            val isCompleted = index <= currentStepIndex
                            val isCurrent = index == currentStepIndex

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isCompleted) GoldAccent else CreamSurfaceVariant
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isCompleted) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = NavyDark,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .clip(CircleShape)
                                                .background(TextMuted)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = stepName,
                                    fontSize = 10.sp,
                                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isCompleted) NavyPrimary else TextMuted
                                )
                            }
                        }
                    }
                }
            } else {
                Text(
                    text = "This order was cancelled.",
                    color = ErrorRed,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = CreamBorder, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(10.dp))

            // Order Content Summary
            Text(
                text = "Purchased Items:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = GoldDark
            )
            Text(
                text = order.itemsSummary,
                fontSize = 12.sp,
                color = TextPrimary,
                modifier = Modifier.padding(vertical = 2.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Recipient & Destination
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Recipient:", fontSize = 11.sp, color = TextSecondary)
                Text("${order.customerName} (${order.customerPhone})", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Delivery Address:", fontSize = 11.sp, color = TextSecondary)
                Text("${order.deliveryAddress}, ${order.city}", fontSize = 11.sp, color = TextPrimary, maxLines = 1)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Payment Method:", fontSize = 11.sp, color = TextSecondary)
                Text(order.paymentMethod, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SuccessGreen)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = CreamBorder, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total COD Payable:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = NavyPrimary)
                Text(
                    text = "Rs. ${currencyFormatter.format(order.totalAmount.toInt())}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary
                    )
                )
            }
        }
    }
}
