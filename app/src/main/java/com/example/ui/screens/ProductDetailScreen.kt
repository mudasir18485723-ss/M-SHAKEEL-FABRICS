package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.data.model.Product
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamSurfaceVariant
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldContainer
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
import java.util.Locale

@Composable
fun ProductDetailScreen(
    product: Product?,
    viewModel: StoreViewModel,
    onBack: () -> Unit,
    onNavigateCheckout: () -> Unit
) {
    if (product == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(WarmCream),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Product details unavailable.")
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = onBack) {
                    Text("Return to Shop")
                }
            }
        }
        return
    }

    val availableOptions = remember(product.options) {
        product.options.split(",").map { it.trim() }.filter { it.isNotBlank() }
    }
    var selectedOption by remember {
        mutableStateOf(availableOptions.firstOrNull() ?: "Standard Yardage")
    }
    var quantity by remember { mutableIntStateOf(1) }
    var isFavorite by remember { mutableStateOf(false) }

    val currencyFormatter = NumberFormat.getNumberInstance(Locale.US)
    val priceFormatted = "Rs. ${currencyFormatter.format(product.price.toInt())}"

    val isAvailable = product.stockQuantity > 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmCream)
    ) {
        // Top Navigation Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(NavyDark)
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("detail_back_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = GoldLight
                )
            }

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = GoldLight
                ),
                maxLines = 1,
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
            )

            IconButton(
                onClick = { isFavorite = !isFavorite }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Wishlist",
                    tint = if (isFavorite) GoldAccent else GoldLight
                )
            }
        }

        // Scrollable Content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Main Hero Fabric Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.1f)
                    .background(NavyDark.copy(alpha = 0.05f))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.imageUrl)
                        .crossfade(true)
                        .error(R.drawable.msf_logo)
                        .build(),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Badges overlay
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                ) {
                    Surface(
                        color = NavyDark.copy(alpha = 0.85f),
                        shape = RoundedCornerShape(6.dp),
                        border = androidx.compose.foundation.BorderStroke(0.5.dp, GoldLight)
                    ) {
                        Text(
                            text = product.fabricType.uppercase(),
                            color = GoldLight,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Product Information Block
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
                    .border(1.dp, CreamBorder, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = SilkWhite)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    // Category & Rating
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = product.category,
                            color = GoldDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = GoldAccent,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${product.rating} (${product.reviewCount} Reviews)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Title
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            color = NavyDark
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Price & Savings
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = priceFormatted,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = NavyPrimary
                            )
                        )

                        if (product.originalPrice != null && product.originalPrice > product.price) {
                            Spacer(modifier = Modifier.width(10.dp))
                            val origFormatted = "Rs. ${currencyFormatter.format(product.originalPrice.toInt())}"
                            Text(
                                text = origFormatted,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = TextMuted,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            )

                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                color = GoldContainer,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                val discountPct = (((product.originalPrice - product.price) / product.originalPrice) * 100).toInt()
                                Text(
                                    text = "$discountPct% OFF",
                                    color = GoldDark,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Divider(color = CreamBorder, thickness = 0.5.dp)
                    Spacer(modifier = Modifier.height(14.dp))

                    // Stock Availability Status
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = when {
                                !isAvailable -> ErrorRed
                                product.stockQuantity in 1..5 -> LowStockOrange
                                else -> SuccessGreen
                            },
                            modifier = Modifier.size(10.dp)
                        ) {}

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = when {
                                !isAvailable -> "Currently Sold Out. Next weaving batch in production."
                                product.stockQuantity in 1..5 -> "Low Stock: Only ${product.stockQuantity} suits remaining!"
                                else -> "In Stock: Ready for immediate dispatch (${product.stockQuantity} available)"
                            },
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = when {
                                !isAvailable -> ErrorRed
                                product.stockQuantity in 1..5 -> LowStockOrange
                                else -> SuccessGreen
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Select Fabric / Size Option
                    Text(
                        text = "SELECT FABRIC YARDAGE / OPTION",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldDark,
                        letterSpacing = 1.5.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        availableOptions.forEach { opt ->
                            val isSel = opt == selectedOption
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSel) NavyPrimary else WarmCream,
                                border = androidx.compose.foundation.BorderStroke(
                                    width = 1.dp,
                                    color = if (isSel) GoldAccent else CreamBorder
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedOption = opt }
                                    .testTag("option_$opt")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = opt,
                                        color = if (isSel) GoldLight else TextPrimary,
                                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 13.sp
                                    )
                                    if (isSel) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = GoldAccent,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Quantity Stepper
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "QUANTITY",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldDark,
                            letterSpacing = 1.5.sp
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .border(1.dp, CreamBorder, RoundedCornerShape(8.dp))
                                .background(WarmCream)
                        ) {
                            IconButton(
                                onClick = { if (quantity > 1) quantity-- },
                                enabled = quantity > 1,
                                modifier = Modifier.size(36.dp).testTag("qty_minus")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Decrease",
                                    tint = if (quantity > 1) NavyPrimary else TextMuted,
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                            Text(
                                text = quantity.toString(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(horizontal = 14.dp)
                            )

                            IconButton(
                                onClick = {
                                    if (quantity < product.stockQuantity) quantity++
                                },
                                enabled = quantity < product.stockQuantity,
                                modifier = Modifier.size(36.dp).testTag("qty_plus")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Increase",
                                    tint = if (quantity < product.stockQuantity) NavyPrimary else TextMuted,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Description
                    Text(
                        text = "THE WEAVE & FABRIC STORY",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldDark,
                        letterSpacing = 1.5.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TextPrimary,
                            lineHeight = 22.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Authenticity & Delivery Perquisites
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(CreamSurfaceVariant, RoundedCornerShape(8.dp))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Security, contentDescription = null, tint = GoldDark, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("100% Guaranteed Authentic Fiber & Color Fastness", fontSize = 12.sp, color = TextPrimary)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocalShipping, contentDescription = null, tint = GoldDark, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Cash on Delivery Across Pakistan (2-4 Business Days)", fontSize = 12.sp, color = TextPrimary)
                        }
                    }
                }
            }
        }

        // Sticky Bottom Bar with Action Buttons
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
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Add to Bag Button
                OutlinedButton(
                    onClick = {
                        viewModel.addToCart(product, selectedOption, quantity)
                    },
                    enabled = isAvailable,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = NavyPrimary
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, NavyPrimary),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("detail_add_to_cart_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingBag,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isAvailable) "Add to Bag" else "Out of Stock",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                // Buy Now Button
                Button(
                    onClick = {
                        viewModel.addToCart(product, selectedOption, quantity)
                        onNavigateCheckout()
                    },
                    enabled = isAvailable,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldAccent,
                        contentColor = NavyDark
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("detail_buy_now_btn")
                ) {
                    Text(
                        text = "Buy Now",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
