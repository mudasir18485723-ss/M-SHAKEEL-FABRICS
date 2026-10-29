package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.data.model.CartItem
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamSurfaceVariant
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
fun CartScreen(
    viewModel: StoreViewModel,
    cartItems: List<CartItem>,
    subtotal: Double,
    discountPercent: Double,
    onNavigateShop: () -> Unit,
    onNavigateCheckout: () -> Unit
) {
    val currencyFormatter = NumberFormat.getNumberInstance(Locale.US)
    val discountAmount = subtotal * discountPercent
    val discountedSubtotal = subtotal - discountAmount
    val deliveryFee = if (discountedSubtotal >= 5000.0 || cartItems.isEmpty()) 0.0 else 250.0
    val totalAmount = if (cartItems.isEmpty()) 0.0 else discountedSubtotal + deliveryFee

    var promoInput by remember { mutableStateOf("") }
    var promoMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmCream)
    ) {
        // Top Header
        Surface(
            color = NavyDark,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Text(
                    text = "MY SHOPPING BAG",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = GoldLight,
                        letterSpacing = 1.5.sp
                    )
                )
                Text(
                    text = "${cartItems.sumOf { it.quantity }} items selected",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = GoldPrimary
                    )
                )
            }
        }

        if (cartItems.isEmpty()) {
            // Empty State
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.ShoppingBag,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Your Bag is Currently Empty",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = FontFamily.Serif,
                            color = NavyPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Discover our exquisite collection of authentic royal boski, handspun karandi, and luxury cottons.",
                        color = TextSecondary,
                        fontSize = 13.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = onNavigateShop,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NavyPrimary,
                            contentColor = GoldLight
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("empty_cart_explore_btn")
                    ) {
                        Text("Explore Collections", fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            // Active Cart Content
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 14.dp)
            ) {
                // Free Shipping Progress
                item {
                    val progress = (discountedSubtotal / 5000.0).coerceIn(0.0, 1.0).toFloat()
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                            .border(0.5.dp, CreamBorder, RoundedCornerShape(8.dp)),
                        colors = CardDefaults.cardColors(containerColor = SilkWhite)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.LocalShipping,
                                    contentDescription = null,
                                    tint = GoldDark,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (discountedSubtotal >= 5000.0) {
                                        "Congratulations! You unlocked FREE Express Delivery"
                                    } else {
                                        val remaining = 5000.0 - discountedSubtotal
                                        "Add Rs. ${currencyFormatter.format(remaining.toInt())} more for FREE Delivery"
                                    },
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (discountedSubtotal >= 5000.0) SuccessGreen else NavyPrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            LinearProgressIndicator(
                                progress = progress,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = GoldAccent,
                                trackColor = CreamSurfaceVariant
                            )
                        }
                    }
                }

                // Cart Item Cards
                items(cartItems, key = { it.cartId }) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .border(1.dp, CreamBorder, RoundedCornerShape(10.dp)),
                        colors = CardDefaults.cardColors(containerColor = SilkWhite)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Thumbnail
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(item.productImage)
                                    .crossfade(true)
                                    .error(R.drawable.msf_logo)
                                    .build(),
                                contentDescription = item.productName,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(0.5.dp, CreamBorder, RoundedCornerShape(8.dp))
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            // Details
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.productName,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontFamily = FontFamily.Serif,
                                        fontWeight = FontWeight.Bold,
                                        color = NavyDark
                                    ),
                                    maxLines = 1
                                )
                                Text(
                                    text = "Option: ${item.selectedOption}",
                                    fontSize = 11.sp,
                                    color = GoldDark,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Rs. ${currencyFormatter.format(item.productPrice.toInt())}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NavyPrimary
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                // Quantity Controls
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .border(0.5.dp, CreamBorder, RoundedCornerShape(6.dp))
                                        .background(WarmCream)
                                ) {
                                    IconButton(
                                        onClick = {
                                            viewModel.updateCartQuantity(item.cartId, item.quantity - 1)
                                        },
                                        modifier = Modifier.size(28.dp).testTag("cart_minus_${item.cartId}")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Remove,
                                            contentDescription = "Decrease",
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }

                                    Text(
                                        text = item.quantity.toString(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(horizontal = 10.dp)
                                    )

                                    IconButton(
                                        onClick = {
                                            viewModel.updateCartQuantity(item.cartId, item.quantity + 1)
                                        },
                                        modifier = Modifier.size(28.dp).testTag("cart_plus_${item.cartId}")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Increase",
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            }

                            // Delete Action
                            IconButton(
                                onClick = { viewModel.removeCartItem(item.cartId) },
                                modifier = Modifier.testTag("delete_item_${item.cartId}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DeleteOutline,
                                    contentDescription = "Remove item",
                                    tint = TextMuted
                                )
                            }
                        }
                    }
                }

                // Promo Coupon Card
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .border(1.dp, CreamBorder, RoundedCornerShape(10.dp)),
                        colors = CardDefaults.cardColors(containerColor = SilkWhite)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "VIP PRIVILEGE COUPON",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldDark,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = promoInput,
                                    onValueChange = { promoInput = it },
                                    placeholder = { Text("Enter VIP10 or MSF15", fontSize = 12.sp) },
                                    singleLine = true,
                                    shape = RoundedCornerShape(8.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = GoldDark,
                                        unfocusedBorderColor = CreamBorder
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("promo_input")
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = {
                                        val success = viewModel.applyPromoCode(promoInput)
                                        promoMessage = if (success) {
                                            "Coupon applied successfully!"
                                        } else {
                                            "Invalid coupon code"
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = NavyPrimary,
                                        contentColor = GoldLight
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.testTag("apply_promo_btn")
                                ) {
                                    Text("Apply", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            promoMessage?.let { msg ->
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = msg,
                                    fontSize = 11.sp,
                                    color = if (discountPercent > 0.0) SuccessGreen else TextSecondary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                // Order Price Summary Card
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .border(1.dp, CreamBorder, RoundedCornerShape(10.dp)),
                        colors = CardDefaults.cardColors(containerColor = SilkWhite)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "ORDER VALUE SUMMARY",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldDark,
                                letterSpacing = 1.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Bag Subtotal", color = TextSecondary, fontSize = 13.sp)
                                Text("Rs. ${currencyFormatter.format(subtotal.toInt())}", fontWeight = FontWeight.Medium, color = TextPrimary)
                            }

                            if (discountAmount > 0.0) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("VIP Privilege Discount (${(discountPercent * 100).toInt()}%)", color = SuccessGreen, fontSize = 13.sp)
                                    Text("- Rs. ${currencyFormatter.format(discountAmount.toInt())}", fontWeight = FontWeight.Bold, color = SuccessGreen)
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Nationwide Delivery (COD)", color = TextSecondary, fontSize = 13.sp)
                                Text(
                                    text = if (deliveryFee == 0.0) "FREE" else "Rs. ${currencyFormatter.format(deliveryFee.toInt())}",
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
                                Text(
                                    text = "Estimated Total",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = NavyPrimary
                                    )
                                )
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
            }

            // Bottom Sticky Checkout Bar
            Surface(
                color = SilkWhite,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 0.5.dp, color = CreamBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Total to Pay", fontSize = 11.sp, color = TextMuted)
                        Text(
                            text = "Rs. ${currencyFormatter.format(totalAmount.toInt())}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = NavyPrimary
                            )
                        )
                    }

                    Button(
                        onClick = onNavigateCheckout,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldAccent,
                            contentColor = NavyDark
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .height(48.dp)
                            .testTag("cart_proceed_to_checkout_btn")
                    ) {
                        Text("Proceed to Checkout", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}
