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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CartItem
import com.example.data.model.OrderEntity
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamSurfaceVariant
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
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
import java.util.Locale

@Composable
fun CheckoutScreen(
    viewModel: StoreViewModel,
    cartItems: List<CartItem>,
    subtotal: Double,
    discountPercent: Double,
    onBack: () -> Unit,
    onOrderSuccess: (OrderEntity) -> Unit
) {
    val formState by viewModel.checkoutForm.collectAsState()

    val popularCities = listOf("Lahore", "Karachi", "Islamabad", "Rawalpindi", "Faisalabad", "Multan", "Peshawar", "Sialkot", "Quetta")

    var selectedPayment by remember { mutableStateOf("COD") }

    val currencyFormatter = NumberFormat.getNumberInstance(Locale.US)
    val discountAmount = subtotal * discountPercent
    val discountedSubtotal = subtotal - discountAmount
    val deliveryFee = if (discountedSubtotal >= 5000.0 || cartItems.isEmpty()) 0.0 else 250.0
    val totalAmount = discountedSubtotal + deliveryFee

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmCream)
    ) {
        // Top App Bar
        Surface(
            color = NavyDark,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("checkout_back_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = GoldLight
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(
                        text = "VIP SECURE CHECKOUT",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            color = GoldLight,
                            letterSpacing = 1.sp
                        )
                    )
                    Text(
                        text = "Cash on Delivery • Express Verification",
                        style = MaterialTheme.typography.bodySmall.copy(color = GoldPrimary)
                    )
                }
            }
        }

        // Scrollable Checkout Body
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // General Error Banner (if submission error)
            formState.submitError?.let { err ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = ErrorRed.copy(alpha = 0.1f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = null,
                            tint = ErrorRed,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = err,
                            color = ErrorRed,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Customer Contact & Shipping Details Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CreamBorder, RoundedCornerShape(10.dp)),
                colors = CardDefaults.cardColors(containerColor = SilkWhite)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "1. RECIPIENT & DELIVERY DETAILS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldDark,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Full Name
                    OutlinedTextField(
                        value = formState.name,
                        onValueChange = { viewModel.updateCheckoutForm(name = it) },
                        label = { Text("Full Name *") },
                        placeholder = { Text("e.g. Barrister Daniyal Khan") },
                        isError = formState.nameError != null,
                        supportingText = formState.nameError?.let { { Text(it, color = ErrorRed) } },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldDark,
                            unfocusedBorderColor = CreamBorder
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("checkout_name_input")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Phone Number
                    OutlinedTextField(
                        value = formState.phone,
                        onValueChange = { viewModel.updateCheckoutForm(phone = it) },
                        label = { Text("Mobile Number (for Courier SMS/Call) *") },
                        placeholder = { Text("0300 1234567") },
                        isError = formState.phoneError != null,
                        supportingText = formState.phoneError?.let { { Text(it, color = ErrorRed) } },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldDark,
                            unfocusedBorderColor = CreamBorder
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("checkout_phone_input")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Email Address
                    OutlinedTextField(
                        value = formState.email,
                        onValueChange = { viewModel.updateCheckoutForm(email = it) },
                        label = { Text("Email Address (for Order Invoice & Tracking)") },
                        placeholder = { Text("patron@example.com") },
                        isError = formState.emailError != null,
                        supportingText = formState.emailError?.let { { Text(it, color = ErrorRed) } },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldDark,
                            unfocusedBorderColor = CreamBorder
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("checkout_email_input")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Delivery Address
                    OutlinedTextField(
                        value = formState.address,
                        onValueChange = { viewModel.updateCheckoutForm(address = it) },
                        label = { Text("Delivery Address (Street, House/Apt, Sector) *") },
                        placeholder = { Text("House 42, Street 8, Phase 5 DHA") },
                        isError = formState.addressError != null,
                        supportingText = formState.addressError?.let { { Text(it, color = ErrorRed) } },
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldDark,
                            unfocusedBorderColor = CreamBorder
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("checkout_address_input")
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // City Selection Pills
                    Text("Select Destination City:", fontSize = 11.sp, color = TextSecondary, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(popularCities) { city ->
                            val isSel = formState.city.equals(city, ignoreCase = true)
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (isSel) NavyPrimary else WarmCream,
                                border = androidx.compose.foundation.BorderStroke(
                                    width = 1.dp,
                                    color = if (isSel) GoldAccent else CreamBorder
                                ),
                                modifier = Modifier
                                    .clickable { viewModel.updateCheckoutForm(city = city) }
                                    .padding(vertical = 2.dp)
                            ) {
                                Text(
                                    text = city,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSel) GoldLight else TextPrimary,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Order Notes / Tailoring Remarks
                    OutlinedTextField(
                        value = formState.notes,
                        onValueChange = { viewModel.updateCheckoutForm(notes = it) },
                        label = { Text("Special Instructions or Tailoring Notes") },
                        placeholder = { Text("e.g. Leave package with guard or call before delivery") },
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldDark,
                            unfocusedBorderColor = CreamBorder
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("checkout_notes_input")
                    )
                }
            }

            // Payment Option Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CreamBorder, RoundedCornerShape(10.dp)),
                colors = CardDefaults.cardColors(containerColor = SilkWhite)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "2. PAYMENT METHOD",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldDark,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Option 1: Cash on Delivery (Enabled & Default)
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = WarmCream,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (selectedPayment == "COD") GoldAccent else CreamBorder
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedPayment = "COD" }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedPayment == "COD",
                                onClick = { selectedPayment = "COD" },
                                colors = RadioButtonDefaults.colors(selectedColor = GoldAccent)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.Payments,
                                contentDescription = null,
                                tint = GoldDark,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Cash on Delivery (COD)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = NavyPrimary
                                )
                                Text(
                                    text = "Pay in cash at your doorstep upon parcel receipt",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Option 2: Online Bank / Card (Optional / Informational placeholder)
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = SilkWhite,
                        border = androidx.compose.foundation.BorderStroke(0.5.dp, CreamBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = false,
                                enabled = false,
                                onClick = null,
                                colors = RadioButtonDefaults.colors(disabledSelectedColor = TextMuted)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.CreditCard,
                                contentDescription = null,
                                tint = TextMuted,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Online Card / Direct Bank Transfer",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 13.sp,
                                    color = TextMuted
                                )
                                Text(
                                    text = "Optional online gateway (Cash on Delivery available by default)",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                        }
                    }
                }
            }

            // Order Review Summary Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CreamBorder, RoundedCornerShape(10.dp)),
                colors = CardDefaults.cardColors(containerColor = SilkWhite)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "3. ORDER REVIEW SUMMARY",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldDark,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    cartItems.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${item.productName} x${item.quantity}",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp,
                                    color = NavyDark
                                )
                                Text(
                                    text = item.selectedOption,
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }
                            Text(
                                text = "Rs. ${currencyFormatter.format((item.productPrice * item.quantity).toInt())}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = NavyPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(color = CreamBorder, thickness = 0.5.dp)
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal", fontSize = 12.sp, color = TextSecondary)
                        Text("Rs. ${currencyFormatter.format(subtotal.toInt())}", fontSize = 12.sp, color = TextPrimary)
                    }

                    if (discountAmount > 0.0) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("VIP Discount", fontSize = 12.sp, color = SuccessGreen)
                            Text("- Rs. ${currencyFormatter.format(discountAmount.toInt())}", fontSize = 12.sp, color = SuccessGreen, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Delivery Charges", fontSize = 12.sp, color = TextSecondary)
                        Text(
                            text = if (deliveryFee == 0.0) "FREE" else "Rs. ${currencyFormatter.format(deliveryFee.toInt())}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (deliveryFee == 0.0) SuccessGreen else TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(color = CreamBorder, thickness = 0.5.dp)
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total Amount (Pay on Delivery)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NavyPrimary)
                        Text(
                            text = "Rs. ${currencyFormatter.format(totalAmount.toInt())}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                color = NavyPrimary
                            )
                        )
                    }
                }
            }
        }

        // Bottom Confirmation Button
        Surface(
            color = SilkWhite,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 0.5.dp, color = CreamBorder)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Button(
                    onClick = {
                        viewModel.submitOrder { order ->
                            onOrderSuccess(order)
                        }
                    },
                    enabled = !formState.isSubmitting && cartItems.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldAccent,
                        contentColor = NavyDark
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("checkout_confirm_order_btn")
                ) {
                    if (formState.isSubmitting) {
                        CircularProgressIndicator(
                            color = NavyDark,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Verifying Stock & Reserving...", fontWeight = FontWeight.Bold)
                    } else {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Confirm Order (Cash on Delivery)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
