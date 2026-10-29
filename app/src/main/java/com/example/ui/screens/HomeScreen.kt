package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Loyalty
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Product
import com.example.ui.components.ProductCard
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamSurfaceVariant
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldContainer
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
import com.example.ui.viewmodel.Screen
import com.example.ui.viewmodel.StoreViewModel

data class CategoryCardItem(
    val title: String,
    val subtitle: String,
    val categoryKey: String,
    val tag: String
)

data class ReviewItem(
    val author: String,
    val location: String,
    val rating: Int,
    val comment: String,
    val productPurchased: String
)

@Composable
fun HomeScreen(
    viewModel: StoreViewModel,
    featuredProducts: List<Product>,
    onProductClick: (Long) -> Unit,
    onNavigateShop: (String) -> Unit,
    onNavigateAbout: () -> Unit,
    onNavigateContact: () -> Unit
) {
    var newsletterEmail by remember { mutableStateOf("") }
    var newsletterSubscribed by remember { mutableStateOf(false) }

    val categories = listOf(
        CategoryCardItem("Men's Wear", "Pure Boski, Karandi & Giza Cotton", "Men's Wear", "Bespoke"),
        CategoryCardItem("Women's Wear", "Embroidered Chiffon & Silk Velvet", "Women's Wear", "Festive"),
        CategoryCardItem("Unstitched Fabrics", "Mercerized Latha & Tropical Twill", "Unstitched Fabrics", "Heritage"),
        CategoryCardItem("New Arrivals", "Pashmina Shawls & Luxury Jacquard", "New Arrivals", "Exclusive")
    )

    val reviews = listOf(
        ReviewItem(
            author = "Barrister Daniyal Khan",
            location = "Lahore, Punjab",
            rating = 5,
            comment = "The 7-Pound Royal Boski is unmatched in weight and fall. Wore it for my brother's wedding and received non-stop compliments. M Shakeel Fabrics is our family's trusted brand.",
            productPurchased = "Royal Boski Silk 7-Pound"
        ),
        ReviewItem(
            author = "Syeda Mahnoor Zaidi",
            location = "Islamabad",
            rating = 5,
            comment = "Ordered the Empress Embroidered Chiffon online. Cash on delivery was swift and the tilla embroidery is even more breathtaking in person. Truly VIP luxury.",
            productPurchased = "Empress Embroidered Chiffon 3pc"
        ),
        ReviewItem(
            author = "Chaudhry Waqas",
            location = "Karachi, Sindh",
            rating = 5,
            comment = "Egyptian Giza Cotton platinum weave has that crisp yet soft touch you only find in top international luxury brands. Fast shipping to Clifton.",
            productPurchased = "Imperial Egyptian Giza Cotton"
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmCream)
    ) {
        // 1. HERO SECTION
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NavyDark)
            ) {
                // Hero Banner Image
                Image(
                    painter = painterResource(id = R.drawable.hero_banner),
                    contentDescription = "M Shakeel Fabrics Royal Showroom",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

                // Gradient Overlay
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    NavyDark.copy(alpha = 0.5f),
                                    NavyDark.copy(alpha = 0.85f),
                                    NavyDark
                                )
                            )
                        )
                )

                // Hero Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        color = GoldAccent.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(20.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent)
                    ) {
                        Text(
                            text = "ESTABLISHED HERITAGE • SINCE 1928",
                            color = GoldLight,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Wear Your Style.\nFeel the Difference.",
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontFamily = FontFamily.Serif,
                            color = SilkWhite,
                            textAlign = TextAlign.Center,
                            fontSize = 26.sp,
                            lineHeight = 34.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Curating Pakistan's most revered unstitched fabrics, royal menswear, and artisanal eastern couture. Woven to perfection with authentic yarn integrity.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = GoldLight.copy(alpha = 0.9f),
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { onNavigateShop("All") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GoldAccent,
                                contentColor = NavyDark
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("hero_shop_collection_btn")
                        ) {
                            Text(
                                text = "Shop Collection",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        OutlinedButton(
                            onClick = { onNavigateShop("Unstitched Fabrics") },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = GoldLight
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GoldLight),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("hero_explore_fabrics_btn")
                        ) {
                            Text(
                                text = "Explore Fabrics",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }

        // 2. PROMOTIONAL VIP BANNER
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(NavyPrimary, NavyLight, NavyPrimary)
                        )
                    )
                    .border(width = 0.5.dp, color = GoldPrimary.copy(alpha = 0.4f))
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Loyalty,
                            contentDescription = null,
                            tint = GoldAccent,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "NATIONWIDE CASH ON DELIVERY",
                                color = GoldLight,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "Free delivery on orders over Rs. 5,000. Use code VIP10 for 10% off.",
                                color = SilkWhite.copy(alpha = 0.8f),
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        // 3. FEATURED CATEGORIES
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 12.dp)
            ) {
                Text(
                    text = "CURATED COLLECTIONS",
                    color = GoldDark,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Text(
                    text = "Explore Our Finest Weaves",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = FontFamily.Serif,
                        color = NavyPrimary
                    ),
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    categories.forEach { cat ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .border(1.dp, CreamBorder, RoundedCornerShape(10.dp))
                                .clickable { onNavigateShop(cat.categoryKey) }
                                .testTag("category_card_${cat.categoryKey}"),
                            colors = CardDefaults.cardColors(containerColor = SilkWhite),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Surface(
                                        color = GoldContainer,
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = cat.tag.uppercase(),
                                            color = GoldDark,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = cat.title,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontFamily = FontFamily.Serif,
                                            fontWeight = FontWeight.Bold,
                                            color = NavyDark
                                        )
                                    )
                                    Text(
                                        text = cat.subtitle,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = TextSecondary
                                        )
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(NavyPrimary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowForward,
                                        contentDescription = "Open ${cat.title}",
                                        tint = GoldLight,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 4. FEATURED PRODUCTS (HORIZONTAL SCROLL)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "SIGNATURE EDITIONS",
                            color = GoldDark,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                        Text(
                            text = "Featured Masterpieces",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontFamily = FontFamily.Serif,
                                color = NavyPrimary
                            )
                        )
                    }

                    Text(
                        text = "View All",
                        color = GoldDark,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .clickable { onNavigateShop("All") }
                            .padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(featuredProducts) { product ->
                        ProductCard(
                            product = product,
                            onClick = { onProductClick(product.id) },
                            onQuickAdd = {
                                val firstOpt = product.options.split(",").firstOrNull() ?: "Standard"
                                viewModel.addToCart(product, firstOpt, 1)
                            },
                            modifier = Modifier.width(220.dp)
                        )
                    }
                }
            }
        }

        // 5. BRAND STORY & CRAFTSMANSHIP
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, GoldPrimary.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = NavyDark)
            ) {
                Column(
                    modifier = Modifier.padding(22.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = GoldAccent,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "THE M SHAKEEL HERITAGE",
                            color = GoldLight,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Ninety-Eight Years of Unrivaled Textile Excellence",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = FontFamily.Serif,
                            color = SilkWhite
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "From our origins in traditional loom weaving to our state-of-the-art combing mills, M SHAKEEL FABRICS stands as an epitome of eastern sartorial prestige. Every yard is inspected for yarn tensile strength, color fastness, and opulent fall.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = SilkWhite.copy(alpha = 0.85f),
                            lineHeight = 22.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("100%", color = GoldAccent, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            Text("Pure Fiber Guarantee", color = GoldLight, fontSize = 11.sp)
                        }
                        Column {
                            Text("50k+", color = GoldAccent, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            Text("Satisfied Connoisseurs", color = GoldLight, fontSize = 11.sp)
                        }
                        Column {
                            Text("2-4 Days", color = GoldAccent, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            Text("Express Delivery", color = GoldLight, fontSize = 11.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = onNavigateAbout,
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldAccent),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Read Brand Story & Showrooms", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        // 6. CUSTOMER TESTIMONIALS
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "TESTIMONIALS",
                    color = GoldDark,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Text(
                    text = "Voices of Our Patronage",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = FontFamily.Serif,
                        color = NavyPrimary
                    ),
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(reviews) { r ->
                        Card(
                            modifier = Modifier
                                .width(280.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .border(1.dp, CreamBorder, RoundedCornerShape(10.dp)),
                            colors = CardDefaults.cardColors(containerColor = SilkWhite)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row {
                                        repeat(r.rating) {
                                            Icon(
                                                imageVector = Icons.Default.Star,
                                                contentDescription = null,
                                                tint = GoldAccent,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Verified,
                                            contentDescription = "Verified Buyer",
                                            tint = SuccessGreen,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Text("Verified", color = SuccessGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "\"${r.comment}\"",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = TextPrimary,
                                        lineHeight = 20.sp
                                    )
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Divider(color = CreamBorder, thickness = 0.5.dp)

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = r.author,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = NavyDark
                                )
                                Text(
                                    text = "${r.location} • Bought ${r.productPurchased}",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                        }
                    }
                }
            }
        }

        // 7. NEWSLETTER SUBSCRIPTION
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, CreamBorder, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = CreamSurfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = GoldDark,
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Join The VIP Fabric Circle",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            color = NavyPrimary
                        )
                    )

                    Text(
                        text = "Receive early access to seasonal Boski releases, private festive catalogs, and exclusive patron privileges.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (!newsletterSubscribed) {
                        OutlinedTextField(
                            value = newsletterEmail,
                            onValueChange = { newsletterEmail = it },
                            placeholder = { Text("Enter your email address", fontSize = 13.sp) },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("newsletter_email_input"),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GoldDark,
                                unfocusedBorderColor = CreamBorder,
                                focusedContainerColor = SilkWhite,
                                unfocusedContainerColor = SilkWhite
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                                if (newsletterEmail.isNotBlank()) {
                                    newsletterSubscribed = true
                                    viewModel.showMessage("Welcome to the VIP Fabric Circle!")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NavyPrimary,
                                contentColor = GoldLight
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("newsletter_submit_btn")
                        ) {
                            Text("Subscribe for VIP Privileges", fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = SuccessGreen,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "You are subscribed to M Shakeel VIP updates!",
                                color = SuccessGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }

        // 8. LUXURY FOOTER
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NavyDark)
                    .padding(24.dp)
            ) {
                Column {
                    Text(
                        text = "M SHAKEEL FABRICS",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp,
                            color = GoldLight
                        )
                    )
                    Text(
                        text = "Bespoke eastern fabrics, pure silk boski, and royal festive ensembles.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SilkWhite.copy(alpha = 0.7f)
                        ),
                        modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                    )

                    Divider(color = GoldPrimary.copy(alpha = 0.2f))

                    Spacer(modifier = Modifier.height(14.dp))

                    Text("CUSTOMER CARE & SERVICES", color = GoldAccent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "• Shop Full Catalog",
                        color = SilkWhite,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .clickable { onNavigateShop("All") }
                            .padding(vertical = 3.dp)
                    )
                    Text(
                        text = "• About Brand Legacy",
                        color = SilkWhite,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .clickable(onClick = onNavigateAbout)
                            .padding(vertical = 3.dp)
                    )
                    Text(
                        text = "• Contact & Flagship Showrooms",
                        color = SilkWhite,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .clickable(onClick = onNavigateContact)
                            .padding(vertical = 3.dp)
                    )
                    Text(
                        text = "• Shipping & Returns (2-4 Days Express COD)",
                        color = SilkWhite.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(vertical = 3.dp)
                    )
                    Text(
                        text = "• Authentic Fabric Guarantee",
                        color = SilkWhite.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(vertical = 3.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "© 2026 M SHAKEEL FABRICS. All rights reserved.\nCrafted with pride for connoisseurs of fine eastern textiles.",
                        fontSize = 10.sp,
                        color = TextMuted,
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }
}
